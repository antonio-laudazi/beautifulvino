package com.amazonaws.lambda.funzioni.get;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.get.RichiestaGetBadge;
import com.marte5.modello.risposte.get.RispostaGetBadge;

public class getBadge implements RequestHandler<RichiestaGetBadge, RispostaGetBadge> {

    @Override
    public RispostaGetBadge handleRequest(RichiestaGetBadge input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaGetBadge risposta = getRisposta(input);
        return risposta;
    }
    
    private RispostaGetBadge getRisposta(RichiestaGetBadge input) {
    		RispostaGetBadge risposta = new RispostaGetBadge();
        long idBadge = input.getIdBadge();
        
        Esito esito = FunzioniUtils.getEsitoPositivo();
        Badge badge = new Badge();
        
        AmazonDynamoDB client = null;
        try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getBadge ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idBadge == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idBadge nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			badge = mapper.load(Badge.class, idBadge);
			
		}
        
        risposta.setBadge(badge);
        risposta.setEsito(esito);
        return risposta;
    }
}
