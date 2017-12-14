package com.amazonaws.lambda.funzioni.get;

import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Vino;
import com.marte5.modello.richieste.get.RichiestaGetVini;
import com.marte5.modello.risposte.get.RispostaGetVini;

public class getVini implements RequestHandler<RichiestaGetVini, RispostaGetVini> {

    @Override
    public RispostaGetVini handleRequest(RichiestaGetVini input, Context context) {
        
    	RispostaGetVini risposta = getRisposta(input);
    		return risposta;
    }
    
    private RispostaGetVini getRisposta(RichiestaGetVini input) {
    		RispostaGetVini risposta = new RispostaGetVini();

		Esito esito = FunzioniUtils.getEsitoPositivo();
		
		//scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
		AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getVini ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr = new DynamoDBScanExpression();
			List<Vino> vini;
			try {
				vini = mapper.scan(Vino.class, expr);
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getVini ");
				esito.setTrace(e.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			risposta.setVini(vini);
		}	
		
		risposta.setEsito(esito);
    		return risposta;
    }
}
