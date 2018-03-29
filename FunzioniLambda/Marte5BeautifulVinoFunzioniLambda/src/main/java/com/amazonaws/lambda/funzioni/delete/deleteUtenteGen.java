package com.amazonaws.lambda.funzioni.delete;

import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Utente.AziendaUtente;
import com.marte5.modello2.Utente.EventoUtente;
import com.marte5.modello2.Utente.UtenteUtente;
import com.marte5.modello2.Utente.VinoUtente;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Vino.UtenteVino;
import com.marte5.modello.richieste.delete.RichiestaDeleteGenerica;
import com.marte5.modello.risposte.delete.RispostaDeleteGenerica;

public class deleteUtenteGen implements RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica> {

    @Override
    public RispostaDeleteGenerica handleRequest(RichiestaDeleteGenerica input, Context context) {
    		context.getLogger().log("Input: " + input);
    		RispostaDeleteGenerica risposta = new RispostaDeleteGenerica();

    		String idUtente = input.getIdUtente();
        
        Esito esito = FunzioniUtils.getEsitoPositivo();
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putEvento ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idUtente == null || idUtente.equals("")) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdUtente NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdUtente NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		Utente utenteDaCancellare = mapper.load(Utente.class, idUtente);
	        		if(utenteDaCancellare == null) {
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Utente con id: " + idUtente + " non trovato sul database");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Utente con id: " + idUtente + " non trovato sul database");
	    				risposta.setEsito(esito);
	    				return risposta;
	        		} else {
	        			//cancello il collegamento con gli eventi
	        			List<EventoUtente> listaEventi = utenteDaCancellare.getEventiUtenteInt();
	        			if (listaEventi != null) {
		        			for (EventoUtente eu : listaEventi) {
		        				Evento eventoDaCanc = mapper.load(Evento.class, eu.getIdEvento());
		        				List<UtenteEvento> listaUtenteEvento = eventoDaCanc.getIscrittiEventoInt();
		        				UtenteEvento vucanc = null;
		        				for (UtenteEvento v : listaUtenteEvento) {
			        				if (v.getIdUtente().equals(utenteDaCancellare.getIdUtente())) {
			        					vucanc = v;
			        				}
		        				}
		        				if (vucanc != null)listaUtenteEvento.remove(vucanc);
		        				mapper.save(eventoDaCanc);
		        			}
	        			}
	        			//cancello il collegamento con le aziende
	        			//non c'è il collegamento fra le azieda e gli utenti, nulla da cancellare
//	        			List<AziendaUtente> listaAziende = utenteDaCancellare.getAziendeUtenteInt();
//	        			if (listaAziende != null) {
//		        			for (AziendaUtente eu : listaAziende) {
//		        				Azienda aziendaDaCanc = mapper.load(Azienda.class, eu.getIdAzienda());
//		        				List<UtenteAzienda> listaUtenteAziedna = aziendaDaCanc.getUtenteAzienda();
//		        				UtenteAzienda vucanc = null;
//		        				for (UtenteAzienda v : listaUtenteAzienda) {
//			        				if (v.getIdUtente().equals(utenteDaCancellare.getIdUtente())) {
//			        					vucanc = v;
//			        				}
//		        				}
//		        				if (vucanc != null)listaUtenteAziedna.remove(vucanc);
//		        				mapper.save(aziednaDaCanc);
//		        			}
//	        			}
	        			//cancello il collegamneto con i Vini
	        			List<VinoUtente> listaVini = utenteDaCancellare.getViniUtenteInt();
	        			if (listaVini != null) {
		        			for (VinoUtente eu : listaVini) {
		        				Vino vinoDaCanc = mapper.load(Vino.class, eu.getIdVino());
		        				List<UtenteVino> listaUtenteVino = vinoDaCanc.getUtentiVinoInt();
		        				UtenteVino vucanc = null;
		        				for (UtenteVino v : listaUtenteVino) {
			        				if (v.getIdUtente().equals(utenteDaCancellare.getIdUtente())) {
			        					vucanc = v;
			        				}
		        				}
		        				if (vucanc != null)listaUtenteVino.remove(vucanc);
		        				mapper.save(vinoDaCanc);
		        			}
	        			}
	        			//cancello il collegamneto con gli altri utenti
	        			List<UtenteUtente> listaUtenti = utenteDaCancellare.getUtentiUtenteInt();
	        			if (listaUtenti != null) {
		        			for (UtenteUtente eu : listaUtenti) {
		        				Utente utenteDaCanc = mapper.load(Utente.class, eu.getIdUtente());
		        				List<UtenteUtente> listaUtenteUtente = utenteDaCanc.getUtentiUtenteInt();
		        				UtenteUtente vucanc = null;
		        				for (UtenteUtente v : listaUtenteUtente) {
			        				if (v.getIdUtente().equals(utenteDaCancellare.getIdUtente())) {
			        					vucanc = v;
			        				}
		        				}
		        				if (vucanc != null)listaUtenteUtente.remove(vucanc);
		        				mapper.save(utenteDaCanc);
		        			}
	        			}
	        			//caricato l'evento, lo vado a cancellare
	        			try {
						mapper.delete(utenteDaCancellare);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
		    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Eccezione nell'operazione interna di salvataggio");
		    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + e.getMessage());
		    				risposta.setEsito(esito);
		    				return risposta;
					}
	        			
	        			//cancello eventuale immagine del feed
	        			String immagineUtenteUrl = utenteDaCancellare.getUrlFotoUtente();
	        			if(immagineUtenteUrl != null) {
	        				if(!immagineUtenteUrl.equals("")) {
		        				esito = FunzioniUtils.cancellaImmagine(immagineUtenteUrl);
		        			}
	        			}
	        		}
	        }
		}
        
		risposta.setEsito(esito);
        // TODO: implement your handler
        return risposta;
    }

}
