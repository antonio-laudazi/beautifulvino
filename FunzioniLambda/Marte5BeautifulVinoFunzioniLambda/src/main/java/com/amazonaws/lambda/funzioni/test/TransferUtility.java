package com.amazonaws.lambda.funzioni.test;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Azienda;
import com.marte5.modello.Azienda.VinoAzienda;
import com.marte5.modello.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.Provincia;
import com.marte5.modello.Vino;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class TransferUtility implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaGetGenerica risposta = getRisposta(input);
        return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
        long idAzienda = input.getIdAzienda();
        
        Azienda azienda = new Azienda();
    		
        Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage("Richiesta Azienda gestit correttamente per l'azienda: " + input.getIdAzienda());
        
        AmazonDynamoDB client = null;
        try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEvento ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//	        List<Azienda> scanResult = mapper.scan(Azienda.class, scanExpression);
//
//	        for (Azienda az : scanResult) {
//	        	
//	        		com.marte5.modello2.Azienda az2 = new com.marte5.modello2.Azienda();
//	        		az2.setIdAzienda(az.getIdAzienda()+"");
//	        		az2.setNomeAzienda(az.getNomeAzienda());
//	        		az2.setLatitudineAzienda(az.getLatitudineAzienda());
//	        		az2.setLongitudineAzienda(az.getLongitudineAzienda());
//	        		az2.setUrlImmagineAzienda(az.getUrlImmagineAzienda());
//	        		
//	        		ArrayList<com.marte5.modello2.Azienda.VinoAzienda> vinoAzienda2 = new ArrayList<>();
//	        		for (VinoAzienda vinoazienda : az.getViniAziendaInt()) {
//						com.marte5.modello2.Azienda.VinoAzienda va = new com.marte5.modello2.Azienda.VinoAzienda();
//						va.setIdVino(vinoazienda.getIdVino()+"");
//						va.setAnnoVino(vinoazienda.getAnnoVino());
//						va.setNomeVino(vinoazienda.getNomeVino());
//						vinoAzienda2.add(va);
//					}
//	        		az2.setViniAziendaInt(vinoAzienda2);
//	        		
//	        		mapper.save(az2);
//	        		
//	        }
	        
	        List<Provincia> scanResult = mapper.scan(Provincia.class, scanExpression);

	        for (Provincia az : scanResult) {
	        	
	        		com.marte5.modello2.Provincia az2 = new com.marte5.modello2.Provincia();
	        		
	        		az2.setIdProvincia(az.getIdProvincia()+"");
	        		az2.setNomeProvincia(az.getNomeProvincia());
	        		az2.setSiglaProvincia(az.getSiglaProvincia());
	        		
	        		mapper.save(az2);
	        		
	        }
			
			
			
		}
        
        risposta.setAzienda(azienda);
        risposta.setEsito(esito);
        return risposta;
    }
    
}
