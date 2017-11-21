package com.amazonaws.lambda.funzioni.update;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Utente;
import com.marte5.modello.richieste.update.RilchiestaUpdateUtente;
import com.marte5.modello.risposte.update.RispostaUpdateUtente;

public class updateUtente implements RequestHandler<RilchiestaUpdateUtente, RispostaUpdateUtente> {

    @Override
    public RispostaUpdateUtente handleRequest(RilchiestaUpdateUtente input, Context context) {
	    	context.getLogger().log("Input: " + input);
	    	RispostaUpdateUtente risposta = new RispostaUpdateUtente();
	    
	    //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
	    Esito esito = new Esito();
	    esito.setCodice(EsitoHelper.ESITO_OK_CODICE);
	    esito.setMessage(EsitoHelper.ESITO_OK_MESSAGGIO);
	    
	    AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " updateUtente ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			Utente utente = input.getUtente();
			if(utente == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
	        } else {
	        	
		        	long idUtente = input.getUtente().getIdUtente();
	        		if(idUtente == 0) { //0 è il valore di default se l'oggetto json è nullo per il tipo long
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " IdEvento NULL o DataEvento NULL");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " IdEvento NULL o DataEvento NULL");
	        		} else {
	        			try {
	    					mapper.save(utente);
	    				} catch (Exception e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
	    					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Utente " + input.getUtente().getIdUtente());
	    					esito.setTrace(e.getMessage());
	    				}
	        		}
		        
	        }
		}
		
		risposta.setEsito(esito);
		
		return risposta;	
    }

}
