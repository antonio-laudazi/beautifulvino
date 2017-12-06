package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Azienda;
import com.marte5.modello.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.Utente;
import com.marte5.modello.Vino;
import com.marte5.modello.richieste.get.RichiestaGetUtente;
import com.marte5.modello.risposte.get.RispostaGetUtente;

public class getUtente implements RequestHandler<RichiestaGetUtente, RispostaGetUtente> {

    @Override
    public RispostaGetUtente handleRequest(RichiestaGetUtente input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetUtente risposta = getRispostaDiTest(input);
        
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getUtente ");
			esito.setTrace(e1.getMessage());
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
			
			//gestione e recupero eventi associati all'utente
			List<Evento> eventiUtente = utente.getEventiUtente();
			List<Evento> eventiCompletiUtente = new ArrayList<>();
			for (Iterator<Evento> iterator = eventiUtente.iterator(); iterator.hasNext();) {
				Evento evento = iterator.next();
				Evento eventoCompleto = mapper.load(Evento.class, evento.getIdEvento());
				eventoCompleto.setStatoEvento(evento.getStatoEvento());
				
				eventiCompletiUtente.add(eventoCompleto);
			}
			utente.setEventiUtente(eventiCompletiUtente);
			
			//gestione e recupero badge associati all'utente
			List<Badge> badges = utente.getBadgeUtente();
			List<Badge> badgesCompleti = new ArrayList<>();
			for (Iterator<Badge> iterator = badges.iterator(); iterator.hasNext();) {
				Badge badge = iterator.next();
				Badge badgeCompleto = mapper.load(Badge.class, badge.getIdBadge());
				
				badgesCompleti.add(badgeCompleto);
			}
			utente.setBadgeUtente(badgesCompleti);
			
			//badge e aziende (vini) sono già nel profilo utente per comodità, e ce li metto quando faccio le nuove associazioni
		}
        
        risposta.setEsito(esito);
        risposta.setUtente(utente);
        return risposta;
    }
    
    private RispostaGetUtente getRispostaDiTest(RichiestaGetUtente input) {
    		RispostaGetUtente risposta = new RispostaGetUtente();
        
        Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage("Esito corretto della getUtente");
        
        Utente utente = new Utente();
        
        utente.setIdUtente(1);
        utente.setNomeUtente("Nomeutente");
        utente.setCognomeUtente("Cognomeutente");
        utente.setCreditiUtente(10);
        utente.setEsperienzaUtente(30);
        utente.setLivelloUtente("Esperto");
        utente.setBiografiaUtente("Biografia dell'utente corrente");
        utente.setUrlFotoUtente("");
        
        /*--------------------------------------*/
        
        List<Evento> eventiUtente = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
        		Evento evento = new Evento();
        		
        		evento.setIdEvento((new Date()).getTime());
        		evento.setDataEvento((new Date()).getTime());
        		evento.setDataEventoStringa(FunzioniUtils.getStringVersion(new Date()));
        		evento.setLuogoEvento("Luogo dell'evento " +   i);
        		evento.setTitoloEvento("Titolo evento " + i);
        		evento.setTemaEvento("Tema Evento " + i);
        		evento.setPrezzoEvento(35f);
        		evento.setUrlFotoEvento("");
        		evento.setStatoEvento("N");
        		
        		eventiUtente.add(evento);
        }
        utente.setEventiUtente(eventiUtente);
        utente.setNumTotEventi(eventiUtente.size());
        utente.setCondivisioneEventi("S");
        
        /*--------------------------------------*/
        
        List<Badge> badges = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
        		Badge badge = new Badge();
        		
        		badge.setIdBadge(i);
        		badge.setNomeBadge("Nomebadge_" + i);
        		badge.setInfoBadge("info badge");
        		badge.setUrlLogoBadge("");
        		badge.setTuoBadge((i==0?"S":"N"));
        		
        		badges.add(badge);
        }
        utente.setBadgeUtente(badges);
        utente.setNumTotBadge(badges.size());
        utente.setCondivisioneBadge("S");
        
        /*--------------------------------------*/
        
        /*qui c'è l'elenco dei vini divisi per aziende; l'interrogazione dovrà essere fatta per vini, poi la suddivisione sarà fatta direttamente sulla funzione*/
        List<Azienda> aziendeUtente = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
        		Azienda azienda = new Azienda();
        	
        		azienda.setIdAzienda(i);
        		azienda.setNomeAzienda("Nomeazienda_" + i);
        		azienda.setUrlLogoAzienda("");
        		
        		List<Vino> viniAzienda = new ArrayList<>();
        		for(int j = 0; j < 2; j++) {
        			Vino vino = new Vino();
        			vino.setIdVino(j);
        			vino.setNomeVino("nome_vino_" + i + "_" + j);
        			vino.setInfoVino("info del vino");
        			vino.setUrlLogoVino("");
        			
        			viniAzienda.add(vino);
        		}
        		
        		azienda.setViniAzienda(viniAzienda);
        		aziendeUtente.add(azienda);
        }
        utente.setAziendeUtente(aziendeUtente);
        utente.setNumTotAziende(aziendeUtente.size());
        utente.setCondivisioneVini("S");
        
        risposta.setUtente(utente);
        risposta.setEsito(esito);
        // TODO: implement your handler
        return risposta;
    }
}
