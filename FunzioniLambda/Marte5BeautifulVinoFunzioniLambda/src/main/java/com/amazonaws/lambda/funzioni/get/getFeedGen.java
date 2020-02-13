package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Feed;

public class getFeedGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        
    		RispostaGetGenerica risposta = getRisposta(input);
    		return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    	
    	RispostaGetGenerica risposta = new RispostaGetGenerica();
		String idUltimoFeed = input.getIdUltimoFeed();
		long dataUltimoFeed = input.getDataUltimoFeed();
		String elencoCompleto = input.getElencoCompleto();

		Esito esito = FunzioniUtils.getEsitoPositivo();
		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
		
		//scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
        int scannedCount = 0;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getFeed ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr = new DynamoDBScanExpression();
			
			//DynamoDBQueryExpression qexpr = new DynamoDBQueryExpression();
			
			//mettere i parametri di ricerca qui !!!!
			
			//ottengo il numero totale di elementi, esclusa la paginazione
			//scannedCount = mapper.count(Feed.class, expr);
			
			//qexpr.withLimit(5);
//			if(elencoCompleto == null || !elencoCompleto.equals("S")) {
//					
//					expr.withLimit(12);
//					
//					if((idUltimoFeed != null && !idUltimoFeed.equals("")) && dataUltimoFeed != 0) {
//						//configuro la paginazione
//						
//						Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
//						AttributeValue av1 = new AttributeValue();
//						av1.setS("" + idUltimoFeed);
//						AttributeValue av2 = new AttributeValue();
//						av2.setN("" + dataUltimoFeed);
//						exclusiveStartKey.put("idFeed", av1);
//						exclusiveStartKey.put("dataFeed", av2);
//						
//						expr.setExclusiveStartKey(exclusiveStartKey);
//						//qexpr.setExclusiveStartKey(exclusiveStartKey);
//					}
//			}
			
			//ottengo la 'pagina'
			ScanResultPage<Feed> page = mapper.scanPage(Feed.class, expr);
			List<Feed> listaFeed = page.getResults();
			scannedCount = 0;
			for (Feed l: listaFeed) {
				if (l.getPubblicato() == true) {
					scannedCount ++;
				}
			}
			//riordino i feed per data
			Collections.sort(listaFeed, new Comparator<Feed>(){
				@Override
				public int compare(Feed arg0, Feed arg1) {
					long d0 = arg0.getDataFeed();
					long d1 = arg1.getDataFeed();
					return (Long.compare(d1, d0));
				}
		      });
			//listaFeed = reverse(listaFeed);
			//ne invio solo 112
			if (listaFeed != null && (!(elencoCompleto != null && elencoCompleto.equalsIgnoreCase("S"))) ) {
				List<Feed> tempFeed = new ArrayList<>();
				
				for (Feed f :listaFeed) {
					if (f.getPubblicato() == true) {
						tempFeed.add(f);
					}
				}
				listaFeed = tempFeed;
				if ((idUltimoFeed != null && !idUltimoFeed.equals("")  ) ) {
					for (Feed f : listaFeed) {
						if (f.getIdFeed().equals(idUltimoFeed)) {
							int idf = listaFeed.indexOf(f);
							List<Feed> temp = new ArrayList<Feed>();
							for (int i = idf + 1 ; i < idf + 13 ; i++) {
								if (i >= listaFeed.size()) break;
								if (listaFeed.get(i) != null) {
									temp.add(listaFeed.get(i));
								}
							}
							listaFeed = temp;
						}
					}
				}else {
					List<Feed> temp = new ArrayList<Feed>();
					for (int i = 0; i < 12 ; i++) {
						if (i >= listaFeed.size()) break;
						if (listaFeed.get(i) != null) {
							temp.add(listaFeed.get(i));
						}
					}
					listaFeed = temp;
				}
			}
			
			risposta.setFeed(listaFeed);
	
		}	
            
		risposta.setNumTotFeed(scannedCount);
		risposta.setEsito(esito);
		return risposta;
    }
    
//    public List<Feed> reverse(List<Feed> list) {
//        if(list.size() > 1) {                   
//            Feed value = list.remove(0);
//            reverse(list);
//            list.add(value);
//        }
//        return list;
//    }
    
//    public List<Feed> reverse(List<Feed> list) {
//        int length = list.size();
//        List<Feed> result = new ArrayList<>();
//
//        for (int i = length - 1; i >= 0; i--) {
//            result.add(list.get(i));
//        }
//
//        return result;
//    }

}
