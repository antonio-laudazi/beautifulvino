package com.amazonaws.lambda.funzioni.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello2.Badge;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Livello;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Evento.BadgeEvento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Evento.VinoEvento;

public class BeautifulVinoPeriodicEvent implements RequestHandler<Map<String,Object>, String> {
	
	public static final int INFINITI_PUNTI_ESP = -1;
	private static final String SMTP_HOST_NAME = "smtps.aruba.it";
    private static final int SMTP_HOST_PORT = 465;//465,587,25
    private static final String SMTP_AUTH_USER = "info@beautifulvino.com";
    private static final String SMTP_AUTH_PWD  = "biutiful2017";
    
	@Override
	public String handleRequest(Map<String,Object> input, Context context) {
		AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		}catch (Exception e) {
			System.err.println("errore apertura client");
			e.printStackTrace();
		}
		String text = "aggiornamento periodico eventi\n";
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			RichiestaGetGenerica rg = new RichiestaGetGenerica();
			BeautifulVinoGet g = new BeautifulVinoGet();
			rg.setFunctionName("getEventiGen");
			rg.setIdUtente("eu-central-1:e7ae1814-8e42-49fc-a183-d5e2abaf0d7c");
			RispostaGetGenerica out = g.handleRequest(rg, context);
			List<Evento> eventi = out.getEventi();
			
			if (eventi != null) {
				for (Evento e : eventi) {
					Date date = new Date();
					Calendar dataOggi = Calendar.getInstance();
					dataOggi.setTime(date);
					
					Date datee = new Date (e.getDataEvento());
					Calendar dataEvento = Calendar.getInstance();
					dataEvento.setTime(datee);
					
					//carico il badge
					BadgeEvento be = e.getBadgeEventoInt();
					Badge bu = null;
					if (be != null) {
//						RichiestaGetGenerica rgb = new RichiestaGetGenerica();
//						BeautifulVinoGet gb = new BeautifulVinoGet();
//						rgb.setFunctionName("getBadgeGen");
//						rgb.setIdUtente("eu-central-1:e7ae1814-8e42-49fc-a183-d5e2abaf0d7c");
//						rgb.setIdBadge(be.getIdBadge());
//						RispostaGetGenerica outb = gb.handleRequest(rgb, context);
//						bu = outb.getBadge();
						bu = mapper.load(Badge.class, be.getIdBadge());
						bu.setDataBadge(e.getDataEvento());
						mapper.save(bu);
					}
					
		        	if (dataEvento.get(Calendar.DAY_OF_MONTH) == dataOggi.get(Calendar.DAY_OF_MONTH) && dataEvento.get(Calendar.MONTH) == dataOggi.get(Calendar.MONTH) && dataEvento.get(Calendar.YEAR) == dataOggi.get(Calendar.YEAR)) {
		        		
						//aggiungo tutti i vini dell'evento a tutti gli utenti iscritti
		        		text = text + "-----------------------------------------\n";
		        		text = text + "evento " + e.getTitoloEvento() + " (id: " + e.getIdEvento() + ") e' previsto per oggi\n";
		        		List<UtenteEvento> u = e.getIscrittiEventoInt();
		        		List<VinoEvento> v = e.getViniEventoInt();
		        		if (u != null && v != null) {
			        		for (UtenteEvento uu : u) {
			        			//collego l'utente al vino
			        			for (VinoEvento vv : v) {
			        				RichiestaConnectGenerica r = new RichiestaConnectGenerica();    	
			        				BeautifulVinoConnect c = new BeautifulVinoConnect();
			        				r.setFunctionName("connectViniAUtenteGen");
			        				r.setStatoVino("A");
			        				r.setIdUtente(uu.getIdUtente());
			        				r.setIdVino(vv.getIdVino());
			        				RispostaConnectGenerica o = c.handleRequest(r, context);
			        				System.out.println("esito connect " + uu.getIdUtente() + " " + vv.getIdVino() + " = "+ o.getEsito());
			        				text = text + "il vino " + vv.getNomeVino() + "(id: " + vv.getIdVino()+ ") e' stato aggiunto alla lista dell'utente con id " + 
			        						uu.getIdUtente() + " con esito: "+ o.getEsito().getMessage() + "\n";
			        			}
			        			//aggiungo il badge all'utente
			        			if (bu != null) {
				        			Esito esito = aggiungiBadge(uu, bu, context);
				        			text = text + "Il Badge " + bu.getNomeBadge() + " (id: " + bu.getIdBadge() +
				        					") e' stata aggiunto all'utente con id " + uu.getIdUtente() +
				        					" con esito: " + esito.getMessage() +"\n";
			        			}
			        			//aggiungo crediti e esperienza
				        		String risp = aggiungiEspCred(uu.getIdUtente(), e.getPuntiEsperienza(), e.getCreditiEvento(), mapper);
				        		if (risp.equals("errore")) {
				        			System.out.println("errore aggiunta punti esperienza o crediti");
				        		}
				        		text = text + risp + "\n";
			        		}
			        		text = text + "-----------------------------------------\n";
		        		}
		        	}
		        }
			}
		}
		if (!text.equals("aggiornamento periodico eventi\n"))sendMail(text, "riepilogo aggiornamento periodico BeatifulVino");
		return "ok";
	}
	
	private String aggiungiEspCred(String idUtente, int exp, int cred, DynamoDBMapper mapper) {
		Utente utente = mapper.load(Utente.class, idUtente);
		if(utente == null) {
			return "errore";
		}
		String text = "l'utente " + utente.getNomeUtente() + "ha guadagnato " + exp + " esperienza e " +
				cred + " crediti. ";
		utente.setEsperienzaUtente(utente.getEsperienzaUtente() + exp); 
		utente.setCreditiUtente(utente.getCreditiUtente() + cred);
		text = text + " adesso ha " + utente.getEsperienzaUtente() + " exp e" + utente.getCreditiUtente() + " cred.";
		System.out.println ("l'utente : " + utente.getIdUtente() + utente.getNomeUtente() + utente.getCognomeUtente() + "ha ottenuto " + exp + " esperienza");
		//gestione livello utente 
		int esp = utente.getEsperienzaUtente();
		utente.setLivelloUtente("unknown");
		DynamoDBScanExpression expr = new DynamoDBScanExpression();
		List<Livello> listaLivelli = mapper.scan(Livello.class, expr);
		for (Livello l : listaLivelli) {
			if (l.getMax() != INFINITI_PUNTI_ESP) {
				if (esp >= l.getMin() && esp <= l.getMax() ) {
					utente.setLivelloUtente(l.getNomeLivello());
					int gap = l.getMax() - esp;
					String prox = "";
					for (Livello l1: listaLivelli) {
						if (l1.getMin() == l.getMax() + 1) prox = l1.getNomeLivello();
					}
					utente.setPuntiMancantiProssimoLivelloUtente("Per diventare " + prox + " ti mancano " + gap + 1 + " pt" );
					break;
				}
			}else {
				if (esp >= l.getMin() ) {
					utente.setLivelloUtente(l.getNomeLivello());
					utente.setPuntiMancantiProssimoLivelloUtente("Hai raggiunto il massimo livello" );
					break;
				}
			}
		}
		mapper.save(utente);
		return text;
	}
	
	private Esito aggiungiBadge (UtenteEvento utente, Badge badge, Context context) {
		RichiestaConnectGenerica rg = new RichiestaConnectGenerica();
		BeautifulVinoConnect g = new BeautifulVinoConnect();
		rg.setFunctionName("connectBadgeAUtenteGen");
		rg.setIdUtente(utente.getIdUtente());
		List<Badge> lb = new ArrayList<>();
		lb.add(badge);
		rg.setBadges(lb);
		RispostaConnectGenerica out = g.handleRequest(rg, context);
		return out.getEsito();
	}
	
	private void sendMail (String testo, String oggetto){
		Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", SMTP_HOST_PORT);
        props.put("mail.smtp.ssl.enable", "true");
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        // mailSession.setDebug(true);
        Transport transport;
		try {
			transport = mailSession.getTransport();
	        MimeMessage message = new MimeMessage(mailSession);
	        message.setContent(testo, "text/plain");
	        message.setSubject(oggetto);
	        message.setFrom(new InternetAddress(SMTP_AUTH_USER));
	        message.addRecipient(Message.RecipientType.TO,
	        new InternetAddress(SMTP_AUTH_USER));
	        transport.connect();
	        transport.sendMessage(message,
	            message.getRecipients(Message.RecipientType.TO));
	        transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = SMTP_AUTH_USER;
           String password = SMTP_AUTH_PWD;
           return new PasswordAuthentication(username, password);
        }
    }
}