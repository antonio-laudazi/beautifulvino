package com.amazonaws.lambda.funzioni.put;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Utente;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;

public class putUtenteGen implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {

    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
    		
    		RispostaPutGenerica risposta = new RispostaPutGenerica();
        
    		long idUtenteRisposta = 0;
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
			if(utente == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		long idUtente = utente.getIdUtente();
	        	
		        	if(idUtente == 0) {
	        			//insert
		        		idUtente = FunzioniUtils.getEntitaId();
		        } 
		        	idUtenteRisposta = idUtente;
	        		utente.setIdUtente(idUtente);
		        
		        try {
					mapper.save(utente);
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
}