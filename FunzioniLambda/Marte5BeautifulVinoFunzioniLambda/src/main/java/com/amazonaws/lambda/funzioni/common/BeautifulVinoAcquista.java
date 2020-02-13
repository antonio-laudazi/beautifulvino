package com.amazonaws.lambda.funzioni.common;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.acquista.RichiestaAcquistaGenerica;
import com.marte5.modello.risposte.Risposta;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Utente;

public class BeautifulVinoAcquista implements RequestHandler<RichiestaAcquistaGenerica, Risposta> {
	
	private static final String SMTP_HOST_NAME = "smtps.aruba.it";
    private static final int SMTP_HOST_PORT = 465;//465,587,25
    private static final String SMTP_AUTH_USER = "eventi@beautifulvino.com";
    private static final String SMTP_AUTH_PWD  = "beautifulevents";
    
    @Override
	public Risposta handleRequest(RichiestaAcquistaGenerica input, Context context) {
        Risposta risposta = new Risposta();
        Esito esito = FunzioniUtils.getEsitoPositivo();
        Utente utente = input.getUtente();
        String nomeU = utente.getUsernameUtente();
        String emailU = utente.getEmailUtente();
        Evento evento = input.getEvento();
        String facebookU = evento.getFacebookEvento();
        String idU = utente.getIdUtente();
        String nomeE = input.getNomeEvento();
        String idE = input.getIdEvento();
        
        Date date = new Date (evento.getDataEvento());
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = f.format(date);
        
        int num = input.getNumeroPartecianti();
        String ac = "prenotato";
        if (input.getAcquista() == 1) {
        	ac = "acquistato";
        }
        if (nomeU == null) nomeU = "utente senza nome";
        if (nomeE == null) nomeE = "evento senza nome";
        if (nomeU != null && idU != null && nomeE != null && idE != null) {
	        String testo = "l'utente " + nomeU + " (id:" + idU +") ha "+ ac + " l'evento " + nomeE + " (id: " + idE + ").\n Numero partecipanti " + num +
	        		". \n email: " + emailU;
	        String oggetto = ac + " evento " + nomeE;
	        sendMail(testo, oggetto, SMTP_AUTH_USER);
	        
	        //invio l'email a l'utente
	        String partStr = " posti";
	        if (num == 1) {
	        	partStr = " posto";
	        }
	        String testo1 = "<div style=\"font-style:interUI; font-size:16pt\">" + 
	        		"    <div>Ciao "+ nomeU + "!</div>" + 
	        		"     <p></p>  " +
	        		"    <div>Ti confermiamo la prenotazione di "+ input.getNumeroPartecianti()+ partStr + " per la degustazione "+ evento.getTitoloEvento()+ ".</div></br>" + 
	        		"    <div>Ci vediamo il "+ dateString +" a "+ evento.getCittaEvento() +"!</div></br>" + 
	        		"    <div>Fai sapere ai tuoi amici che ci sarai, condividi l'evento:  <a href=" + facebookU+ ">"+facebookU +"</a>.</div>" + 
	        		"     <p></p>  " +
	        		"    <div>Trovi tutte le info nell'app.</div>" + 
	        		"     <p></p>  " +
	        		"    <div>A presto!</div>" + 
	        		"     <p></p>  " +
	        		"    <div style=\"color:#E8C900\">Il Team di Beautiful Vino </div>" + 
	        		"    <a href=\"www.beautifulvino.com\">www.beautifulvino.com</a>" + 
	        		"     <p></p>  " +
	        		"    <div><img src=\"https://s3.eu-central-1.amazonaws.com/beautifulvino-bucket-immagini/1539174434157_eventoImagefile.png\"></div>" + 
	        		"</div>" ;
	        String oggetto1 = "Evento BeautifulVino";
	        sendMail(testo1, oggetto1, emailU);
        }else {
        	esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_PROCEDURA_LAMBDA);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + "passati valori nulli");
			risposta.setEsito(esito);
			return risposta;
        }
        risposta.setEsito(esito);
		return risposta;
	}
    
	private void sendMail (String testo, String oggetto, String mail){
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
	        message.setContent(testo, "text/html");
	        message.setSubject(oggetto);
	        message.setFrom(new InternetAddress(SMTP_AUTH_USER));
	        message.addRecipient(Message.RecipientType.TO,
	        new InternetAddress(mail));
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