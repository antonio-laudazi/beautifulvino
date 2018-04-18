package com.amazonaws.lambda.funzioni.common;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Evento.VinoEvento;

public class BeautifulVinoPeriodic {

	public static void main(String[] args) {
		

		input.put("functionName", "getEventiGen");
//      List<Evento> eventi = output.getEventi();
		post(input, urlGet);
		
        for (Evento e : eventi) {
        	long dataEvento = getJustDateFrom(e.getDataEvento());
        	long dataOggi = getJustDateFrom(new Date().getTime());
        			//completare
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
        				RispostaConnectGenerica o = c.handleRequest(r, ctx);
        				System.out.println("esito connect " + uu.getIdUtente() + " " + vv.getIdVino() + " = "+ o.getEsito());
        			}
        		}
        	}
        }
	}
	
	private static long getJustDateFrom(long d) {
	    long milliseconds = d;
	    return milliseconds - (milliseconds%(1000*60*60));
	}
	
	private static void post(JSONObject input, String url) {
		
		StringEntity entity = new StringEntity(input.toString(),ContentType.APPLICATION_FORM_URLENCODED);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        request.setEntity(entity);
        HttpResponse response = null;
        try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        HttpEntity r = response.getEntity();
        JSONObject rr = Ja
        return;
	}
}
