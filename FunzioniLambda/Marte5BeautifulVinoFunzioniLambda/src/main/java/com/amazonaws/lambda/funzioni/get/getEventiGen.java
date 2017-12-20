package com.amazonaws.lambda.funzioni.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
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
import com.marte5.modello.Utente;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getEventiGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetGenerica risposta = getRisposta(input);
       return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    	
    		//controllo del token
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
    		long idUltimoEvento = input.getIdUltimoEvento();
    		long dataUltimoEvento = input.getDataUltimoEvento();
    		long idUtente = input.getIdUtente();
    		
    		Esito esito = FunzioniUtils.getEsitoPositivo();
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
        int scannedCount = 0;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEventi ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr = new DynamoDBScanExpression();

			//ottengo il numero totale di elementi, esclusa la paginazione
			scannedCount = mapper.count(Evento.class, expr);
			expr.withLimit(12);
			//qexpr.withLimit(5);
			
			if(idUltimoEvento != 0 && dataUltimoEvento != 0) {
				//configuro la paginazione
				
				Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
				AttributeValue av1 = new AttributeValue();
				av1.setN("" + idUltimoEvento);
				AttributeValue av2 = new AttributeValue();
				av2.setN("" + dataUltimoEvento);
				exclusiveStartKey.put("idEvento", av1);
				exclusiveStartKey.put("dataEvento", av2);
				
				expr.setExclusiveStartKey(exclusiveStartKey);
				//qexpr.setExclusiveStartKey(exclusiveStartKey);
			}
			//ottengo la 'pagina'
			//QueryResultPage<Evento> qpage = mapper.queryPage(Evento.class, qexpr);
			ScanResultPage<Evento> page = mapper.scanPage(Evento.class, expr);
			
			List<Evento> eventi = page.getResults();
			Utente utente = mapper.load(Utente.class, idUtente);
			if(utente != null) {
				for (Evento evento : eventi) {
					String statoEvento = FunzioniUtils.EVENTO_STATO_NEUTRO;
					try {
						statoEvento = FunzioniUtils.getStatoEvento(utente, evento.getIdEvento(), evento.getDataEvento(), mapper);
					} catch (Exception e) {
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
						esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEventi: errore nell'estrazione delle associazioni degli eventi preferiti");
						esito.setTrace(e.getMessage());
						risposta.setEsito(esito);
						return risposta;
					}
					evento.setStatoEvento(statoEvento);
				}
			}
			
			risposta.setEventi(page.getResults());
		}	
            
		risposta.setNumTotEventi(scannedCount);
		risposta.setEsito(esito);
    		return risposta;
    }
}
