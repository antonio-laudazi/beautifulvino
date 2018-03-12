package com.amazonaws.lambda.funzioni.delete;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.richieste.delete.RichiestaDeleteGenerica;
import com.marte5.modello.risposte.delete.RispostaDeleteGenerica;

public class deleteEventoGen implements RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica> {

    @Override
    public RispostaDeleteGenerica handleRequest(RichiestaDeleteGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaDeleteGenerica risposta = new RispostaDeleteGenerica();

        long idEvento = input.getIdEvento();
        long dataEvento = input.getDataEvento();
        
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
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idEvento == 0 || dataEvento == 0) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdEvento NULL oppure DataEvento NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdEvento NULL oppure DataEvento NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		Evento eventoDaCancellare = mapper.load(Evento.class, idEvento, dataEvento );
	        		if(eventoDaCancellare == null) {
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Evento con id: " + idEvento + " non trovato sul database");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Evento con id: " + idEvento + " non trovato sul database");
	    				risposta.setEsito(esito);
	    				return risposta;
	        		} else {
	        			//caricato l'evento, lo vado a cancellare
	        			
	        			try {
						mapper.delete(eventoDaCancellare);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
		    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Eccezione nell'operazione interna di salvataggio");
		    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + e.getMessage());
		    				risposta.setEsito(esito);
		    				return risposta;
					}
	        			
	        			//cancello eventuale immagine dell'evento
	        			String immagineEventoUrl = eventoDaCancellare.getUrlFotoEvento();
	        			if(!immagineEventoUrl.equals("")) {
	        				esito = FunzioniUtils.cancellaImmagine(immagineEventoUrl);
	        			}
	        		}
	        }
		}
        
		risposta.setEsito(esito);
        // TODO: implement your handler
        return risposta;
    }
}
