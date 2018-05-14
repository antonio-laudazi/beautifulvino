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
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.BadgeUtente;
import com.marte5.modello2.Utente.EventoUtente;
import com.marte5.modello2.Utente.UtenteUtente;
import com.marte5.modello2.Utente.VinoUtente;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getUtenteGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetGenerica risposta = getRisposta(input);
        
        return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
    		
    		String idUtente = input.getIdUtente();
    		String idUtentePadre = input.getIdUtentePadre();
        Utente utente = new Utente();
    	
        Esito esito = FunzioniUtils.getEsitoPositivo();
        esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getUtente ");
			esito.setTrace(e1.getMessage());
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
			
			utente = mapper.load(Utente.class, idUtente);
			if(utente == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente nullo, non posso procedere");
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
			utente.setEventiUtente(eventiCompletiUtente);
			
			//gestione e recupero badge associati all'utente
			List<BadgeUtente> badges = utente.getBadgeUtenteInt();
			if(badges != null) {
				DynamoDBScanExpression expr = new DynamoDBScanExpression();
				List<Badge> tuttiBadge;
				try {
					tuttiBadge = mapper.scan(Badge.class, expr);
				} catch (Exception e) {
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
					esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " problema nell'estrazione di tutti i badge ");
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
				List<Badge> badgesCompleti = new ArrayList<>();
				for (Badge badge : tuttiBadge) {
					Badge nuovo = new Badge();
					nuovo.setIdBadge(badge.getIdBadge());
					nuovo.setNomeBadge(badge.getNomeBadge());
					nuovo.setInfoBadge(badge.getInfoBadge());
					nuovo.setUrlLogoBadge(badge.getUrlLogoBadge());
					nuovo.setTuoBadge("N");
					for (BadgeUtente badgeUtente : badges) {
						if(badgeUtente.getIdBadge().equals(badge.getIdBadge())) {
							nuovo.setTuoBadge("S");
						}
					}
					if (input.getIdUtenteLog() == input.getIdUtentePadre() ||
							nuovo.getTuoBadge().equals("S")
							) {
							badgesCompleti.add(nuovo);
					}
				}
				utente.setBadgeUtente(badgesCompleti);
			}
			//recupero associazione fra utente loggato e l'utente richiesto
			String idUtenteLog = input.getIdUtenteLog();
			if (idUtenteLog != null) {
				Utente utenteLog = mapper.load(Utente.class, idUtenteLog);
				if (utenteLog != null) {
					List<UtenteUtente> listaUtenti = utenteLog.getUtentiUtenteInt();
					risposta.setStato("D");
					for (UtenteUtente u : listaUtenti) {
						if (u.getIdUtente().equals(utente.getIdUtente())) {
							risposta.setStato("A");
						}
					}
				}
			}
			//riordino Aziende
			List<VinoUtente> vini = utente.getViniUtenteInt();
			List<Azienda> aziendeConvertite = new ArrayList<>();
			if(vini != null) {
				aziendeConvertite = FunzioniUtils.riordinaViniAzienda_Utente(vini, mapper);
			}
			utente.setAziendeUtente(aziendeConvertite);
			
			utente.setStatoUtente("D");
			if(idUtentePadre != null) {
				Utente utentePadre = mapper.load(Utente.class, idUtentePadre);
				if(utentePadre != null) {
					List<Utente> utentiSeguiti = utentePadre.getUtentiUtente();
					if(utentiSeguiti != null) {
						for(Utente u : utentiSeguiti) {
							if(u.getIdUtente().equals(idUtente)) {
								utente.setStatoUtente("A");
							}
						}
					}
				}
			}
			
		}     
        risposta.setEsito(esito);
        risposta.setUtente(utente);
        return risposta;
    }
    
}
