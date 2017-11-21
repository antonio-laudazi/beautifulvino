package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
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
