package com.amazonaws.lambda.funzioni.put.backup;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.put.RichiestaPutBadge;
import com.marte5.modello.risposte.put.RispostaPutBadge;

public class putBadge implements RequestHandler<RichiestaPutBadge, RispostaPutBadge> {
	
    @Override
    public RispostaPutBadge handleRequest(RichiestaPutBadge input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaPutBadge risposta = new RispostaPutBadge();
        
        long idBadge = FunzioniUtils.getEntitaId();
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putBadge ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);

	        Badge badge = input.getBadge();
	        if(badge == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Badge NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Badge NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		badge.setIdBadge(idBadge);
		        
		        try {
					mapper.save(badge);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Vino " + input.getBadge().getIdBadge());
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
	        }
		}	
        risposta.setEsito(esito);
        risposta.setIdBadge(idBadge);
        return risposta;
    }
}
