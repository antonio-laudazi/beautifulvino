package com.amazonaws.lambda.funzioni.get.backup;

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
import com.marte5.modello.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.Utente;
import com.marte5.modello.Utente.BadgeUtente;
import com.marte5.modello.Utente.EventoUtente;
import com.marte5.modello.richieste.get.RichiestaGetUtente;
import com.marte5.modello.risposte.get.RispostaGetUtente;

public class getUtente implements RequestHandler<RichiestaGetUtente, RispostaGetUtente> {

    @Override
    public RispostaGetUtente handleRequest(RichiestaGetUtente input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetUtente risposta = getRisposta(input);
        
        return risposta;
    }
    
    private RispostaGetUtente getRisposta(RichiestaGetUtente input) {
    		RispostaGetUtente risposta = new RispostaGetUtente();
    		
    		long idUtente = input.getIdUtente();
        Utente utente = new Utente();
    		
        Esito esito = FunzioniUtils.getEsitoPositivo();
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getUtente ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			if(idUtente == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			utente = mapper.load(Utente.class, idUtente);
			if(utente == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			//gestione e recupero eventi associati all'utente
			List<EventoUtente> eventiUtente = utente.getEventiUtenteInt();
			List<Evento> eventiCompletiUtente = new ArrayList<>();
			if(eventiUtente != null) {
				for (Iterator<EventoUtente> iterator = eventiUtente.iterator(); iterator.hasNext();) {
					EventoUtente evento = iterator.next();
					Evento eventoCompleto = mapper.load(Evento.class, evento.getIdEvento());
					if(eventoCompleto != null) {
						eventoCompleto.setStatoEvento(evento.getStatoEvento());
						eventiCompletiUtente.add(eventoCompleto);
					}
				}
			}
			
			utente.setEventiUtente(eventiCompletiUtente);
			
			//gestione e recupero badge associati all'utente
			List<BadgeUtente> badges = utente.getBadgeUtenteInt();
			List<Badge> badgesCompleti = new ArrayList<>();
			if(badges != null) {
				for (Iterator<BadgeUtente> iterator = badges.iterator(); iterator.hasNext();) {
					BadgeUtente badge = iterator.next();
					Badge badgeCompleto = mapper.load(Badge.class, badge.getIdBadge());
					
					badgesCompleti.add(badgeCompleto);
				}
			}
			utente.setBadgeUtente(badgesCompleti);
		}
        
        risposta.setEsito(esito);
        risposta.setUtente(utente);
        return risposta;
    }
    
}
