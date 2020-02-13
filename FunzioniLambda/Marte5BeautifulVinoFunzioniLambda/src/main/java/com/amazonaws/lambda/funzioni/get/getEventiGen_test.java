package com.amazonaws.lambda.funzioni.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Utente;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getEventiGen_test implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetGenerica risposta = getRisposta(input);
       return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    	
    		//controllo del token
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
    		String idUltimoEvento = input.getIdUltimoEvento();
    		long dataUltimoEvento = input.getDataUltimoEvento();
    		String idUtente = input.getIdUtente();
    		
    		String elencoCompleto = input.getElencoCompleto();
    		
    		Esito esito = FunzioniUtils.getEsitoPositivo();
    		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
        int scannedCount = 0;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEventi ");
			esito.setTrace(e1.getMessage());
			esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
		}	
            
		risposta.setNumTotEventi(scannedCount);
		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
		risposta.setEsito(esito);
    		return risposta;
    }
}
