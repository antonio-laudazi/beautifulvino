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
import com.marte5.modello.richieste.delete.RichiestaDeleteGenerica;
import com.marte5.modello.risposte.delete.RispostaDeleteGenerica;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Azienda.EventoAzienda;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.AziendaEvento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Evento.VinoEvento;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.EventoUtente;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Vino.EventoVino;

public class deleteEventoGen implements RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica> {

    @Override
    public RispostaDeleteGenerica handleRequest(RichiestaDeleteGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaDeleteGenerica risposta = new RispostaDeleteGenerica();

        String idEvento = input.getIdEvento();
        long dataEvento = input.getDataEvento();
        
        Esito esito = new Esito();
        esito.setCodice(EsitoHelper.ESITO_OK_CODICE);
        esito.setMessage(EsitoHelper.ESITO_OK_MESSAGGIO);
        
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
			if(idEvento == null || idEvento.equals("") ) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdEvento NULL ");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdEvento NULL ");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		Evento eventoDaCancellare = mapper.load(Evento.class, idEvento, dataEvento );
	        		if(eventoDaCancellare == null) {
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Evento con id: " + idEvento + " non trovato sul database");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Evento con id: " + idEvento + " non trovato sul database");
	    				risposta.setEsito(esito);
	    				return risposta;
	        		} else {
	        			//cancello il collegamento con l'azienda
	        			AziendaEvento aziendaEvento = eventoDaCancellare.getAziendaOspitanteEventoInt();
	        			if (aziendaEvento != null) {	        					        		
	        					Azienda aziendaDaCanc = mapper.load(Azienda.class, aziendaEvento.getIdAzienda());
	        					if (aziendaDaCanc != null) {
	        					List<EventoAzienda> listaEventiAzienda = aziendaDaCanc.getEventiAziendaInt();
	    	        			if (listaEventiAzienda != null) {
	    		        			EventoAzienda vcanc = null; 
	    		        			for (EventoAzienda a : listaEventiAzienda) {
	    		        				if (a.getIdEvento().equals(eventoDaCancellare.getIdEvento()) ) {
	    		        					vcanc = a;	        					
	    		        				}
	    		        			}
	    		        			if (vcanc != null) listaEventiAzienda.remove(vcanc);
	    	        			}
        					}
	        			}
	        			//cancello il collegamento con i vini
	        			List<VinoEvento> listaVini = eventoDaCancellare.getViniEventoInt();
	        			if (listaVini != null) {
		        			for (VinoEvento ev : listaVini) {
		        				Vino vinoDaCanc = mapper.load(Vino.class, ev.getIdVino());
		        				if (vinoDaCanc != null) {
			        				List<EventoVino> listaViniEvento = vinoDaCanc.getEventiVinoInt();
			        				EventoVino vecanc = null;
			        				for (EventoVino v : listaViniEvento) {
				        				if (v.getIdEvento().equals(eventoDaCancellare.getIdEvento())) {
				        					vecanc = v;
				        				}
			        				}
			        				if (vecanc != null)listaViniEvento.remove(vecanc);
			        				mapper.save(vinoDaCanc);
		        				}
		        			}
	        			}
	        			//cancello il collegamento con gli utenti
	        			List<UtenteEvento> listaUtenti = eventoDaCancellare.getIscrittiEventoInt();
	        			if (listaUtenti != null) {
		        			for (UtenteEvento ev : listaUtenti) {
		        				Utente utenteDaCanc = mapper.load(Utente.class, ev.getIdUtente());
		        				if (utenteDaCanc != null) {
			        				List<EventoUtente> listaUtenteEvento = utenteDaCanc.getEventiUtenteInt();
			        				EventoUtente vucanc = null;
			        				for (EventoUtente v : listaUtenteEvento) {
				        				if (v.getIdEvento().equals(eventoDaCancellare.getIdEvento())) {
				        					vucanc = v;
				        				}
			        				}
			        				if (vucanc != null)listaUtenteEvento.remove(vucanc);
			        				mapper.save(utenteDaCanc);
		        				}
		        			}
	        			}
	        			//caricato l'evento, lo vado a cancellare
	        			try {
						mapper.delete(eventoDaCancellare);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
		    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Eccezione nell'operazione interna di salvataggio");
		    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + e.getMessage());
		    				risposta.setEsito(esito);
		    				return risposta;
					}
	        			
	        			//cancello eventuale immagine dell'evento
	        			String immagineEventoUrl = eventoDaCancellare.getUrlFotoEvento();
	        			if(immagineEventoUrl != null) {
	        				if(!immagineEventoUrl.equals("")) {
		        				esito = FunzioniUtils.cancellaImmagine(immagineEventoUrl);
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
