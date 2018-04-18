package com.amazonaws.lambda.funzioni.put;


import java.util.Date;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Feed;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;

public class putFeedGen implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {
	
    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaPutGenerica risposta = new RispostaPutGenerica();
        
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putFeed ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);

	        Feed feed = input.getFeed();
	        if(feed == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Feed NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Feed NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        	
	        		String idFeed = feed.getIdFeed();
	        		long dataFeed = feed.getDataFeed();
	        		if(idFeed == null || idFeed.equals("")){
	        			idFeed = FunzioniUtils.getEntitaId();
	        			feed.setIdFeed(idFeed);
	        			if(dataFeed == 0L) {
	        				feed.setDataFeed((new Date()).getTime());
	        			}
	        		} else {
	        			Feed dbFeed = mapper.load(Feed.class, idFeed, dataFeed);
	        			if(dbFeed == null) {
	        				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
	        				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Non esiste su DB un feed con id " + idFeed);
	        				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Feed NULL");
	        				risposta.setEsito(esito);
	        				return risposta;
	        			}
	        		}
		        
		        try {
					mapper.save(feed);
				} catch (Exception e) {
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Evento " + input.getFeed().getIdFeed());
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
		        
		        risposta.setIdFeed(idFeed);
	        }
		}	
        risposta.setEsito(esito);
        
        return risposta;
    }
}
