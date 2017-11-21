package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.Vino;
import com.marte5.modello.richieste.get.RichiestaGetAzienda;
import com.marte5.modello.risposte.get.RispostaGetAzienda;

public class getAzienda implements RequestHandler<RichiestaGetAzienda, RispostaGetAzienda> {

    @Override
    public RispostaGetAzienda handleRequest(RichiestaGetAzienda input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaGetAzienda risposta = getRispostaDiTest(input);
        return risposta;
    }
    
    private RispostaGetAzienda getRispostaDiTest(RichiestaGetAzienda input) {
    		RispostaGetAzienda risposta = new RispostaGetAzienda();
        
        Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage("Richiesta Azienda gestit correttamente per l'azienda: " + input.getIdAzienda());

        Azienda azienda = new Azienda();
        
        azienda.setIdAzienda(1);
        azienda.setInfoAzienda("Info azienda con id: " + input.getIdAzienda());
        azienda.setLatitudineAzienda(43.313333);
        azienda.setLongitudineAzienda(10.518434);
        azienda.setLuogoAzienda("Cecina");
        azienda.setNomeAzienda("NomeAzienda");
        azienda.setUrlLogoAzienda("");
        azienda.setZonaAzienda("Zona azienda");
        
        List<Evento> eventiAzienda = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
        		Evento evento = new Evento();
        		
        		evento.setIdEvento((new Date()).getTime());
        		evento.setDataEvento((new Date()).getTime());
        		evento.setDataEventoStringa(FunzioniUtils.getStringVersion(new Date()));
        		evento.setLuogoEvento("Cecina");
        		evento.setTitoloEvento("Titoloevento" + i);
        		evento.setTemaEvento("Temaevento"+i);
        		evento.setPrezzoEvento(30f);
        		evento.setUrlFotoEvento("");
        		evento.setStatoEvento("P");
        		
        		eventiAzienda.add(evento);
        }
        azienda.setNumEventiAzienda(eventiAzienda.size());
        azienda.setEventiAzienda(eventiAzienda);
        
        List<Vino> viniAzienda = new ArrayList<>();
        for(int i = 0; i< 8; i++) {
        		Vino vino = new Vino();
        		
        		vino.setIdVino(i);
        		vino.setNomeVino("Vino"+i);
        		vino.setInfoVino("Info relative al vino vino"+i);
        		vino.setUrlLogoVino("");
        		
        		viniAzienda.add(vino);
        }
        azienda.setNumViniAzienda(viniAzienda.size());
        azienda.setViniAzienda(viniAzienda);
        
        risposta.setEsito(esito);
        risposta.setAzienda(azienda);
        
        return risposta;
    }

}
