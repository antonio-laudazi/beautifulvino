package com.amazonaws.lambda.funzioni.delete;

import java.util.List;

import com.amazonaws.lambda.funzioni.common.BeautifulVinoGet;
import com.amazonaws.lambda.funzioni.common.BeautifulVinoPut;
import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.delete.RichiestaDeleteGenerica;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.delete.RispostaDeleteGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Azienda.EventoAzienda;
import com.marte5.modello2.Azienda.VinoAzienda;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.AziendaEvento;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.AziendaUtente;
import com.marte5.modello2.Vino;

public class deleteAziendaGen implements RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica> {

    @Override
    public RispostaDeleteGenerica handleRequest(RichiestaDeleteGenerica input, Context context) {
    		context.getLogger().log("Input: " + input);
    		RispostaDeleteGenerica risposta = new RispostaDeleteGenerica();

    		String idAzienda = input.getIdAzienda();
        
        Esito esito = FunzioniUtils.getEsitoPositivo();
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " deleteAzienda ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idAzienda == null || idAzienda.equals("")) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " idAzienda NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " idAzienda NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		Azienda aziendaDaCancellare = mapper.load(Azienda.class, idAzienda);
	        		if(aziendaDaCancellare == null) {
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Azienda con id: " + idAzienda + " non trovato sul database");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Azienda con id: " + idAzienda + " non trovato sul database");
	    				risposta.setEsito(esito);
	    				return risposta;
	        		} else {
	        			//cancello il collegamento col i vini
	        			List<VinoAzienda> listaVini = aziendaDaCancellare.getViniAziendaInt();
	        			if (listaVini != null) {
		        			for (VinoAzienda av : listaVini) {
		        				Vino vinoDaCanc = mapper.load(Vino.class, av.getIdVino());
		        				if (vinoDaCanc != null) {
			        				vinoDaCanc.setAziendaVinoInt(null);
			        				//Dovrei cancellare il vino???
			        				mapper.save(vinoDaCanc);
		        				}
		        			}
	        			}
	        			//cancello il collegamento con gli eventi
	        			List<EventoAzienda> listaEventi = aziendaDaCancellare.getEventiAziendaInt();
	        			if (listaEventi != null) {
		        			for (EventoAzienda ev : listaEventi) {
		        				Evento eventoDaCanc = mapper.load(Evento.class, ev.getIdEvento());
		        					//devo cancellare l'evento???
			        				if (eventoDaCanc != null) {
			        				AziendaEvento aziendaOsp = eventoDaCanc.getAziendaOspitanteEventoInt();
			        				if (aziendaOsp != null && aziendaOsp.getIdAzienda() == aziendaDaCancellare.getIdAzienda() ) {	        				
			        					eventoDaCanc.setAziendaOspitanteEventoInt(null);
			        					mapper.save(eventoDaCanc);
			        				}
		        				}
		        			}
	        			}
	        			//cancello il collogamento con gli utenti
	        			RichiestaGetGenerica r = new RichiestaGetGenerica();
	        			BeautifulVinoGet g = new BeautifulVinoGet();
	        			r.setFunctionName("getUtentiGen");
	        			r.setIdUtente("eu-central-1:e7ae1814-8e42-49fc-a183-d5e2abaf0d7c");
	        			RispostaGetGenerica out = g.handleRequest(r, context);
	        			System.out.println("risultato richiesta utenti: "+ out.getEsito().getMessage());
	        			List<Utente> utenti = out.getUtenti();
	        			for (Utente u : utenti) {
	        				List<AziendaUtente> aziendeUtente = u.getAziendeUtenteInt();
	        				AziendaUtente daCanc = null;
	        				if (aziendeUtente != null) {
		        				for (AziendaUtente au : aziendeUtente) {
		        					if (au.getIdAzienda().equals(aziendaDaCancellare.getIdAzienda())) {
		        						daCanc = au;
		        					}
		        				}
	        				}
	        				if (daCanc != null) {
	        					aziendeUtente.remove(daCanc);
	        					RichiestaPutGenerica pg = new RichiestaPutGenerica();
	        					BeautifulVinoPut p = new BeautifulVinoPut();
	        					pg.setFunctionName("putUtenteGen");
	        					pg.setUtente(u);
	        					RispostaPutGenerica out1 = p.handleRequest(pg, context);
	        					System.out.println("risultato inserimento utenti " + out1.getEsito().getMessage());
	        				}
	        			}
	        			try {
	        			//cancello l'azienda
						mapper.delete(aziendaDaCancellare);
					} catch (Exception e) {
						e.printStackTrace();
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
		    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Eccezione nell'operazione interna di salvataggio");
		    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + e.getMessage());
		    				risposta.setEsito(esito);
		    				return risposta;
					}    			
	        			//cancello eventuale immagine dell'evento
	        			String immagineAziendaUrl = aziendaDaCancellare.getUrlImmagineAzienda();
	        			if(immagineAziendaUrl != null) {
	        				if(!immagineAziendaUrl.equals("")) {
		        				FunzioniUtils.cancellaImmagine(immagineAziendaUrl);
		        			}
	        			}
	        		}
	        }
		}
        
		risposta.setEsito(esito);
        return risposta;
    }
}
