package com.amazonaws.lambda.funzioni.update;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.richieste.update.RichiestaUpdateEvento;
import com.marte5.modello.risposte.update.RispostaUpdateEvento;

public class updateEvento implements RequestHandler<RichiestaUpdateEvento, RispostaUpdateEvento> {

    @Override
    public RispostaUpdateEvento handleRequest(RichiestaUpdateEvento input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaUpdateEvento risposta = new RispostaUpdateEvento();

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
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putEvento ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);

	        Evento evento = input.getEvento();
	        if(evento == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Evento NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Evento NULL");
	        } else {
	        	
	        		//visto che è un update controllo l'id
	        		long idEvento = input.getEvento().getIdEvento();
	        		long dataEvento = input.getEvento().getDataEvento();
	        		if(idEvento == 0 || dataEvento == 0) { //0 è il valore di default se l'oggetto json è nullo per il tipo long
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " IdEvento NULL o DataEvento NULL");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " IdEvento NULL o DataEvento NULL");
	        		} else {
	        			try {
	    					mapper.save(evento);
	    				} catch (Exception e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
	    					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Evento " + input.getEvento().getIdEvento());
	    					esito.setTrace(e.getMessage());
	    				}
	        		}
	        }
		}	
        risposta.setEsito(esito);
        risposta.setIdEvento(input.getEvento().getIdEvento());
        return risposta;
    }

}
