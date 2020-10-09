package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getEventiAziendaGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetGenerica risposta = getRisposta(input);
       return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    	
			//controllo del token
			RispostaGetGenerica risposta = new RispostaGetGenerica();
			String idAzienda = input.getIdAzienda();
			
			Esito esito = FunzioniUtils.getEsitoPositivo();
			esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
	    
			if(idAzienda == null || idAzienda.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idAzienda nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
	    		
	        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
	        AmazonDynamoDB client = null;
			try {
				client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
			} catch (Exception e1) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEventi ");
				esito.setTrace(e1.getMessage());
				esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			if(client != null) {
				DynamoDBMapper mapper = new DynamoDBMapper(client);
				DynamoDBScanExpression expr = new DynamoDBScanExpression();			
				
				ArrayList<AttributeValue> attributi = new ArrayList<AttributeValue>();
				AttributeValue attributo = new AttributeValue(idAzienda);
				attributi.add(attributo);
				expr.addFilterCondition("idAziendaOspitanteEvento", new Condition().withComparisonOperator(ComparisonOperator.CONTAINS).withAttributeValueList(attributi));				
				
		        List<Evento> eventi;
				try {
					eventi = mapper.scan(Evento.class, expr);
				} catch (Exception e1) {
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
					esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEventiAzienda ");
					esito.setTrace(e1.getMessage());
					esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
				
				risposta.setEventi(eventi);
				risposta.setNumTotEventi(eventi.size());
			}	
			
			esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
			risposta.setEsito(esito);
    		return risposta;
    }
}
