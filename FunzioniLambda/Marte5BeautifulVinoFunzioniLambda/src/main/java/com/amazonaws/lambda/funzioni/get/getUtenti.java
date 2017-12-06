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
import com.marte5.modello.Utente;
import com.marte5.modello.richieste.get.RichiestaGetUtenti;
import com.marte5.modello.risposte.get.RispostaGetUtenti;

public class getUtenti implements RequestHandler<RichiestaGetUtenti, RispostaGetUtenti> {

    @Override
    public RispostaGetUtenti handleRequest(RichiestaGetUtenti input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetUtenti risposta = getRisposta(input);
        return risposta;
    }
    
    private RispostaGetUtenti getRisposta(RichiestaGetUtenti input) {
    	
    		//controllo del token
    	
    		RispostaGetUtenti risposta = new RispostaGetUtenti();
    		Esito esito = FunzioniUtils.getEsitoPositivo();
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getUtenti ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr = new DynamoDBScanExpression();
			List<Utente> utenti;
			try {
				utenti = mapper.scan(Utente.class, expr);
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getUtenti ");
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
