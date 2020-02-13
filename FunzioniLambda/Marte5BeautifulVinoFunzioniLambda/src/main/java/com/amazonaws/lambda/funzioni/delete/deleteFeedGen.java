package com.amazonaws.lambda.funzioni.delete;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.delete.RichiestaDeleteGenerica;
import com.marte5.modello.risposte.delete.RispostaDeleteGenerica;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Feed;
import com.marte5.modello2.Feed.EventoFeed;
import com.marte5.modello2.Feed.VinoFeed;
import com.marte5.modello2.Vino;

public class deleteFeedGen implements RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica> {

    @Override
    public RispostaDeleteGenerica handleRequest(RichiestaDeleteGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaDeleteGenerica risposta = new RispostaDeleteGenerica();

        String idFeed = input.getIdFeed();
        long dataFeed = input.getDataFeed();
        
        Esito esito = new Esito();
        esito.setCodice(EsitoHelper.ESITO_OK_CODICE);
        esito.setMessage(EsitoHelper.ESITO_OK_MESSAGGIO);
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " deleteFeed ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idFeed == null || idFeed.equals("") || dataFeed == 0) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdFeed NULL oppure DataFeed NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdFeed NULL oppure DataFeed NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		Feed feedDaCancellare = mapper.load(Feed.class, idFeed, dataFeed );
	        		if(feedDaCancellare == null) {
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Feed con id: " + idFeed + " non trovato sul database");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Feed con id: " + idFeed + " non trovato sul database");
	    				risposta.setEsito(esito);
	    				return risposta;
	        		} else {
        			
	        			try {
						mapper.delete(feedDaCancellare);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
		    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Eccezione nell'operazione interna di salvataggio");
		    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + e.getMessage());
		    				risposta.setEsito(esito);
		    				return risposta;
					}
	        			
	        			//cancello eventuale immagine del feed
	        			String immagineFeedUrl = feedDaCancellare.getUrlImmagineFeed();
	        			if(immagineFeedUrl != null) {
	        				if(!immagineFeedUrl.equals("")) {
		        				esito = FunzioniUtils.cancellaImmagine(immagineFeedUrl);
		        			}
	        			}
	        			
	        			//cancello eventuale immagine header del feed
	        			String immagineHaderFeedUrl = feedDaCancellare.getUrlImmagineHeaderFeed();
	        			if(immagineHaderFeedUrl != null) {
	        				if(!immagineHaderFeedUrl.equals("")) {
		        				FunzioniUtils.cancellaImmagine(immagineHaderFeedUrl);
		        			}
	        			}
	        			
	        		}
	        }
		}
        
		risposta.setEsito(esito);
        // TODO: implement your handler
        return risposta;
    }
}
