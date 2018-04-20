package com.amazonaws.lambda.funzioni.connect;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.VinoUtente;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Vino.UtenteVino;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;

public class connectViniAUtenteGen implements RequestHandler<RichiestaConnectGenerica, RispostaConnectGenerica> {

	// Replace sender@example.com with your "From" address.
    // This address must be verified.
    static final String FROM = "sender@example.com";
    static final String FROMNAME = "Sender Name";
	
    // Replace recipient@example.com with a "To" address. If your account 
    // is still in the sandbox, this address must be verified.
    static final String TO = "recipient@example.com";
    
    // Replace smtp_username with your Amazon SES SMTP user name.
    static final String SMTP_USERNAME = "smtp_username";
    
    // Replace smtp_password with your Amazon SES SMTP password.
    static final String SMTP_PASSWORD = "smtp_password";
    
    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    static final String CONFIGSET = "ConfigSet";
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";
    
    // The port you will connect to on the Amazon SES SMTP endpoint. 
    static final int PORT = 587;
    
    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
    
    static final String BODY = String.join(
    	    System.getProperty("line.separator"),
    	    "<h1>Amazon SES SMTP Email Test</h1>",
    	    "<p>This email was sent with Amazon SES using the ", 
    	    "<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
    	    " for <a href='https://www.java.com'>Java</a>."
    	);
	
    @Override
    public RispostaConnectGenerica handleRequest(RichiestaConnectGenerica input, Context context) {
        context.getLogger().log("Input: " + input);

        RispostaConnectGenerica risposta = getRisposta(input);
        return risposta;
    }

	private RispostaConnectGenerica getRisposta(RichiestaConnectGenerica input) {
		RispostaConnectGenerica risposta = new RispostaConnectGenerica();
		Esito esito = new Esito();
		esito.setCodice(100);
        esito.setMessage("Esito corretto per la richiesta getEventi");
        
        String idUtente = input.getIdUtente();
        String idVino = input.getIdVino();
        String statoVino = input.getStatoVino();
        
		AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " connectEventoAUtente ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			if(idVino == null || idVino.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idVino nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			if(statoVino == null || statoVino.equals("") || (!statoVino.equals(FunzioniUtils.VINO_STATO_PREFERITO) && !statoVino.equals(FunzioniUtils.EVENTO_STATO_ACQUISTATO) && !statoVino.equals(FunzioniUtils.EVENTO_STATO_CANCELLATO) && !statoVino.equals(FunzioniUtils.EVENTO_STATO_NEUTRO))) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " lo stato ricevuto non è presente o non è riconosciuto tra quelli gestiti (P-Preferito, A-Acquistato/prenotato, D-Cancellato)");
		        risposta.setEsito(esito);
		        return risposta;
			}
			if(idUtente == null || idUtente.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			Utente utente = mapper.load(Utente.class, idUtente);
			if(utente == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente non troovato sul DB, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			Vino vino = mapper.load(Vino.class, idVino);
			if(vino == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " vino non troovato sul DB, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			//CONTROLLO SE IL VINO è GIà ASSOCIATO ALL'UTENTE
			List<VinoUtente> viniUtente = utente.getViniUtenteInt();
			boolean vinoGiaAssociato = false;
			String statoVinoAttuale = "";
			if(viniUtente != null) {
				for (Iterator<VinoUtente> iterator = viniUtente.iterator(); iterator.hasNext();) {
					VinoUtente vinoUtente = iterator.next();
					if(vinoUtente.getIdVino().equals(idVino)) {
						vinoGiaAssociato = true;
						statoVinoAttuale = vinoUtente.getStatoVino();
					}
				}
			} else {
				viniUtente = new ArrayList<VinoUtente>();
			}
			
			if(vinoGiaAssociato) {
				//4
				VinoUtente daRimuovere = null;
				for (Iterator<VinoUtente> iterator = viniUtente.iterator(); iterator.hasNext();) {
					VinoUtente vinoUtente = iterator.next();
					if(vinoUtente.getIdVino().equals(idVino)) {
						daRimuovere = vinoUtente;
					}
				}
				if(daRimuovere.getStatoVino().equals(FunzioniUtils.VINO_STATO_ACQUISTATO) && !statoVino.equals(FunzioniUtils.VINO_STATO_ACQUISTATO)) {
					//7 - non posso cancellare o cambiare stato se lo stato precedente è Acquistato
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
	    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " si tenta di cambiare di stato o cancellare un vino già acquistato.");
	    		        risposta.setEsito(esito);
	    		        return risposta;
				} else {
					viniUtente.remove(daRimuovere);
					
					//devo anche rimuovere l'utente dal vino
	    				if(!(statoVino.equals(FunzioniUtils.VINO_STATO_CANCELLATO) || statoVino.equals(FunzioniUtils.VINO_STATO_NEUTRO))) {
	    					associaUtenteAVino(idUtente, idVino, mapper);
	    					daRimuovere.setStatoVino(statoVino);
	    					viniUtente.add(daRimuovere);
	    				} else {
	    					disassociaUtenteAVino(idUtente, idVino, mapper);
	    				}
				}
				
			} else {
				
				//5
				//vino già associato (P o A) e stato proposto D o N
				if(statoVino.equals(FunzioniUtils.VINO_STATO_CANCELLATO) || statoVino.equals(FunzioniUtils.VINO_STATO_NEUTRO)) {
					//7
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
	    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " si tenta di cancellare un vino non associato all'utente");
	    		        risposta.setEsito(esito);
	    		        return risposta;
				} else {
					//vino già associato (P o A) e stato proposto A
					//6
					VinoUtente vu = new VinoUtente();
					
					vu.setIdVino(idVino);
					vu.setStatoVino(statoVino);
					vu.setUrlLogoVino(vino.getUrlLogoVino());
					vu.setUvaggioVino(vino.getUvaggioVino());
					vu.setNomeVino(vino.getNomeVino());
					vu.setInBreveVino(vino.getInBreveVino());
					viniUtente.add(vu);
					
					//devo anche associare l'utente al Vino
					associaUtenteAVino(idUtente, idVino, mapper);
				}
			}
			
			utente.setViniUtenteInt(viniUtente);
			
			//NELLE PROPERTIES CERCO SE DEVO MANDARE LA MAIL
			com.marte5.modello2.Properties prop = mapper.load(com.marte5.modello2.Properties.class, com.marte5.modello2.Properties.PROPERTY_KEY_SENDMAIL);
//			if(prop != null && prop.getPropertyValue().equals("S")) {
//				try {
//					inviaMailAcquisto();
//				} catch (UnsupportedEncodingException | MessagingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
//					esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Errore nell'invio della mail per l'acquisto");
//					esito.setTrace(e.getMessage());
//					risposta.setEsito(esito);
//					return risposta;
//				}
//			}
			
			try {
				mapper.save(utente);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Errore nell'aggiunta dell'evento tra i preferiti dell'utente ");
				esito.setTrace(e.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
		}
		
        risposta.setEsito(esito);
		return risposta;
	}
	
	private void associaUtenteAVino(String idUtente, String idVino, DynamoDBMapper mapper) {
		Vino vino = mapper.load(Vino.class, idVino);
		List<UtenteVino> utentiVino = vino.getUtentiVinoInt();
		if(utentiVino == null) {
			utentiVino = new ArrayList<UtenteVino>();
		}
		boolean presente = false;
		for (UtenteVino utenteVino : utentiVino) {
			if(utenteVino.getIdUtente().equals(idUtente)) {
				presente = true;
			}
		}
		if(!presente) {
			UtenteVino uv = new UtenteVino();
			uv.setIdUtente(idUtente);
			utentiVino.add(uv);
		}
		vino.setUtentiVinoInt(utentiVino);
		mapper.save(vino);
	}
	
	private void disassociaUtenteAVino(String idUtente, String idVino, DynamoDBMapper mapper) {
		Vino vino = mapper.load(Vino.class, idVino);
		List<UtenteVino> utentiVino = vino.getUtentiVinoInt();
		if(utentiVino == null) {
			utentiVino = new ArrayList<UtenteVino>();
		}
		UtenteVino daRimuovere = null;
		boolean presente = false;
		for (UtenteVino utenteVino : utentiVino) {
			if(utenteVino.getIdUtente().equals(idUtente)) {
				daRimuovere = utenteVino;
				presente = true;
			}
		}
		if(presente) {
			utentiVino.remove(daRimuovere);
		}
		vino.setUtentiVinoInt(utentiVino);
		mapper.save(vino);
	}
	
//	private void inviaMailAcquisto() throws UnsupportedEncodingException, MessagingException {
//		
//		Properties props = System.getProperties();
//	    	props.put("mail.transport.protocol", "smtp");
//	    	props.put("mail.smtp.port", PORT); 
//	    	props.put("mail.smtp.starttls.enable", "true");
//	    	props.put("mail.smtp.auth", "true");
//	
//	        // Create a Session object to represent a mail session with the specified properties. 
//	    	Session session = Session.getDefaultInstance(props);
//
//        // Create a message with the specified information. 
//        MimeMessage msg = new MimeMessage(session);
//        msg.setFrom(new InternetAddress(FROM,FROMNAME));
//        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
//        msg.setSubject(SUBJECT);
//        msg.setContent(BODY,"text/html");
//        
//        // Add a configuration set header. Comment or delete the 
//        // next line if you are not using a configuration set
//        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
//            
//        // Create a transport.
//        Transport transport = session.getTransport();
//                    
//        // Send the message.
//        try
//        {
//            System.out.println("Sending...");
//            
//            // Connect to Amazon SES using the SMTP username and password you specified above.
//            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
//        	
//            // Send the email.
//            transport.sendMessage(msg, msg.getAllRecipients());
//            System.out.println("Email sent!");
//        }
//        catch (Exception ex) {
//            System.out.println("The email was not sent.");
//            System.out.println("Error message: " + ex.getMessage());
//        }
//        finally
//        {
//            // Close and terminate the connection.
//            transport.close();
//        }
//	} 
}
