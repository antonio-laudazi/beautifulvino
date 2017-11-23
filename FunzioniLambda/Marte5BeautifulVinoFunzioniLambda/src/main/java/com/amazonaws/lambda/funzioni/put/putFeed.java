package com.amazonaws.lambda.funzioni.put;

import java.util.Date;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Feed;
import com.marte5.modello.richieste.put.RichiestaPutFeed;
import com.marte5.modello.risposte.put.RispostaPutFeed;

public class putFeed implements RequestHandler<RichiestaPutFeed, RispostaPutFeed> {
	
    @Override
    public RispostaPutFeed handleRequest(RichiestaPutFeed input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaPutFeed risposta = new RispostaPutFeed();
        
        Date actualDate = new Date();
        long idFeed = actualDate.getTime();
        
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
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putFeed ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);

	        Feed feed = input.getFeed();
	        if(feed == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Feed NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Feed NULL");
	        } else {
	        		feed.setIdFeed(idFeed);
		        
		        try {
					mapper.save(feed);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Evento " + input.getFeed().getIdFeed());
					esito.setTrace(e.getMessage());
				}
	        }
		}	
        risposta.setEsito(esito);
        risposta.setIdFeed(idFeed);
        return risposta;
    }
}
