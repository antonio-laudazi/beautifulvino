package com.amazonaws.lambda.funzioni.put;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
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
        
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
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
	        	
		        	long idAzienda = azienda.getIdAzienda();
		        	
		        	if(idAzienda == 0) {
	        			//insert
		        		idAzienda = FunzioniUtils.getEntitaId();
		        } 
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
		        risposta.setIdAzienda(idAzienda);
	        }
		}	
        risposta.setEsito(esito);
        
        return risposta;
    }
}
