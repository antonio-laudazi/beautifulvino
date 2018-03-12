package com.amazonaws.lambda.funzioni.put;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Utente;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;

public class putUtenteGen implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {

    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
    		
    		RispostaPutGenerica risposta = new RispostaPutGenerica();
        
    		String idUtenteRisposta = "";
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putUtente ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			Utente utente = input.getUtente();
			Utente utenteDaSalvare = new Utente();
			if(utente == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		String idUtente = utente.getIdUtente();
		        	if(idUtente == null || idUtente.equals("")) {
		        		//insert
		        		idUtente = FunzioniUtils.getEntitaId();
		        		utenteDaSalvare = utente;
		        		utente.setIdUtente(idUtente);
		        		idUtenteRisposta = idUtente;
		        }  else {
		        		//update
		        		utenteDaSalvare = getUtenteModificato(utente, mapper);
		        		idUtenteRisposta = utente.getIdUtente();
		        }
	        		
		        try {
					mapper.save(utenteDaSalvare);
				} catch (Exception e) {
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Utente " + input.getUtente().getIdUtente());
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
	        }
			risposta.setIdUtente(idUtenteRisposta);
		}
		
		risposta.setEsito(esito);
		return risposta;
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
    			utenteDB = new Utente();
    			utenteDB.setIdUtente(FunzioniUtils.getEntitaId());
    		}
    		
    		
    		return utenteDB;
    	
    }
}