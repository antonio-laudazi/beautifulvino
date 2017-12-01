package com.amazonaws.lambda.funzioni.put;

import java.util.Date;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.put.RichiestaPutAzienda;
import com.marte5.modello.risposte.put.RispostaPutAzienda;

public class putAzienda implements RequestHandler<RichiestaPutAzienda, RispostaPutAzienda> {
	
    @Override
    public RispostaPutAzienda handleRequest(RichiestaPutAzienda input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaPutAzienda risposta = new RispostaPutAzienda();
        
        Date actualDate = new Date();
        long idAzienda = actualDate.getTime();
        
        //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        Esito esito = new Esito();
        esito.setCodice(EsitoHelper.ESITO_OK_CODICE);
        esito.setMessage(EsitoHelper.ESITO_OK_MESSAGGIO);
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putAzienda ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);

	        Azienda azienda = input.getAzienda();
	        if(azienda == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Azienda NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Azienda NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		azienda.setIdAzienda(idAzienda);
		        
		        try {
					mapper.save(azienda);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Vino " + input.getAzienda().getIdAzienda());
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
	        }
		}	
        risposta.setEsito(esito);
        risposta.setIdAzienda(idAzienda);
        return risposta;
    }
}
