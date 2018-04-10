package com.amazonaws.lambda.funzioni.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Evento.VinoEvento;

public class BeautifulVinoPeriodic {

	public static void main(String[] args) {
		
		JSONObject input = new JSONObject();
		input.put("functionName", "getEventiGen");
		input.put("idUtente", "eu-central-1:e7ae1814-8e42-49fc-a183-d5e2abaf0d7c");
//        List<Evento> eventi = output.getEventi();
		String urlGet = "https://gmnh1plxq7.execute-api.eu-central-1.amazonaws.com/BeautifulVinoGet";
		String urlConnect = "https://ivbkaplee3.execute-api.eu-central-1.amazonaws.com/BeautifulVinoConnect";
		JSONObject out = post(input, urlGet);
		JSONArray data = (JSONArray) out.get("eventi");
//        for (int i = 0; i < data.length(); i++) {
//        	JSONObject e = (JSONObject) data.get(i);
//        	long dataEvento = getJustDateFrom(e.get("dataE"));
//        	long dataOggi = getJustDateFrom(new Date().getTime());
//        			//completare
//        	if (dataEvento == dataOggi) {
//        		List<UtenteEvento> u = e.getIscrittiEventoInt();
//        		List<VinoEvento> v = e.getViniEventoInt();
//        		for (UtenteEvento uu : u) {
//        			for (VinoEvento vv : v) {
//        				RichiestaConnectGenerica r = new RichiestaConnectGenerica();    	
//        				BeautifulVinoConnect c = new BeautifulVinoConnect();
//        				r.setFunctionName("connectViniAUtenteGen");
//        				r.setStatoVino("A");
//        				r.setIdUtente(uu.getIdUtente());
//        				r.setIdVino(vv.getIdVino());
//
//
//        			}
//        		}
//        	}
//        }
	}
	
	private static long getJustDateFrom(long d) {
	    long milliseconds = d;
	    return milliseconds - (milliseconds%(1000*60*60));
	}
	
	private static JSONObject post(JSONObject input, String url) {
		
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
        String rs = getResponseString(r);
        JSONObject rj = new JSONObject(rs);
        return rj;
	}
	
	public static String getResponseString(HttpEntity entity) {
	    StringBuilder builder = new StringBuilder();
	    if (entity != null) {
	        try (BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()))) {
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                builder.append(inputLine);
	            }
	        } catch (IOException e) {
	        	e.printStackTrace();
	            return null;
	        }

	    }
	    return builder.toString();
	}
}
