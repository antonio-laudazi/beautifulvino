package com.amazonaws.lambda.funzioni.get;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.richieste.get.RichiestaGetEventi;
import com.marte5.modello.risposte.get.RispostaGetEventi;

public class getEventi implements RequestHandler<RichiestaGetEventi, RispostaGetEventi> {

    @Override
    public RispostaGetEventi handleRequest(RichiestaGetEventi input, Context context) {
        context.getLogger().log("Input: " + input);
        
//        try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        RispostaGetEventi risposta = getRisposta(input);
        //RispostaGetEventi risposta = getRispostaDiTest(input);
        return risposta;
    }
    
    private RispostaGetEventi getRisposta(RichiestaGetEventi input) {
    	
    		//controllo del token
    	
    		RispostaGetEventi risposta = new RispostaGetEventi();
    		long idUltimoEvento = input.getIdUltimoEvento();
    		long dataUltimoEvento = input.getDataUltimoEvento();
    		
    		Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage("Esito corretto per la richiesta getEventi");
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
        int scannedCount = 0;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putEvento ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr = new DynamoDBScanExpression();
			
			//mettere i parametri di ricerca qui !!!!
			
			//ottengo il numero totale di elementi, esclusa la paginazione
			scannedCount = mapper.count(Evento.class, expr);
			
			if(idUltimoEvento != 0 && dataUltimoEvento != 0) {
				//configuro la paginazione
				expr.withLimit(4);
				
				Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
				AttributeValue av1 = new AttributeValue();
				av1.setN("" + idUltimoEvento);
				AttributeValue av2 = new AttributeValue();
				av2.setN("" + dataUltimoEvento);
				exclusiveStartKey.put("idEvento", av1);
				exclusiveStartKey.put("dataEvento", av2);
				
				expr.setExclusiveStartKey(exclusiveStartKey);
			}
			//ottengo la 'pagina'
			ScanResultPage<Evento> page = mapper.scanPage(Evento.class, expr);
			risposta.setEventi(page.getResults());
		}	
            
		risposta.setNumTotEventi(scannedCount);
		risposta.setEsito(esito);
    		return risposta;
    }
//    
//    private RispostaGetEventi getRispostaDiTest(RichiestaGetEventi input) {
//    	
//    		RispostaGetEventi risposta = new RispostaGetEventi();
//
//        Esito esito = new Esito();
//        esito.setCodice(100);
//        esito.setMessage("Esito corretto per la richiesta getEventi");
//        
//        List<Evento> eventi = new ArrayList<>();
//		for(int i = 0; i < 7; i++) {
//			Evento evento = new Evento();
//			
//			evento.setIdEvento((new Date()).getTime());
//			evento.setTitoloEvento("Titolo Evento " + (i+1));
//			evento.setTestoEvento("Questo è il testo per l'evento " + (i+1) + " che descrive quello che troverete durante l'evento.");
//			evento.setTemaEvento("Tema evento " + (i+1));
//			evento.setIndirizzoEvento("Via Dell'evento " + (i+1) + ", Citta, Provincia, CAP" );
//			evento.setLuogoEvento("Luogo dell'evento");
//			evento.setDataEvento((new Date()).getTime());
//			evento.setDataEventoStringa(FunzioniUtils.getStringVersion(new Date()));
//			evento.setUrlFotoEvento("");
//			evento.setStatoEvento("N");
//			evento.setLatitudineEvento(43.313333);
//			evento.setLongitudineEvento(10.518434);
//			evento.setNumMaxPartecipantiEvento(20);
//			evento.setNumPostiDisponibiliEvento(7);
//			
//			eventi.add(evento);
//			
//		}
//        
//        int eventiSize = eventi.size();
//        
//        risposta.setEsito(esito);
//        risposta.setEventi(eventi);
//        risposta.setNumTotEventi(eventiSize);
//        
//        // TODO: implement your handler
//        return risposta;
//    }

}
