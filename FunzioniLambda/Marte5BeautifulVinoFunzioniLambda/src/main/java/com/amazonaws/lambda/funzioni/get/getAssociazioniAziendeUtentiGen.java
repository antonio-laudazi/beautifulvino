package com.amazonaws.lambda.funzioni.get;

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
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello2.AssociazioneAziendaUtente;

public class getAssociazioniAziendeUtentiGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica>{

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
    
	    AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getAziende ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	        eav.put(":val1", new AttributeValue().withN(input.getIdAzienda()));
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr = new DynamoDBScanExpression().withFilterExpression("IdAzienda = :val1").withExpressionAttributeValues(eav);
			List<AssociazioneAziendaUtente> associazioni;
			try {
				associazioni = mapper.scan(AssociazioneAziendaUtente.class, expr);
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getAziende ");
				esito.setTrace(e.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			risposta.setAssociazioniAziendeUtenti(associazioni);
		}	
		
		risposta.setEsito(esito);
			return risposta;
	}

}
