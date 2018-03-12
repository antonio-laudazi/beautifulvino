package com.amazonaws.lambda.funzioni.delete.backup;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.Feed;
import com.marte5.modello.richieste.delete.RichiestaDeleteEvento;
import com.marte5.modello.richieste.delete.RichiestaDeleteFeed;
import com.marte5.modello.risposte.delete.RispostaDeleteEvento;
import com.marte5.modello.risposte.delete.RispostaDeleteFeed;

public class deleteFeed implements RequestHandler<RichiestaDeleteFeed, RispostaDeleteFeed> {

    @Override
    public RispostaDeleteFeed handleRequest(RichiestaDeleteFeed input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaDeleteFeed risposta = new RispostaDeleteFeed();

        long idFeed = input.getIdFeed();
        long dataFeed = input.getDataFeed();
        
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
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " deleteFeed ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idFeed == 0 || dataFeed == 0) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdFeed NULL oppure DataFeed NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdFeed NULL oppure DataFeed NULL");
	        } else {
	        		Feed feedDaCancellare = mapper.load(Feed.class, idFeed, dataFeed );
	        		if(feedDaCancellare == null) {
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Feed con id: " + idFeed + " non trovato sul database");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Feed con id: " + idFeed + " non trovato sul database");
	        		} else {
	        			//caricato l'evento, lo vado a cancellare
	        			
	        			try {
						mapper.delete(feedDaCancellare);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
		    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Eccezione nell'operazione interna di salvataggio");
		    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + e.getMessage());
					}
	        		}
	        }
		}
        
		risposta.setEsito(esito);
        // TODO: implement your handler
        return risposta;
    }
}
