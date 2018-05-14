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

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;
import com.marte5.modello2.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.BadgeEvento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Evento.VinoEvento;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.BadgeUtente;

public class BeautifulVinoPeriodicEvent implements RequestHandler<Map<String,Object>, String> {
	
	private static final String SMTP_HOST_NAME = "smtps.aruba.it";
    private static final int SMTP_HOST_PORT = 465;//465,587,25
    private static final String SMTP_AUTH_USER = "info@beautifulvino.com";
    private static final String SMTP_AUTH_PWD  = "biutiful2017";
    
	@Override
	public String handleRequest(Map<String,Object> input, Context context) {
		RichiestaGetGenerica rg = new RichiestaGetGenerica();
		BeautifulVinoGet g = new BeautifulVinoGet();
		rg.setFunctionName("getEventiGen");
		rg.setIdUtente("eu-central-1:e7ae1814-8e42-49fc-a183-d5e2abaf0d7c");
		RispostaGetGenerica out = g.handleRequest(rg, context);
		List<Evento> eventi = out.getEventi();
		String text = "aggiornamento peridico eventi\n";
		if (eventi != null) {
			for (Evento e : eventi) {
				Date date = new Date();
				Calendar dataOggi = Calendar.getInstance();
				dataOggi.setTime(date);
				
				Date datee = new Date (e.getDataEvento());
				Calendar dataEvento = Calendar.getInstance();
				dataEvento.setTime(datee);
				
				//aggiunta Badge 
				BadgeEvento be = e.getBadgeEventoInt();
				RichiestaGetGenerica rgb = new RichiestaGetGenerica();
				BeautifulVinoGet gb = new BeautifulVinoGet();
				rgb.setFunctionName("getBadgeGen");
				rgb.setIdUtente("eu-central-1:e7ae1814-8e42-49fc-a183-d5e2abaf0d7c");
				rgb.setIdBadge(be.getIdBadge());
				RispostaGetGenerica outb = gb.handleRequest(rg, context);
				Badge badge = outb.getBadge();
				BadgeUtente bu = new BadgeUtente();
				bu.setIdBadge(badge.getIdBadge());
				bu.setInfoBadge(badge.getInfoBadge());
				bu.setNomeBadge(badge.getNomeBadge());
				bu.setTuoBadge(badge.getTuoBadge());
				bu.setUrlLogoBadge(badge.getUrlLogoBadge());
				
	        	if (dataEvento.get(Calendar.DAY_OF_MONTH) == dataOggi.get(Calendar.DAY_OF_MONTH) && dataEvento.get(Calendar.MONTH) == dataOggi.get(Calendar.MONTH) && dataEvento.get(Calendar.YEAR) == dataOggi.get(Calendar.YEAR)) {
	        		text = text + "-----------------------------------------\n";
	        		text = text + "evento " + e.getTitoloEvento() + " (id: " + e.getIdEvento() + ") è previsto per oggi\n";
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
		        				text = text + "il vino " + vv.getNomeVino() + "(id: " + vv.getIdVino()+ ") è stato aggiunto alla lista dell'utente con id " + 
		        						uu.getIdUtente() + " con esito: "+ o.getEsito().getMessage() + "\n";
		        			}
		        			//aggiungo il badge all'utente
		        			Esito esito = aggiungiBadge(uu, bu, context);
		        			text = text + "Il Badge " + bu.getNomeBadge() + " (id: " + bu.getIdBadge() +
		        					") è stata aggiunto all'utente con id " + uu.getIdUtente() +
		        					" con esito: " + esito.getMessage() +"\n";
		        		}
		        		text = text + "----------------------------------------\n";
	        		}
	        	}
	        }
		}
		sendMail(text, "riepilogo aggiornamento periodico BeatifulVino");
		return "ok";
	}
	
	private Esito aggiungiBadge (UtenteEvento utente, BadgeUtente badge, Context context) {
		//richedo l'utente
		RichiestaGetGenerica rg = new RichiestaGetGenerica();
		BeautifulVinoGet g = new BeautifulVinoGet();
		rg.setFunctionName("getUtenteGen");
		rg.setIdUtente(utente.getIdUtente());
		RispostaGetGenerica out = g.handleRequest(rg, context);
		if (out.getEsito().getCodice() != 100) return out.getEsito();
		Utente u = out.getUtente();
		//aggiorno l'utente
		List<BadgeUtente> bu = u.getBadgeUtenteInt();
		if (bu == null)bu = new ArrayList<>();
		bu.add(badge);
		u.setBadgeUtenteInt(bu);
		//salvo l'utente
		RichiestaPutGenerica pg = new RichiestaPutGenerica();
		BeautifulVinoPut p = new BeautifulVinoPut();
		pg.setUtente(u);
		RispostaPutGenerica outp = p.handleRequest(pg, context);
		return outp.getEsito();
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