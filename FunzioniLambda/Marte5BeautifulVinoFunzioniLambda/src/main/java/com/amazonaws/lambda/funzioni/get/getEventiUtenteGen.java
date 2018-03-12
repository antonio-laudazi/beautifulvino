package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.EventoUtente;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getEventiUtenteGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetGenerica risposta = getRisposta(input);
       return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    	
    		//controllo del token
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
    		String idUtente = input.getIdUtente();
    		
    		Esito esito = FunzioniUtils.getEsitoPositivo();
    		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEventi ");
			esito.setTrace(e1.getMessage());
			esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);

			if(idUtente == null || idUtente.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			Utente utente = mapper.load(Utente.class, idUtente);
			if(utente == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente non trovato su DB, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			//gestione e recupero eventi associati all'utente
			List<EventoUtente> eventiUtente = utente.getEventiUtenteInt();
			List<Evento> eventiCompletiUtente = new ArrayList<>();
			if(eventiUtente != null) {
				for (Iterator<EventoUtente> iterator = eventiUtente.iterator(); iterator.hasNext();) {
					EventoUtente evento = iterator.next();
					Evento eventoCompleto = mapper.load(Evento.class, evento.getIdEvento(), evento.getDataEvento());
					if(eventoCompleto != null) {
						eventoCompleto.setStatoEvento(evento.getStatoEvento());
						eventiCompletiUtente.add(eventoCompleto);
					}
				}
			}
			
			risposta.setEventi(eventiCompletiUtente);
			risposta.setNumTotEventi(eventiCompletiUtente.size());
		}	
		
		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
		risposta.setEsito(esito);
    		return risposta;
    }
}
