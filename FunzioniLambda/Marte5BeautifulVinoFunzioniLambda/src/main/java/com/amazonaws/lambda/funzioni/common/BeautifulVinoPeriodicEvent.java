package com.amazonaws.lambda.funzioni.common;


import java.util.Date;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Evento.VinoEvento;

public class BeautifulVinoPeriodicEvent implements RequestHandler<Integer, String> {

	@Override
	public String handleRequest(Integer input, Context context) {
		RichiestaGetGenerica rg = new RichiestaGetGenerica();
		BeautifulVinoGet g = new BeautifulVinoGet();
		rg.setFunctionName("getEventiGen");
		rg.setIdUtente("eu-central-1:e7ae1814-8e42-49fc-a183-d5e2abaf0d7c");
		RispostaGetGenerica out = g.handleRequest(rg, context);
		List<Evento> eventi = out.getEventi();
		for (Evento e : eventi) {
        	long dataEvento = getJustDateFrom(e.getDataEvento());
        	long dataOggi = getJustDateFrom(new Date().getTime());
        	if (dataEvento == dataOggi) {
        		List<UtenteEvento> u = e.getIscrittiEventoInt();
        		List<VinoEvento> v = e.getViniEventoInt();
        		for (UtenteEvento uu : u) {
        			for (VinoEvento vv : v) {
        				RichiestaConnectGenerica r = new RichiestaConnectGenerica();    	
        				BeautifulVinoConnect c = new BeautifulVinoConnect();
        				r.setFunctionName("connectViniAUtenteGen");
        				r.setStatoVino("A");
        				r.setIdUtente(uu.getIdUtente());
        				r.setIdVino(vv.getIdVino());
        				RispostaConnectGenerica o = c.handleRequest(r, context);
        				System.out.println("esito connect " + uu.getIdUtente() + " " + vv.getIdVino() + " = "+ o.getEsito());
        			}
        		}
        	}
        }
		return null;
	}
	
	private static long getJustDateFrom(long d) {
	    long milliseconds = d;
	    return milliseconds - (milliseconds%(1000*60*60));
	}
}