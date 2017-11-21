package com.amazonaws.lambda.funzioni.put;

import java.util.Date;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Utente;
import com.marte5.modello.richieste.put.RichiestaPutUtente;
import com.marte5.modello.risposte.put.RispostaPutUtente;

public class putUtente implements RequestHandler<RichiestaPutUtente, RispostaPutUtente> {

    @Override
    public RispostaPutUtente handleRequest(RichiestaPutUtente input, Context context) {
    		
    		context.getLogger().log("Input: " + input);
    		RispostaPutUtente risposta = new RispostaPutUtente();
        
        Date actualDate = new Date();
        long idUtente = actualDate.getTime();
        
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
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putUtente ");
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
	        		utente.setIdUtente(idUtente);
		        
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
		
		risposta.setEsito(esito);
		risposta.setIdUtente(idUtente);
		
		return risposta;
    }

}
