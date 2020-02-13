package com.amazonaws.lambda.funzioni.common;

import java.util.Date;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.richieste.notifica.RichiestaNotificaGenerica;
import com.marte5.modello.risposte.notifiche.RispostaNotificaGenerica;
import com.marte5.modello2.Credenziali;
import com.marte5.modello.Esito;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class BeautifulVinoNotification implements RequestHandler<RichiestaNotificaGenerica, RispostaNotificaGenerica> {
	
    @Override
    public RispostaNotificaGenerica handleRequest(RichiestaNotificaGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaNotificaGenerica risposta = new RispostaNotificaGenerica();
        
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_PROCEDURA_LAMBDA);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " notifica ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);

	        String msg = input.getMessaggio();
	        
	        
	        if(msg == null) {
	        	esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_PROCEDURA_LAMBDA);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " notifica ");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " notifica ");
				risposta.setEsito(esito);
				return risposta;
	        } else {
    			Credenziali cred = mapper.load(Credenziali.class, 0);
    			if(cred == null) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " credenziali non trovate");
    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " notifica ");
    				risposta.setEsito(esito);
    				return risposta;
	        			
	        		}
    			//create a new SNS client and set endpoint
    			BasicAWSCredentials awsCreds = new BasicAWSCredentials(cred.getKey(), cred.getSecretKey());
    			AmazonSNS snsClient = AmazonSNSClient.builder()
						.withRegion("eu-central-1")
						.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
						.build();			
    			//publish to an SNS topic
    			System.out.println(msg);
    	        PublishRequest publishRequest = new PublishRequest("arn:aws:sns:eu-central-1:801532940274:test", msg);
    	        //PublishRequest publishRequest = new PublishRequest("arn:aws:sns:eu-central-1:801532940274:testIos", msg);
    	        publishRequest.setMessageStructure("json");
    	        PublishResult publishResult = null;
    	        try {
    	        	publishResult = snsClient.publish(publishRequest);
    	        }catch(Exception e) {
    	        	System.out.println(e.getMessage());
    	        	System.out.println(e.getStackTrace());
    	        	esito.setMessage(e.getMessage());
    	        	esito.setCodice(200);
    	        	risposta.setEsito(esito);
    	        	return risposta;
    	        }
    	        //print MessageId of message published to SNS topic
    	        System.out.println("MessageId - " + publishResult.getMessageId());
    	        
    	        
//    	        publishRequest = new PublishRequest("arn:aws:sns:eu-central-1:801532940274:android", msg);
//    	        publishRequest.setMessageStructure("json");
//    	        publishResult = null;
//    	        try {
//    	        	publishResult = snsClient.publish(publishRequest);
//    	        }catch(Exception e) {
//    	        	System.out.println(e.getMessage());
//    	        	System.out.println(e.getStackTrace());
//    	        	esito.setMessage(e.getMessage());
//    	        	esito.setCodice(200);
//    	        	risposta.setEsito(esito);
//    	        	return risposta;
//    	        }
//    	        //print MessageId of message published to SNS topic
//    	        System.out.println("MessageId - " + publishResult.getMessageId());
	        }
		}	
        risposta.setEsito(esito);
        
        return risposta;
    }
}