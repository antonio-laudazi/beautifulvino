package com.amazonaws.lambda.funzioni.get.backup;

import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.get.RichiestaGetAziende;
import com.marte5.modello.risposte.get.RispostaGetAziende;

public class getAziende implements RequestHandler<RichiestaGetAziende, RispostaGetAziende> {

    @Override
    public RispostaGetAziende handleRequest(RichiestaGetAziende input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetAziende risposta = getRisposta(input);
        return risposta;
    }
    
    private RispostaGetAziende getRisposta(RichiestaGetAziende input) {
    	
    		//controllo del token
    	
    		RispostaGetAziende risposta = new RispostaGetAziende();
    		Esito esito = FunzioniUtils.getEsitoPositivo();
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getAziende ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr = new DynamoDBScanExpression();
			List<Azienda> aziende;
			try {
				aziende = mapper.scan(Azienda.class, expr);
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getAziende ");
				esito.setTrace(e.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			risposta.setAziende(aziende);
		}	
		
		risposta.setEsito(esito);
    		return risposta;
    }
}
