package com.amazonaws.lambda.funzioni.common;
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

public class BeautifulVinoAcquista implements RequestHandler<RichiestaAcquistaGenerica, Risposta> {
	
	private static final String SMTP_HOST_NAME = "smtps.aruba.it";
    private static final int SMTP_HOST_PORT = 465;//465,587,25
    private static final String SMTP_AUTH_USER = "info@beautifulvino.com";
    private static final String SMTP_AUTH_PWD  = "biutiful2017";
    
    @Override
	public Risposta handleRequest(RichiestaAcquistaGenerica input, Context context) {
        Risposta risposta = new Risposta();
        Esito esito = FunzioniUtils.getEsitoPositivo();
        String nomeU = input.getNomeUtente();
        String idU = input.getIdUtente();
        String nomeE = input.getNomeEvento();
        String idE = input.getIdEvento();
        int num = input.getNumeroPartecianti();
        
        if (nomeU != null && idU != null && nomeE != null && idE != null) {
	        String testo = "l'utente " + nomeU + " (id:" + idU +") ha acquistato l'evento " + nomeE + " (id: " + idE + ")./n Numero partecipanti " + num;
	        String oggetto = "Acquisto evento " + nomeE;
	        sendMail(testo, oggetto);
        }else {
        	esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_PROCEDURA_LAMBDA);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + "passati valori nulli");
			risposta.setEsito(esito);
			return risposta;
        }
        risposta.setEsito(esito);
		return risposta;
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