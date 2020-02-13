package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Azienda.VinoAzienda;
import com.marte5.modello2.Vino;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getViniAziendaGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetGenerica risposta = getRisposta(input);
       return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    	
    		//controllo del token
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
    		String idAzienda = input.getIdAzienda();
    		
    		Esito esito = FunzioniUtils.getEsitoPositivo();
    		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getViniAzienda ");
			esito.setTrace(e1.getMessage());
			esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);

			if(idAzienda == null || idAzienda.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idAzienda nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			Azienda azienda = mapper.load(Azienda.class, idAzienda);
			if(azienda == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " azienda non trovata su DB, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			//gestione e recupero eventi associati all'utente
			List<VinoAzienda> viniAzienda = azienda.getViniAziendaInt();
			List<Vino> viniCompletiAzienda = new ArrayList<>();
			if(viniAzienda != null) {
				for (Iterator<VinoAzienda> iterator = viniAzienda.iterator(); iterator.hasNext();) {
					VinoAzienda vino = iterator.next();
					Vino vinoCompleto = mapper.load(Vino.class, vino.getIdVino());
					if(vinoCompleto != null) {
						vinoCompleto.setStatoVino(vino.getStatoVino());
						viniCompletiAzienda.add(vinoCompleto);
					}
				}
			}
			
			risposta.setVini(viniCompletiAzienda);
		}	
		
		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
		risposta.setEsito(esito);
    		return risposta;
    }
}
