package com.amazonaws.lambda.funzioni.delete;

import java.util.List;

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
import com.marte5.modello.risposte.delete.RispostaDeleteGenerica;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Azienda.VinoAzienda;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.VinoEvento;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.VinoUtente;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Vino.AziendaVino;
import com.marte5.modello2.Vino.EventoVino;
import com.marte5.modello2.Vino.UtenteVino;

public class deleteVinoGen implements RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica>  {

	@Override
	public RispostaDeleteGenerica handleRequest(RichiestaDeleteGenerica input, Context context) {		
		
		 context.getLogger().log("Input: " + input);
	        RispostaDeleteGenerica risposta = new RispostaDeleteGenerica();

	        String idVino = input.getIdVino();
	        
	        Esito esito = new Esito();
	        esito.setCodice(EsitoHelper.ESITO_OK_CODICE);
	        esito.setMessage(EsitoHelper.ESITO_OK_MESSAGGIO);
	        
	        AmazonDynamoDB client = null;
	        try {
				client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " deleteVino ");
				esito.setTrace(e1.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
	        if(client != null) {
	        	DynamoDBMapper mapper = new DynamoDBMapper(client);
				if(idVino == null || idVino.equals("")) {
		        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdVino NULL ");
					esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " IdVino NULL ");
					risposta.setEsito(esito);
					return risposta;
		        }else {
		        	Vino vinoDaCancellare = mapper.load(Vino.class, idVino );
	        		if(vinoDaCancellare == null) {
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " vino con id: " + idVino + " non trovato sul database");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " vino con id: " + idVino + " non trovato sul database");
	    				risposta.setEsito(esito);
	    				return risposta;
	        		} else {
	        			//cancello il collegamneto con l'azienda
	        			AziendaVino aziendaVino = vinoDaCancellare.getAziendaVinoInt();
	        			Azienda aziendaVinoCanc = mapper.load(Azienda.class,aziendaVino.getIdAzienda());
	        			List<VinoAzienda> listaViniAzienda = aziendaVinoCanc.getViniAziendaInt();
	        			if (listaViniAzienda != null) {
		        			VinoAzienda vcanc = null; 
		        			for (VinoAzienda v : listaViniAzienda) {
		        				if (v.getIdVino().equals(vinoDaCancellare.getIdVino()) ) {
		        					vcanc = v;	        					
		        				}
		        			}
		        			if (vcanc != null) listaViniAzienda.remove(vcanc);
	        			}
	        			mapper.save(aziendaVinoCanc);
	        			//cancello il collegamneto con gli eventi
	        			List<EventoVino> listaEventi = vinoDaCancellare.getEventiVinoInt();
	        			if (listaEventi != null) {
		        			for (EventoVino ev : listaEventi) {
		        				Evento eventoDaCanc = mapper.load(Evento.class, ev.getIdEvento());
		        				List<VinoEvento> listaViniEvento = eventoDaCanc.getViniEventoInt();
		        				VinoEvento vecanc = null;
		        				for (VinoEvento v : listaViniEvento) {
			        				if (v.getIdVino().equals(vinoDaCancellare.getIdVino())) {
			        					vecanc = v;
			        				}
		        				}
		        				if (vecanc != null)listaViniEvento.remove(vecanc);
		        				mapper.save(eventoDaCanc);
		        			}
	        			}
	        			//cancello il collegamento con gli utenti
        				List<UtenteVino> listaUtenti = vinoDaCancellare.getUtentiVinoInt();
        				if (listaUtenti != null) {
		        			for (UtenteVino u : listaUtenti) {
		        				Utente utenteDaCanc = mapper.load(Utente.class, u.getIdUtente());
		        				List<VinoUtente> listaViniUtente = utenteDaCanc.getViniUtenteInt();
		        				VinoUtente vucanc = null;
		        				for (VinoUtente v : listaViniUtente) {
			        				if (v.getIdVino().equals(vinoDaCancellare.getIdVino())) {
			        					vucanc = v;
			        				}
		        				}
		        				if (vucanc != null)listaViniUtente.remove(vucanc);
		        				mapper.save(utenteDaCanc);
		        			}
        				}
	        			//cancello il vino
	        			try {
							mapper.delete(vinoDaCancellare);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
			    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Eccezione nell'operazione interna di cancellazione");
			    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + e.getMessage());
			    				risposta.setEsito(esito);
			    				return risposta;
						}
	        			//cancello eventuale immagine del Vino
	        			String immagineVinoUrl = vinoDaCancellare.getUrlImmagineVino();
	        			if(immagineVinoUrl != null) {
	        				if(!immagineVinoUrl.equals("")) {
		        				esito = FunzioniUtils.cancellaImmagine(immagineVinoUrl);
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
