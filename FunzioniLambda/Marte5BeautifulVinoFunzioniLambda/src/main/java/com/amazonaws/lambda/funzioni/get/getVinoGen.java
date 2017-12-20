package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.Utente;
import com.marte5.modello.Vino;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.richieste.get.RichiestaGetVino;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetVino;

public class getVinoGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetGenerica risposta = getRispostaDiTest(input); 
        return risposta;
    }
    
    private RispostaGetGenerica getRispostaDiTest(RichiestaGetGenerica input) {
    	
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
    		
    		Esito esito = new Esito();
    		esito.setCodice(100);
    		esito.setMessage("Richiesta per il vino " + input.getIdVino() + " correttamente gestita");
    		
    		Vino vino = new Vino();
    		vino.setIdVino(1);
    		vino.setNomeVino("Nomevino");

    		vino.setInBreveVino("Descrizione in breve del vino selezionato");
    		vino.setInfoVino("info vino selezionato");
    		vino.setUrlLogoVino("");
    		vino.setStatoVino("N");
    		
    		Azienda azienda = new Azienda();
    		azienda.setNomeAzienda("Nomeazienda Vino");
    		azienda.setIdAzienda(1);
    		azienda.setUrlLogoAzienda("");
    		azienda.setInfoAzienda("infoAzienda Vino");
    		vino.setAziendaVino(azienda);
    		
    		Evento eventoVino = new Evento();
    		eventoVino.setDataEvento((new Date()).getTime());
    		eventoVino.setIdEvento((new Date()).getTime());
    		eventoVino.setTitoloEvento("Titolo evento vino");
    		eventoVino.setTemaEvento("TemaEvento vino");
    		eventoVino.setPrezzoEvento(34f);
    		eventoVino.setUrlFotoEvento("");
    		eventoVino.setStatoEvento("N");
    		
    		List<Evento> eventiVino = new ArrayList<>();
    		eventiVino.add(eventoVino);
    		vino.setEventiVino(eventiVino);
    		
    		List<Utente> utentiVino = new ArrayList<>();
    		for(int i = 0; i < 5; i++) {
    			Utente utente = new Utente();
    			
    			utente.setNomeUtente("Nomeutente"+i);
    			utente.setCognomeUtente("Cognomeutente"+i);
    			utente.setEsperienzaUtente(30);
    			utente.setLivelloUtente("Medio");
    			utente.setIdUtente(i);
    			utente.setUrlFotoUtente("");
    			
    			utentiVino.add(utente);
    		}
    		vino.setUtentiVino(utentiVino);
    		
    		risposta.setVino(vino);
    		risposta.setEsito(esito);
    		
    		return risposta;
    }
}

