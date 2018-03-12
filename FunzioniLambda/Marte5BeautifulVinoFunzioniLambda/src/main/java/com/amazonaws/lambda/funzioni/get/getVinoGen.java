package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello2.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.VinoUtente;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Vino.AziendaVino;
import com.marte5.modello2.Vino.EventoVino;
import com.marte5.modello2.Vino.UtenteVino;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica; 

public class getVinoGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
//        RispostaGetGenerica risposta = getRispostaDiTest(input);
        RispostaGetGenerica risposta = getRisposta(input);
        return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
    	
    		Esito esito = FunzioniUtils.getEsitoPositivo();
    		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
    		String idVino = input.getIdVino();
    		String idUtente = input.getIdUtente();
    		
    		AmazonDynamoDB client = null;
            try {
    			client = AmazonDynamoDBClientBuilder.standard().build();
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEvento ");
    			esito.setTrace(e1.getMessage());
    			risposta.setEsito(esito);
		    return risposta;
    		}
    		if(client != null) {
    			DynamoDBMapper mapper = new DynamoDBMapper(client);
    			if(idVino == null || idVino.equals("")) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idVino nullo, non posso procedere");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			
    			Vino vino = mapper.load(Vino.class, idVino);
    			if(vino == null) {
    				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
    		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " Vino nullo, non posso procedere");
    		        risposta.setEsito(esito);
    		        return risposta;
    			}
    			
    			AziendaVino aziendaVino = vino.getAziendaVinoInt();
    			if(aziendaVino != null) {
    				String aziendaId = aziendaVino.getIdAzienda();
    				if(aziendaId != null && !aziendaId.equals("")) {
    					Azienda azienda = mapper.load(Azienda.class, aziendaId);
    					if(azienda != null) {
    						vino.setAziendaVino(azienda);
    					}
    				}
    			}
    			
    			//gestione utentiVino e eventiVino
    			List<UtenteVino> utentiVino = vino.getUtentiVinoInt();
    			List<Utente> utentiVinoCompleti = new ArrayList<>();
    			if(utentiVino != null) {
    				for (UtenteVino utenteVino : utentiVino) {
						Utente utenteDB = mapper.load(Utente.class, utenteVino.getIdUtente());
						if(utenteDB != null) {
							utentiVinoCompleti.add(utenteDB);
						}
					}
    			}
    			vino.setUtentiVino(utentiVinoCompleti);
    			
    			List<EventoVino> eventiVino = vino.getEventiVinoInt();
    			List<Evento> eventiVinoCompleti = new ArrayList<>();
    			if(eventiVino != null) {
    				for (EventoVino eventoVino : eventiVino) {
    						//serve anche la data evento senno' non trova niente!!!
						Evento eventoDB = mapper.load(Evento.class, eventoVino.getIdEvento(), eventoVino.getDataEvento());
						if(eventoDB != null) {
							eventiVinoCompleti.add(eventoDB);
						}
					}
    			}
    			vino.setEventiVino(eventiVinoCompleti);
    			
    			//vado a recupere lo stato del vino relativo all'utente chiamante
    			if(idUtente != null && !idUtente.equals("")) {
    				Utente utente = mapper.load(Utente.class, idUtente);
    				if(utente != null) {
    					List<VinoUtente> viniUtente = utente.getViniUtenteInt();
        				if(viniUtente != null) {
        					for (VinoUtente vinoUtente : viniUtente) {
        						if(vinoUtente.getIdVino().equals(idVino)) {
        							vino.setStatoVino(vinoUtente.getStatoVino());
        						}
        					}
        				}
    				} else {
    					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
        		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente con id " + idUtente + " inesistente sul DB, non posso procedere");
        		        risposta.setEsito(esito);
        		        return risposta;
    				}
    				
    			}
    			
    			risposta.setVino(vino);
    			risposta.setEsito(esito);
    		}
    		
    		return risposta;
    }
    
}

