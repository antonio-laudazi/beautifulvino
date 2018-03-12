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
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.VinoEvento;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Vino;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getEventoGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        //RispostaGetEvento risposta = getRispostaDiTest(input);
        RispostaGetGenerica risposta = getRisposta(input);
        
        return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    		
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
    		String idEvento = input.getIdEvento();
    		String idUtente = input.getIdUtente();
    		long dataEvento = input.getDataEvento();
    		
    		Evento evento = new Evento();
    		Esito esito = FunzioniUtils.getEsitoPositivo();
    		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEvento ");
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
			if(idUtente == null || idUtente.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
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
			Utente utente = mapper.load(Utente.class, idUtente);
			
			//gestione dello stato evento da visualizzare
			String statoEvento = FunzioniUtils.EVENTO_STATO_NEUTRO;
			try {
				statoEvento = FunzioniUtils.getStatoEvento(utente, idEvento, dataEvento, mapper);
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEvento ");
				esito.setTrace(e.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			evento.setStatoEvento(statoEvento);
			
			//gestione degli utenti da visualizzare
			List<Utente> utentiEvento = evento.getIscrittiEvento();
			List<Utente> utentiEventoCompleti = new ArrayList<>();
			if(utentiEvento != null) {
				for (Iterator<Utente> iterator = utentiEvento.iterator(); iterator.hasNext();) {
					Utente utenteEvento = iterator.next();
					Utente utenteEventoDB = mapper.load(Utente.class, utenteEvento.getIdUtente());
					Utente utenteEventoCompleto = new Utente();
					
					utenteEventoCompleto.setIdUtente(utenteEventoDB.getIdUtente());
					utenteEventoCompleto.setNomeUtente(utenteEventoDB.getNomeUtente());
					utenteEventoCompleto.setCognomeUtente(utenteEventoDB.getCognomeUtente());
					utenteEventoCompleto.setEsperienzaUtente(utenteEventoDB.getEsperienzaUtente());
					utenteEventoCompleto.setLivelloUtente(utente.getLivelloUtente());
					utenteEventoCompleto.setUrlFotoUtente(utente.getUrlFotoUtente());
					
					utentiEventoCompleti.add(utenteEventoCompleto);
				}
			} 
			
			evento.setIscrittiEvento(utentiEventoCompleti);
			
			//gestione dei vini da visualizzare
			List<VinoEvento> viniEvento = evento.getViniEventoInt();
			List<Vino> viniEventoCompleti = new ArrayList<>();
			if(viniEvento != null) {
				for (Iterator<VinoEvento> iterator = viniEvento.iterator(); iterator.hasNext();) {
					VinoEvento vinoEvento = iterator.next();
					Vino vinoEventoDB = mapper.load(Vino.class, vinoEvento.getIdVino());
					Vino vinoEventoCompleto = new Vino();
					
					vinoEventoCompleto.setIdVino(vinoEventoDB.getIdVino());
					vinoEventoCompleto.setNomeVino(vinoEventoDB.getNomeVino());
					vinoEventoCompleto.setInfoVino(vinoEventoDB.getInfoVino());
					vinoEventoCompleto.setInBreveVino(vinoEventoDB.getInBreveVino());
					vinoEventoCompleto.setUrlLogoVino(vinoEventoDB.getUrlLogoVino());
					vinoEventoCompleto.setUvaggioVino(vinoEventoDB.getUvaggioVino());
					
					viniEventoCompleti.add(vinoEventoCompleto);
				}
			}
			evento.setViniEvento(viniEventoCompleti);
			
			//gestione aziende (fornitrice-ospitante)
			String idAziendaFornitrice = evento.getAziendaFornitriceEventoInt().getIdAzienda();
			if(idAziendaFornitrice != null && !idAziendaFornitrice.equals("")) {
				Azienda aziendaFornitrice = mapper.load(Azienda.class, idAziendaFornitrice);
				if(aziendaFornitrice != null) {
					evento.setAziendaFornitriceEvento(aziendaFornitrice);
				}
			}
			String idAziendaOspitante = evento.getAziendaOspitanteEventoInt().getIdAzienda();
			if(idAziendaOspitante != null && !idAziendaOspitante.equals("")) {
				Azienda aziendaOspitante = mapper.load(Azienda.class, idAziendaOspitante);
				if(aziendaOspitante != null) {
					evento.setAziendaOspitanteEvento(aziendaOspitante);
				}
			}
			
			//gestione Badge
			com.marte5.modello2.Evento.BadgeEvento badgeEvento = evento.getBadgeEventoInt();
			if(badgeEvento != null) {
				Badge badge = mapper.load(Badge.class, badgeEvento.getIdBadge());
				if(badge != null) {
					evento.setBadgeEvento(badge);
				} else {
					Badge nuovo = new Badge();
					nuovo.setIdBadge(badgeEvento.getIdBadge());
					evento.setBadgeEvento(badge);
				}
			}
			
		}
    		
    		risposta.setEsito(esito);
    		risposta.setEvento(evento);
    		
    		return risposta;
    }
    
}
