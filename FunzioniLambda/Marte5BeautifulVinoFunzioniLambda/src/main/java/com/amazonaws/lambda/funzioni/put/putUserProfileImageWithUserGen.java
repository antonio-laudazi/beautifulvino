package com.amazonaws.lambda.funzioni.put;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.marte5.modello.Esito;
import com.marte5.modello2.Utente;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;

public class putUserProfileImageWithUserGen implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {
	
    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
    	
        RispostaPutGenerica risposta = new RispostaPutGenerica();
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        Utente utente = input.getUtente();
        
        if(utente == null) {
	    		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(this.getClass().getName() + " - " +  EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage - manca l'utente!");
			esito.setTrace(this.getClass().getName() + " - " +  EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage - manca l'utente!");
			risposta.setEsito(esito);
			return risposta;
	    }
	    
	    if(utente.getIdUtente() == null || utente.getIdUtente().equals("")) {
	    		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(this.getClass().getName() + " - " +  EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage - non posso salvare un utente con idUtente vuoto");
			esito.setTrace(this.getClass().getName() + " - " +  EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage - non posso salvare un utente con idUtente vuoto");
			risposta.setEsito(esito);
			return risposta;
	    }
        
        String idImmagine = FunzioniUtils.getEntitaId();
        
        //dati immagine
        String base64 = input.getBase64Image();
        
        if(base64 != null && !base64.equals("")) {
        	
        		//salvo immagine
	        	String[] splitted = base64.split(",");
            if(splitted.length > 1) {
            		base64 = splitted[1];
            }
            
            String filename =  idImmagine + ".jpg";
            
            //controlli sui dati ricevuti
            String bucketName = "beautifulvino-bucket-immagini-profili";
            
            AmazonS3 client = null;
            
            try {
            		client = AmazonS3ClientBuilder.standard().build();
	    		} catch (Exception e1) {
	    			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
	    			esito.setMessage(this.getClass().getName() + " - " +  EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage - non è stato possibile salvare l'immagine del profilo");
	    			esito.setTrace(e1.getMessage());
	    			risposta.setEsito(esito);
	    			return risposta;
	    		}
            if(client != null) {
            	
	    			//creo un file dal base64 ricevuto
	    			byte[] data = Base64.decodeBase64(base64);
	    			ByteArrayInputStream bis = new ByteArrayInputStream(data);
	    			
	    			// write the image to a file
	    			
	    			BufferedImage image = null;
	    			try {
	    				image = ImageIO.read(bis);
	    				bis.close();
	    				
	    				File outputfile = File.createTempFile("temp", "temp");
	    				ImageIO.write(image, "jpg", outputfile);
	    				
	    				//preparo la richiesta di put aggiungendo l'istruzione che rende pubblico il file
	    				PutObjectRequest request = new PutObjectRequest(bucketName, filename, outputfile);
	    				request.setCannedAcl(CannedAccessControlList.PublicRead);
	    				client.putObject(request);
	    			} catch (IOException e) {
	    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
	    				esito.setMessage(this.getClass().getName() + " - " +  EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage - errore nel salvataggio dell'immagine ");
	    				esito.setTrace(e.getMessage());
	    				risposta.setEsito(esito);
	    				return risposta;
	    			} catch (Exception e) {
	    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
	    				esito.setMessage(this.getClass().getName() + " - " +  EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage - errore nel salvataggio dell'immagine");
	    				esito.setTrace(e.getMessage());
	    				risposta.setEsito(esito);
	    				return risposta;
	    			} finally {
	    				
	    			}
	    			
	    			String imageUrl = FunzioniUtils.AMAZON_S3_BASE_URL + bucketName + "/" + filename;
	    			utente.setUrlFotoUtente(imageUrl);
            }
        }
        
        esito = salvaUtente(utente);
        risposta.setEsito(esito);
        return risposta;
    }
    
    private Esito salvaUtente(Utente utente) {
    		Esito esito = FunzioniUtils.getEsitoPositivo();
    		AmazonDynamoDB clientUser = null;
    		
		try {
			clientUser = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(this.getClass().getName() + " - " +  EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putUtente ");
			esito.setTrace(e1.getMessage());
			return esito;
		}
		
		DynamoDBMapper mapper = new DynamoDBMapper(clientUser);
		
		Utente utenteDaSalvare = getUtenteModificato(utente, mapper);
		
		try {
			mapper.save(utenteDaSalvare);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(this.getClass().getName() + " - " +  EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Utente " + utente.getIdUtente());
			esito.setTrace(e.getMessage());
			return esito;
		}
		
		return esito;
    }
    
    private Utente getUtenteModificato(Utente utenteRicevuto, DynamoDBMapper mapper) {
		
		Utente utenteDB = mapper.load(Utente.class, utenteRicevuto.getIdUtente());
		
		if(utenteDB != null) {
			if(utenteRicevuto.getBiografiaUtente() != null) {
    				utenteDB.setBiografiaUtente(utenteRicevuto.getBiografiaUtente());
	    		}
	    		if(utenteRicevuto.getCittaUtente() != null) {
	    			utenteDB.setCittaUtente(utenteRicevuto.getCittaUtente());
	    		}
	    		if(utenteRicevuto.getCognomeUtente() != null) {
	    			utenteDB.setCognomeUtente(utenteRicevuto.getCognomeUtente());
	    		}
	    		if(utenteRicevuto.getCreditiUtente() != 0) {
	    			utenteDB.setCreditiUtente(utenteRicevuto.getCreditiUtente());
	    		}
	    		if(utenteRicevuto.getEmailUtente() != null) {
	    			utenteDB.setEmailUtente(utenteRicevuto.getEmailUtente());
	    		}
	    		if(utenteRicevuto.getEsperienzaUtente() != 0) {
	    			utenteDB.setEsperienzaUtente(utenteRicevuto.getEsperienzaUtente());
	    		}
	    		if(utenteRicevuto.getLivelloUtente() != null) {
	    			utenteDB.setLivelloUtente(utenteRicevuto.getLivelloUtente());
	    		}
	    		if(utenteRicevuto.getNomeUtente() != null) {
	    			utenteDB.setNomeUtente(utenteRicevuto.getNomeUtente());
	    		}
	    		if(utenteRicevuto.getProfessioneUtente() != null) {
	    			utenteDB.setProfessioneUtente(utenteRicevuto.getProfessioneUtente());
	    		}
	    		if(utenteRicevuto.getUrlFotoUtente() != null) {
	    			utenteDB.setUrlFotoUtente(utenteRicevuto.getUrlFotoUtente());
	    		}
	    		if(utenteRicevuto.getUsernameUtente() != null) {
	    			utenteDB.setUsernameUtente(utenteRicevuto.getUsernameUtente());
	    		}
		} else {
			utenteDB = utenteRicevuto;
			
			if(utenteDB.getIdUtente() == null || utenteDB.getIdUtente().equals("")) {
				utenteDB.setIdUtente(FunzioniUtils.getEntitaId());
			}
		}
		
		return utenteDB;
	
}

}
