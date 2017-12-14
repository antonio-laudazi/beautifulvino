package com.amazonaws.lambda.funzioni.connect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Utente;
import com.marte5.modello.Utente.EventoUtente;
import com.marte5.modello.richieste.connect.RichiestaConnectEventoAUtente;
import com.marte5.modello.risposte.connect.RispostaConnectEventoAUtente;

public class connectEventoAUtente implements RequestHandler<RichiestaConnectEventoAUtente, RispostaConnectEventoAUtente> {

    @Override
    public RispostaConnectEventoAUtente handleRequest(RichiestaConnectEventoAUtente input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaConnectEventoAUtente risposta = getRisposta(input);

        // TODO: implement your handler
        return risposta;
    }
    
    private RispostaConnectEventoAUtente getRisposta(RichiestaConnectEventoAUtente input) {
    		RispostaConnectEventoAUtente risposta = new RispostaConnectEventoAUtente();

    		Esito esito = FunzioniUtils.getEsitoPositivo();
    		
    		long idUtente = input.getIdUtente();
    		long idEvento = input.getIdEvento();
    		String statoEvento = input.getStatoEvento();
    		
        AmazonDynamoDB client = null;
    		try {
    			client = AmazonDynamoDBClientBuilder.standard().build();
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " connectEventoAUtente ");
    			esito.setTrace(e1.getMessage());
    		}
    		if(client != null) {
    			DynamoDBMapper mapper = new DynamoDBMapper(client);
    			
    			if(idEvento == 0) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idEvento nullo, non posso procedere");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			if(idUtente == 0) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			if(statoEvento == null || statoEvento.equals("") || (!statoEvento.equals(FunzioniUtils.EVENTO_STATO_PREFERITO) && !statoEvento.equals(FunzioniUtils.EVENTO_STATO_ACQUISTATO) && !statoEvento.equals(FunzioniUtils.EVENTO_STATO_CANCELLATO) && !statoEvento.equals(FunzioniUtils.EVENTO_STATO_NEUTRO))) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " lo stato ricevuto non è presente o non è riconosciuto tra quelli gestiti (P-Preferito, A-Acquistato/prenotato, D-Cancellato)");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			
    			Utente utente = mapper.load(Utente.class, idUtente);
    			if(utente == null) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente non trovato sul DB, non posso procedere");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			
    			//3
    			//CONTROLLO SE L'EVENTO è GIà ASSOCIATO ALL'UTENTE
    			List<EventoUtente> eventiUtente = utente.getEventiUtenteInt();
    			boolean eventoGiaAssociato = false;
    			if(eventiUtente != null) {
    				for (Iterator<EventoUtente> iterator = eventiUtente.iterator(); iterator.hasNext();) {
    					EventoUtente eventoUtente = iterator.next();
    					if(eventoUtente.getIdEvento() == idEvento) {
    						eventoGiaAssociato = true;
    					}
    				}
    			} else {
    				eventiUtente = new ArrayList<EventoUtente>();
    			}
    			
    			if(eventoGiaAssociato) {
    				//4
    				EventoUtente daRimuovere = null;
    				for (Iterator<EventoUtente> iterator = eventiUtente.iterator(); iterator.hasNext();) {
    					EventoUtente eventoUtente = iterator.next();
    					if(eventoUtente.getIdEvento() == idEvento) {
    						daRimuovere = eventoUtente;
    					}
    				}
    				if(daRimuovere.getStatoEvento().equals(FunzioniUtils.EVENTO_STATO_ACQUISTATO)) {
    					//7 - non posso cancellare o cambiare stato se lo stato precedente è Acquistato
    					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
	    		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " si tenta di cambiare di stato o cancellare un evento già acquistato.");
	    		        risposta.setEsito(esito);
	    		        return risposta;
    				} else {
    					eventiUtente.remove(daRimuovere);
        				if(!(statoEvento.equals(FunzioniUtils.EVENTO_STATO_CANCELLATO) || statoEvento.equals(FunzioniUtils.EVENTO_STATO_NEUTRO))) {
        					daRimuovere.setStatoEvento(statoEvento);
        					eventiUtente.add(daRimuovere);
        				}
    				}
    				
    			} else {
    				//5
    				if(statoEvento.equals(FunzioniUtils.EVENTO_STATO_CANCELLATO) || statoEvento.equals(FunzioniUtils.EVENTO_STATO_NEUTRO)) {
    					//7
    					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
	    		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " si tenta di cancellare un evento non associato all'utente");
	    		        risposta.setEsito(esito);
	    		        return risposta;
    				} else {
    					//6
    					EventoUtente eu = new EventoUtente();
    					eu.setIdEvento(idEvento);
    					eu.setStatoEvento(statoEvento);
    					eventiUtente.add(eu);
    				}
    			}
    			
    			utente.setEventiUtenteInt(eventiUtente);
    			utente.setNumTotEventi(eventiUtente.size());
    			
    			try {
    				mapper.save(utente);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Errore nell'aggiunta dell'evento tra i preferiti dell'utente ");
				esito.setTrace(e.getMessage());
			}
    		}

    		risposta.setEsito(esito);
        return risposta;
    }

}
