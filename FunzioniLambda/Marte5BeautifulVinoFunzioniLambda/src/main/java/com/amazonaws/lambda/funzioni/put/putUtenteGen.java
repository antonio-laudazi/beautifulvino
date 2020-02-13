package com.amazonaws.lambda.funzioni.put;

import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.UtenteEvento;
import com.marte5.modello2.Livello;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.EventoUtente;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;

public class putUtenteGen implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {
	public static final int INFINITI_PUNTI_ESP = -1;
    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
    		
    		RispostaPutGenerica risposta = new RispostaPutGenerica();
        
    		String idUtenteRisposta = "";
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putUtente ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			Utente utente = input.getUtente();
			Utente utenteDaSalvare = new Utente();
			if(utente == null) {
	        	esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Utente NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		String idUtente = utente.getIdUtente();
		        	if(idUtente == null || idUtente.equals("")) {
		        		//insert
		        		idUtente = FunzioniUtils.getEntitaId();
		        		utenteDaSalvare = utente;
		        		utente.setIdUtente(idUtente);
		        		idUtenteRisposta = idUtente;
		        }  else {
		        		//update
		        		utenteDaSalvare = getUtenteModificato(utente, mapper);
		        		idUtenteRisposta = utente.getIdUtente();
		        }
		        	DynamoDBScanExpression expr = new DynamoDBScanExpression();
		        	//se è stato eliminato l'acquisto di un evento elimino il collegamneto
		        	if (utente.getEventoEliminatoUtente() != null &&
		        			!utente.getEventoEliminatoUtente().equals("")	
		        			) {
		        		List<EventoUtente> le = utenteDaSalvare.getAcquistatiEventiUtenteInt();
		        		if (le != null) {
			        		EventoUtente remove = null;
			        		for (EventoUtente e : le) {
			        			if (e.getIdEvento().equals(utente.getEventoEliminatoUtente())) {
			        				remove = e;
			        			}
			        		}		        		
			        		if (le!= null)le.remove(remove);		       
			        		utenteDaSalvare.setAcquistatiEventiUtenteInt(le);
		        		}
		        		Evento evento = mapper.load(Evento.class, utente.getEventoEliminatoUtente(),
		        				utente.getDataEventoEliminatoUtente());
		        		if (evento != null) {
			        		List<UtenteEvento> lu = evento.getIscrittiEventoInt();
			        		UtenteEvento re = null;
			        		for (UtenteEvento u : lu) {
			        			if (u.getIdUtente().equals(utenteDaSalvare.getIdUtente())) {
			        				re = u;
			        			}
			        		}
			        		if (lu != null)lu.remove(re);
			        		evento.setIscrittiEventoInt(lu);
			        		mapper.save(evento);
		        		}
		        	}
		        	//gestione livello utente 
					int esp = utenteDaSalvare.getEsperienzaUtente();
					utenteDaSalvare.setLivelloUtente("unknown");
					List<Livello> listaLivelli = mapper.scan(Livello.class, expr);
					for (Livello l : listaLivelli) {
						if (l.getMax() != INFINITI_PUNTI_ESP) {
							if (esp >= l.getMin() && esp <= l.getMax() ) {
								utenteDaSalvare.setLivelloUtente(l.getNomeLivello());
								int gap = l.getMax() - esp + 1;
								String prox = "";
								for (Livello l1: listaLivelli) {
									if (l1.getMin() == l.getMax() + 1) prox = l1.getNomeLivello();
								}
								utenteDaSalvare.setPuntiMancantiProssimoLivelloUtente("Per diventare " + prox + " ti mancano " + gap + " pt" );
								break;
							}
						}else {
							if (esp >= l.getMin() ) {
								utenteDaSalvare.setLivelloUtente(l.getNomeLivello());
								utenteDaSalvare.setPuntiMancantiProssimoLivelloUtente("Hai raggiunto il massimo livello" );
								break;
							}
						}
					}
	        		
		        try {
					mapper.save(utenteDaSalvare);
				} catch (Exception e) {
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Utente " + input.getUtente().getIdUtente());
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
	        }
			risposta.setIdUtente(idUtenteRisposta);
		}
		
		risposta.setEsito(esito);
		return risposta;
    }
    
    private Utente getUtenteModificato(Utente utenteRicevuto, DynamoDBMapper mapper) {
    		
    		Utente utenteDB = mapper.load(Utente.class, utenteRicevuto.getIdUtente());
    		
    		if(utenteDB != null) {
    			if(utenteRicevuto.getBiografiaUtente() != null) {
        			utenteDB.setBiografiaUtente(utenteRicevuto.getBiografiaUtente());
        		}
        		if(utenteRicevuto.getCittaUtente() != null) {
        			utenteDB.setCittaUtente(utenteRicevuto.getCittaUtente());
        		}
        		if(utenteRicevuto.getCognomeUtente() != null) {
        			utenteDB.setCognomeUtente(utenteRicevuto.getCognomeUtente());
        		}
        		if(utenteRicevuto.getCreditiUtente() != 0) {
        			utenteDB.setCreditiUtente(utenteRicevuto.getCreditiUtente());
        		}
        		if(utenteRicevuto.getEmailUtente() != null) {
        			utenteDB.setEmailUtente(utenteRicevuto.getEmailUtente());
        		}
        		if(utenteRicevuto.getEsperienzaUtente() != 0) {
        			utenteDB.setEsperienzaUtente(utenteRicevuto.getEsperienzaUtente());
        		}
        		if(utenteRicevuto.getLivelloUtente() != null) {
        			utenteDB.setLivelloUtente(utenteRicevuto.getLivelloUtente());
        		}
        		if(utenteRicevuto.getNomeUtente() != null) {
        			utenteDB.setNomeUtente(utenteRicevuto.getNomeUtente());
        		}
        		if(utenteRicevuto.getProfessioneUtente() != null) {
        			utenteDB.setProfessioneUtente(utenteRicevuto.getProfessioneUtente());
        		}
        		if(utenteRicevuto.getUrlFotoUtente() != null) {
        			utenteDB.setUrlFotoUtente(utenteRicevuto.getUrlFotoUtente());
        		}
        		if(utenteRicevuto.getUsernameUtente() != null) {
        			utenteDB.setUsernameUtente(utenteRicevuto.getUsernameUtente());
        		}
    		} else {
    			utenteDB = new Utente();
    			utenteDB.setIdUtente(FunzioniUtils.getEntitaId());
    		}		
    		return utenteDB;  	
    }
}