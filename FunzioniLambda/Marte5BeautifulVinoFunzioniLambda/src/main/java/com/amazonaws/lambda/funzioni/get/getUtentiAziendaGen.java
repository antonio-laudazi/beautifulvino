package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.marte5.modello2.ProfiloAzienda;
import com.marte5.modello2.Utente;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getUtentiAziendaGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetGenerica risposta = getRisposta(input);
        return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    	
    		//controllo del token
    	
		RispostaGetGenerica risposta = new RispostaGetGenerica();
		Esito esito = FunzioniUtils.getEsitoPositivo();
		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
		
		String idAzienda = input.getIdAzienda();
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getUtenti ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr1 = new DynamoDBScanExpression();
					
			if(idAzienda == null || idAzienda.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			ArrayList<AttributeValue> attributi = new ArrayList<AttributeValue>();
			AttributeValue attributo = new AttributeValue(input.getIdAzienda());
			attributi.add(attributo);
			expr1.addFilterCondition("idAzienda", new Condition().withComparisonOperator(ComparisonOperator.CONTAINS).withAttributeValueList(attributi));			
			
//			Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
//	        eav.put(":val1", new AttributeValue().withN(input.getIdAzienda()));
	        
	        // DynamoDBScanExpression expr1 = new DynamoDBScanExpression().withFilterExpression("IdAzienda < :val1").withExpressionAttributeValues(eav);
	        List<ProfiloAzienda> profili;
			try {
				profili = mapper.scan(ProfiloAzienda.class, expr1);
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getAziende ");
				esito.setTrace(e.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			if(profili.size() == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " profilo azienda non trovato ");
				risposta.setEsito(esito);
				return risposta;
			}
			if(profili.size() > 1) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " trovato piu' di un profilo con questo id azienda; c'è una anomalia sul DB ");
				risposta.setEsito(esito);
				return risposta;
			}
			
			ProfiloAzienda profilo = profili.get(0);
			
			DynamoDBScanExpression expr2 = new DynamoDBScanExpression();
			ArrayList<AttributeValue> attributi2 = new ArrayList<AttributeValue>();
			AttributeValue attributo2 = new AttributeValue(profilo.getIdProfiloAzienda());
			attributi2.add(attributo2);
			expr2.addFilterCondition("idProfiloAziendaUtente", new Condition().withComparisonOperator(ComparisonOperator.CONTAINS).withAttributeValueList(attributi2));
//	        
//			Map<String, AttributeValue> eav2= new HashMap<String, AttributeValue>();
//	        eav.put(":val1", new AttributeValue().withN(profilo.getIdProfiloAzienda()));
//			DynamoDBScanExpression expr2 = new DynamoDBScanExpression().withFilterExpression("idProfiloAziendaUtente < :val1").withExpressionAttributeValues(eav2);
			
			List<Utente> utenti;
			try {
				utenti = mapper.scan(Utente.class, expr2);
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getUtenti ");
				esito.setTrace(e.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			risposta.setUtenti(utenti);
		}	
		
		risposta.setEsito(esito);
    		return risposta;
    }
}
