package com.amazonaws.lambda.funzioni.connect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.common.BeautifulVinoAcquista;
import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.acquista.RichiestaAcquistaGenerica;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.Risposta;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.EventoUtente;

public class connectEventoAUtenteGen implements RequestHandler<RichiestaConnectGenerica, RispostaConnectGenerica> {

    @Override
    public RispostaConnectGenerica handleRequest(RichiestaConnectGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaConnectGenerica risposta = getRisposta(input, context);

        // TODO: implement your handler
        return risposta;
    }
    
    private RispostaConnectGenerica getRisposta(RichiestaConnectGenerica input, Context context) {
    		RispostaConnectGenerica risposta = new RispostaConnectGenerica();
    		Utente utente = null;
    		Evento evento = null;
    		Esito esito = FunzioniUtils.getEsitoPositivo();
    		
    		String idUtente = input.getIdUtente();
    		String idEvento = input.getIdEvento();
    		long dataEvento = input.getDataEvento();
    		String statoEvento = input.getStatoEvento();
    		
        AmazonDynamoDB client = null;
    		try {
    			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " connectEventoAUtente ");
    			esito.setTrace(e1.getMessage());
    		}
    		if(client != null) {
    			DynamoDBMapper mapper = new DynamoDBMapper(client);
    			evento = mapper.load(Evento.class, idEvento, dataEvento);
    			if(evento == null) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " evento non trovato sul DB, non posso procedere");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			if(idEvento == null || idEvento.equals("")) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idEvento nullo, non posso procedere");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			if(idUtente == null || idUtente.equals("")) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			if(statoEvento == null || statoEvento.equals("") || (!statoEvento.equals(FunzioniUtils.EVENTO_STATO_PREFERITO) && !statoEvento.equals(FunzioniUtils.EVENTO_STATO_ACQUISTATO) && !statoEvento.equals(FunzioniUtils.EVENTO_STATO_CANCELLATO) && !statoEvento.equals(FunzioniUtils.EVENTO_STATO_NEUTRO))) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " lo stato ricevuto non è presente o non è riconosciuto tra quelli gestiti (P-Preferito, A-Acquistato/prenotato, D-Cancellato)");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			
    			utente = mapper.load(Utente.class, idUtente);
    			if(utente == null) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente non trovato sul DB, non posso procedere");
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
    					if(eventoUtente.getIdEvento().equals(idEvento)) {
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
    					if(eventoUtente.getIdEvento().equals(idEvento)) {
    						daRimuovere = eventoUtente;
    					}
    				}
    				if(daRimuovere.getStatoEvento().equals(FunzioniUtils.EVENTO_STATO_ACQUISTATO)) {
    					//7 - non posso cancellare o cambiare stato se lo stato precedente è Acquistato
    					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
	    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " si tenta di cambiare di stato o cancellare un evento già acquistato.");
	    		        risposta.setEsito(esito);
	    		        return risposta;
    				} else {
    					eventiUtente.remove(daRimuovere);
        				if(!(statoEvento.equals(FunzioniUtils.EVENTO_STATO_CANCELLATO) || statoEvento.equals(FunzioniUtils.EVENTO_STATO_NEUTRO))) {
        					daRimuovere.setStatoEvento(statoEvento);
        					eventiUtente.add(daRimuovere);
        				}
    					Esito out = sendMail(utente.getIdUtente(), utente.getUsernameUtente(), evento.getIdEvento(), evento.getTitoloEvento(), input.getNumeroPartecipanti(), context);
    					if (out.getCodice() != 100) {
    						esito = out;
    	    		        risposta.setEsito(esito);
    	    		        return risposta;
    					}
    				}
    				
    			} else {
    				//5
    				if(statoEvento.equals(FunzioniUtils.EVENTO_STATO_CANCELLATO) || statoEvento.equals(FunzioniUtils.EVENTO_STATO_NEUTRO)) {
    					//7
    					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
	    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " si tenta di cancellare un evento non associato all'utente");
	    		        risposta.setEsito(esito);
	    		        return risposta;
    				} else {
    					//6
    					Esito out =sendMail(utente.getIdUtente(), utente.getUsernameUtente(), evento.getIdEvento(), evento.getTitoloEvento(), input.getNumeroPartecipanti(), context);
    					if (out.getCodice() != 100) {
    						esito = out;
    	    		        risposta.setEsito(esito);
    	    		        return risposta;
    					}
    					EventoUtente eu = new EventoUtente();
    					eu.setIdEvento(idEvento);
    					eu.setStatoEvento(statoEvento);
    					eu.setDataEvento(dataEvento);
    					eventiUtente.add(eu);
    					
    					//se lo stato è "Aquistato" devo aggiungere un utente agli utenti iscritti all'evento
    				}
    			}
    			
    			utente.setEventiUtenteInt(eventiUtente);
    			utente.setNumTotEventi(eventiUtente.size());
    			
    			try {
    				mapper.save(utente);
    				addUtenteIscritto(idEvento, dataEvento, idUtente,evento, mapper);
			} catch (Exception e) {
				e.printStackTrace();
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Errore nell'aggiunta dell'evento tra i preferiti dell'utente ");
				esito.setTrace(e.getMessage());
			}
    		}

    		risposta.setEsito(esito);
        return risposta;
    }
    
    private void addUtenteIscritto(String idEvento, long dataEvento, String idUtente,Evento evento, DynamoDBMapper mapper) {
    		if(evento != null) {
    			List<UtenteEvento> utentiIscritti = evento.getIscrittiEventoInt();
    			if(utentiIscritti == null) {
    				utentiIscritti = new ArrayList<UtenteEvento>();
    			}
    			boolean presente = false;
    			for (UtenteEvento utenteEvento : utentiIscritti) {
				if(utenteEvento.getIdUtente().equals(idUtente)) {
					presente = true;
				}
			}
    			if(!presente) {
    				UtenteEvento ue = new UtenteEvento();
    				ue.setIdUtente(idUtente);
    				utentiIscritti.add(ue);
    			}
    			evento.setIscrittiEventoInt(utentiIscritti);
    			mapper.save(evento);
    		}
    }
    private Esito sendMail(String idU, String nomeU, String idE, String nomeE, int num, Context context) {
    	RichiestaAcquistaGenerica r = new RichiestaAcquistaGenerica();
		BeautifulVinoAcquista c = new BeautifulVinoAcquista();
		r.setIdUtente(idU);
		r.setNomeUtente(nomeU);
		r.setIdEvento(idE);
		r.setNomeEvento(nomeE);
		r.setNumeroPartecipanti(num);
		Risposta out = c.handleRequest(r, context);
		return out.getEsito();
    }
}
