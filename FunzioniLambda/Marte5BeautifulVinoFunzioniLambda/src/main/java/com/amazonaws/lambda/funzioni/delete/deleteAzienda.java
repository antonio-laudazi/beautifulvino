package com.amazonaws.lambda.funzioni.delete;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.delete.RichiestaDeleteAzienda;
import com.marte5.modello.risposte.delete.RispostaDeleteAzienda;

public class deleteAzienda implements RequestHandler<RichiestaDeleteAzienda, RispostaDeleteAzienda> {

    @Override
    public RispostaDeleteAzienda handleRequest(RichiestaDeleteAzienda input, Context context) {
    		context.getLogger().log("Input: " + input);
    		RispostaDeleteAzienda risposta = new RispostaDeleteAzienda();

        long idAzienda = input.getIdAzienda();
        
        Esito esito = FunzioniUtils.getEsitoPositivo();
        
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
			if(idAzienda == 0) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " idAzienda NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " idAzienda NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		Azienda aziendaDaCancellare = mapper.load(Azienda.class, idAzienda);
	        		if(aziendaDaCancellare == null) {
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Azienda con id: " + idAzienda + " non trovato sul database");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Azienda con id: " + idAzienda + " non trovato sul database");
	    				risposta.setEsito(esito);
	    				return risposta;
	        		} else {
	        			//caricato l'evento, lo vado a cancellare
	        			
	        			try {
						mapper.delete(aziendaDaCancellare);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
		    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Eccezione nell'operazione interna di salvataggio");
		    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + e.getMessage());
		    				risposta.setEsito(esito);
		    				return risposta;
					}
	        		}
	        }
		}
        
		risposta.setEsito(esito);
        return risposta;
    }
}
