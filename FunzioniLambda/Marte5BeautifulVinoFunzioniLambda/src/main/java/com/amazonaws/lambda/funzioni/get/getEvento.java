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
import com.marte5.modello.richieste.get.RichiestaGetEvento;
import com.marte5.modello.risposte.get.RispostaGetEvento;

public class getEvento implements RequestHandler<RichiestaGetEvento, RispostaGetEvento> {

    @Override
    public RispostaGetEvento handleRequest(RichiestaGetEvento input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetEvento risposta = getRispostaDiTest(input);
        
        return risposta;
    }
    
    private RispostaGetEvento getRispostaDiTest(RichiestaGetEvento input) {
    		RispostaGetEvento risposta = new RispostaGetEvento();
        
        Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage("Esito corretto per la richiesta di evento per l'idEvento: " + input.getIdEvento() + " dell'utente: " + input.getIdUtente());
        
        Azienda aziendaEvento = new Azienda();
        aziendaEvento.setIdAzienda(1);
        aziendaEvento.setInfoAzienda("Info relative all'azienda ");
        aziendaEvento.setLatitudineAzienda(43.313333);
        aziendaEvento.setLongitudineAzienda(10.518434);
        aziendaEvento.setLuogoAzienda("Cecina");
        aziendaEvento.setNomeAzienda("Nome azienda Vinicola");
        aziendaEvento.setUrlLogoAzienda("");
        aziendaEvento.setZonaAzienda("Zona azienda");
        
        List<Utente> iscrittiEvento = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
        		Utente utente = new Utente();
        		
        		utente.setIdUtente(i);
        		utente.setNomeUtente("Nomeutente"+i);
        		utente.setCognomeUtente("Cognomeutente"+i);
        		utente.setEsperienzaUtente(4);
        		utente.setLivelloUtente("Principiante");
        		utente.setUrlFotoUtente("");
        		
        		iscrittiEvento.add(utente);
        }
        
        List<Vino> viniEvento = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
        		Vino vino = new Vino();
        		
        		vino.setIdVino(i);
        		vino.setNomeVino("Nomevino"+i);
        		vino.setInfoVino("Informazioni sul vino "+ i);
        		vino.setUrlLogoVino("");
        		
        		viniEvento.add(vino);
        }
        
        Evento evento = new  Evento();
        evento.setConvenzionataEvento(false);
        evento.setDataEvento((new Date()).getTime());
        evento.setIdEvento((new Date()).getTime());
        evento.setIndirizzoEvento("Via dello svolgimento dell'evento");
        evento.setLatitudineEvento(43.313333);
        evento.setLongitudineEvento(10.518434);
        evento.setLuogoEvento("Cecina");
        evento.setNumMaxPartecipantiEvento(50);
        evento.setNumPostiDisponibiliEvento(30);
        evento.setPrezzoEvento(100f);
        evento.setStatoEvento("P");
        evento.setTemaEvento("Tema dell'evento");
        evento.setTestoEvento("Questo è il testo dell'evento in questione a cui vorrei partecipare");
        evento.setTitoloEvento("Evento Vinicolo");
        evento.setUrlFotoEvento("");
        evento.setAziendaEvento(aziendaEvento);
        evento.setIscrittiEvento(iscrittiEvento);
        evento.setViniEvento(viniEvento);
        
        risposta.setEsito(esito);
        risposta.setEvento(evento);
        
        // TODO: implement your handler
        return risposta;
    }

}
