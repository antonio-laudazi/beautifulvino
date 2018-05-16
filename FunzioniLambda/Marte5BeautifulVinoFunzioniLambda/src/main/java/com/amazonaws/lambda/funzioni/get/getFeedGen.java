package com.amazonaws.lambda.funzioni.get;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Feed;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getFeedGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        
    		RispostaGetGenerica risposta = getRisposta(input);
    		return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    	
    	RispostaGetGenerica risposta = new RispostaGetGenerica();
		String idUltimoFeed = input.getIdUltimoFeed();
		long dataUltimoFeed = input.getDataUltimoFeed();
		String elencoCompleto = input.getElencoCompleto();

		Esito esito = FunzioniUtils.getEsitoPositivo();
		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
		
		//scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
        int scannedCount = 0;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getFeed ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr = new DynamoDBScanExpression();
			//DynamoDBQueryExpression qexpr = new DynamoDBQueryExpression();
			
			//mettere i parametri di ricerca qui !!!!
			
			//ottengo il numero totale di elementi, esclusa la paginazione
			scannedCount = mapper.count(Feed.class, expr);
			
			//qexpr.withLimit(5);
			if(elencoCompleto == null || !elencoCompleto.equals("S")) {
					
					expr.withLimit(12);
					
					if((idUltimoFeed != null && !idUltimoFeed.equals("")) && dataUltimoFeed != 0) {
						//configuro la paginazione
						
						Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
						AttributeValue av1 = new AttributeValue();
						av1.setS("" + idUltimoFeed);
						AttributeValue av2 = new AttributeValue();
						av2.setN("" + dataUltimoFeed);
						exclusiveStartKey.put("idFeed", av1);
						exclusiveStartKey.put("dataFeed", av2);
						
						expr.setExclusiveStartKey(exclusiveStartKey);
						//qexpr.setExclusiveStartKey(exclusiveStartKey);
					}
			}
			
			//ottengo la 'pagina'
			ScanResultPage<Feed> page = mapper.scanPage(Feed.class, expr);
			risposta.setFeed(page.getResults());
		}	
            
		risposta.setNumTotFeed(scannedCount);
		risposta.setEsito(esito);
		return risposta;
    }

}
