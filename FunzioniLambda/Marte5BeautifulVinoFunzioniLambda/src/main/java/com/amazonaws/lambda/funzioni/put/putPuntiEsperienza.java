package com.amazonaws.lambda.funzioni.put;

import java.util.ArrayList;
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
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;
import com.marte5.modello2.Livello;
import com.marte5.modello2.Utente;

public class putPuntiEsperienza implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {
	
	public static final int INFINITI_PUNTI_ESP = -1;
	
    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
    	
    	
        context.getLogger().log("Input: " + input);
        RispostaPutGenerica risposta = new RispostaPutGenerica();
        
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putFeed ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);

	        String idUtente = input.getIdUtente();
	        String idFeed = input.getIdFeed();
	        
	        Utente utente = mapper.load(Utente.class, idUtente);
	        List<String> feeds = utente.getFeedUtente();
	        if (feeds == null) {
	        	feeds = new ArrayList<String>();
	        }
	        boolean find = false;
	        
	        for (String f : feeds) {
	        	if ( f.equals(idFeed)) {
	        		find = true;
	        	}
	        }
	        if (find == false) {
	        	feeds.add(idFeed);
	        	utente.setFeedUtente(feeds);
	        	utente.setEsperienzaUtente(utente.getEsperienzaUtente() + 10);
	        	//gestione livello utente 
	    		int esp = utente.getEsperienzaUtente();
	    		utente.setLivelloUtente("unknown");
	    		DynamoDBScanExpression expr = new DynamoDBScanExpression();
	    		List<Livello> listaLivelli = mapper.scan(Livello.class, expr);
	    		for (Livello l : listaLivelli) {
	    			if (l.getMax() != INFINITI_PUNTI_ESP) {
	    				if (esp >= l.getMin() && esp <= l.getMax() ) {
	    					utente.setLivelloUtente(l.getNomeLivello());
	    					int gap = l.getMax() - esp + 1;
	    					String prox = "";
	    					for (Livello l1: listaLivelli) {
	    						if (l1.getMin() == l.getMax() + 1) prox = l1.getNomeLivello();
	    					}
	    					utente.setPuntiMancantiProssimoLivelloUtente("Per diventare " + prox + " ti mancano " + gap + " pt" );
	    					break;
	    				}
	    			}else {
	    				if (esp >= l.getMin() ) {
	    					utente.setLivelloUtente(l.getNomeLivello());
	    					utente.setPuntiMancantiProssimoLivelloUtente("Hai raggiunto il massimo livello" );
	    					break;
	    				}
	    			}
	    		}
	        	mapper.save(utente);
	        }else {
	        	esito.setCodice(EsitoHelper.ESITO_KO_CODICE_PUNTI_ESP);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_PUNTI_ESP);
				risposta.setEsito(esito);
				return risposta;
	        }	        
		}	
        risposta.setEsito(esito);
        return risposta;
    }
}
