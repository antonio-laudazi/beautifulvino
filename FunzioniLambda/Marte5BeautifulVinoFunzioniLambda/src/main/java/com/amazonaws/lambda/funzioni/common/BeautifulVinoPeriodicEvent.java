package com.amazonaws.lambda.funzioni.common;

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
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Evento.VinoEvento;

public class BeautifulVinoPeriodicEvent implements RequestHandler<Map<String,Object>, String> {
	
	private static final String SMTP_HOST_NAME = "out.virgilio.it";
    private static final int SMTP_HOST_PORT = 465;//465,587,25
    private static final String SMTP_AUTH_USER = "capetougo@virgilio.it";
    private static final String SMTP_AUTH_PWD  = "biscotti";
    
	@Override
	public String handleRequest(Map<String,Object> input, Context context) {
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
				
	        	if (dataEvento.get(Calendar.DAY_OF_MONTH) == dataOggi.get(Calendar.DAY_OF_MONTH) && dataEvento.get(Calendar.MONTH) == dataOggi.get(Calendar.MONTH) && dataEvento.get(Calendar.YEAR) == dataOggi.get(Calendar.YEAR)) {
	        		List<UtenteEvento> u = e.getIscrittiEventoInt();
	        		List<VinoEvento> v = e.getViniEventoInt();
	        		if (u != null && v != null) {
		        		for (UtenteEvento uu : u) {
		        			for (VinoEvento vv : v) {
		        				RichiestaConnectGenerica r = new RichiestaConnectGenerica();    	
		        				BeautifulVinoConnect c = new BeautifulVinoConnect();
		        				r.setFunctionName("connectViniAUtenteGen");
		        				r.setStatoVino("A");
		        				r.setIdUtente(uu.getIdUtente());
		        				r.setIdVino(vv.getIdVino());
		        				RispostaConnectGenerica o = c.handleRequest(r, context);
		        				System.out.println("esito connect " + uu.getIdUtente() + " " + vv.getIdVino() + " = "+ o.getEsito());
		        			}
		        		}
	        		}
	        	}
	        }
		}
		sendMail();
		return "ok";
	}
	private void sendMail (){
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
	        message.setContent("This is a test", "text/plain");
	        message.setFrom(new InternetAddress("capetougo@virgilio.it"));
	        message.addRecipient(Message.RecipientType.TO,
	        new InternetAddress("capetougo@virgilio.it"));
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