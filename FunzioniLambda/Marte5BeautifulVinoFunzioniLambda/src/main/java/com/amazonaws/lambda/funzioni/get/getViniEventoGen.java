package com.amazonaws.lambda.funzioni.get;

import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello2.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.VinoEvento;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getViniEventoGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        
    		RispostaGetGenerica risposta = getRisposta(input);
    		return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    		RispostaGetGenerica risposta = new RispostaGetGenerica();

    		String idEvento = input.getIdEvento();
    		long dataEvento = input.getDataEvento();
    		
		Evento evento = new Evento();
    		Esito esito = FunzioniUtils.getEsitoPositivo();
    		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
		
		//scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
		AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getVini ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			if(idEvento == null || idEvento.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idEvento nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			if(dataEvento == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " dataEvento nulla, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			evento = mapper.load(Evento.class, idEvento, dataEvento);
			if(evento == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " evento non trovato sul database, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			List<VinoEvento> viniEvento = evento.getViniEventoInt();
			List<Azienda> aziende = FunzioniUtils.riordinaViniAzienda_Evento(viniEvento, mapper);
			
			risposta.setAziende(aziende);
		}	
		
		risposta.setEsito(esito);
    		return risposta;
    }
}
