package com.amazonaws.lambda.funzioni.add;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Utente;
import com.marte5.modello.richieste.add.RichiestaAddCreditiUtente;
import com.marte5.modello.risposte.add.RispostaAddCreditiAUtente;

public class addCreditiUtente implements RequestHandler<RichiestaAddCreditiUtente, RispostaAddCreditiAUtente> {

    @Override
    public RispostaAddCreditiAUtente handleRequest(RichiestaAddCreditiUtente input, Context context) {
	    	context.getLogger().log("Input: " + input);
	    	RispostaAddCreditiAUtente risposta = new RispostaAddCreditiAUtente();
	    
	    Esito esito = FunzioniUtils.getEsitoPositivo();
	    long idUtente = input.getIdUtente();
	    int creditiDaAggiungere = input.getCreditiDaAggiungere();
	    
	    AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " addCreditiAUtente ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			if(idUtente == 0) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " idUtente NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " idUtente NULL");
				risposta.setEsito(esito);
				return risposta;
	        }
			if(creditiDaAggiungere == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " non ci sono crediti da aggiungere");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " non ci sono crediti da aggiungere");
				risposta.setEsito(esito);
				return risposta;
			}
			Utente utente = mapper.load(Utente.class, idUtente);
			if(utente == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        	
		        utente.setCreditiUtente(utente.getCreditiUtente() + creditiDaAggiungere);
		        try {
					mapper.save(utente);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Aggiornamento utente dopoo aggiunta crediti");
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
	        }
		}
		
		risposta.setEsito(esito);
		
		return risposta;	
    }

}
