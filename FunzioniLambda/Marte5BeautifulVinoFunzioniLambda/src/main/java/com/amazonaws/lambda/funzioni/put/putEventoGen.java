package com.amazonaws.lambda.funzioni.put;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.funzioni.common.BeautifulVinoDelete;
import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.transactions.Transaction;
import com.amazonaws.services.dynamodbv2.transactions.TransactionManager;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.delete.RichiestaDeleteGenerica;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.delete.RispostaDeleteGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Azienda.EventoAzienda;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.AziendaEvento;
import com.marte5.modello2.Evento.VinoEvento;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Vino.EventoVino;

public class putEventoGen implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {
	
    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaPutGenerica risposta = new RispostaPutGenerica();
        
        String idEventoRisposta = "";
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        boolean flagOld = false;
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putEvento ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			try {
				TransactionManager.verifyOrCreateTransactionTable(client, "BV_Transactions", 10L, 10L, 10L * 60L);
				TransactionManager.verifyOrCreateTransactionImagesTable(client, "BV_TransactionImages", 10L, 10L, 10L * 60L);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Problemi con la gestione della transazione ");
				esito.setTrace(e1.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			TransactionManager txManager = new TransactionManager (client, "BV_Transactions","BV_TransactionImages");
			// Create a new transaction from the transaction manager
			Transaction transaction = txManager.newTransaction();

	        Evento evento = input.getEvento();
	        if(evento == null) {
	        	esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Evento NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Evento NULL");
				risposta.setEsito(esito);
				transaction.rollback();
				return risposta;
	        } else {
	        	
	        		String idEvento = evento.getIdEvento();
		        	if(idEvento == null || idEvento.equals("")) {
	        			//insert
		        		idEvento = FunzioniUtils.getEntitaId();
		        		flagOld = true;
		        } 
		        	idEventoRisposta = idEvento;
		        	evento.setIdEvento(idEvento);
		            
	        		//gestione aziende
	        		//OSPITANTE
	        		Azienda toLoadOspitante = new Azienda();
	        		if (evento.getAziendaOspitanteEvento() != null && evento.getAziendaOspitanteEvento().getIdAzienda() != null) {
		        		toLoadOspitante.setIdAzienda(evento.getAziendaOspitanteEvento().getIdAzienda());
		        		Azienda aziendaOspitante = transaction.load(toLoadOspitante);
		        		if (aziendaOspitante != null) {
			        		if(evento.getAziendaOspitanteEventoInt() == null){
				        		AziendaEvento aziendaVinoOspitante = new AziendaEvento();
				        		aziendaVinoOspitante.setIdAzienda(aziendaOspitante.getIdAzienda());
				        		aziendaVinoOspitante.setInfoAzienda(aziendaOspitante.getInfoAzienda());
				        		evento.setAziendaOspitanteEventoInt(aziendaVinoOspitante);
			        		}
			        		if (evento.getIndirizzoEvento() == null) evento.setIndirizzoEvento(aziendaOspitante.getIndirizzoAzienda());
			        		if (evento.getTelefonoEvento() == null) evento.setTelefonoEvento(aziendaOspitante.getTelefonoAzienda());
			        		if (evento.getEmailEvento() == null) evento.setEmailEvento(aziendaOspitante.getEmailAzienda());
			        		if (evento.getLatitudineEvento() == 0) evento.setLatitudineEvento(aziendaOspitante.getLatitudineAzienda());
			        		if (evento.getLongitudineEvento() == 0) evento.setLongitudineEvento(aziendaOspitante.getLongitudineAzienda());
			        		if (evento.getCittaEvento() == null) evento.setCittaEvento(aziendaOspitante.getCittaAzienda());
			        		
			        		//creo il collegamento all'evento nell'azienda
			        		EventoAzienda ea = new EventoAzienda();
			        		ea.setCittaEvento(evento.getCittaEvento());
			        		ea.setDataEvento(evento.getDataEvento());
			        		ea.setIdEvento(evento.getIdEvento());
			        		ea.setTemaEvento(ea.getTemaEvento());
			        		ea.setTitoloEvento(ea.getTitoloEvento());
			        		ea.setPrezzoEvento(ea.getPrezzoEvento());
			        		ea.setUrlFotoEvento(ea.getUrlFotoEvento());
			        		List<EventoAzienda> lea = new ArrayList<>();
			        		//se è stata modificata l'azienda cancello il collegamento nella azienda vecchia
			        		if (evento.getOldIdAzineda() != null && aziendaOspitante.getIdAzienda()!= null
			        				&& !aziendaOspitante.getIdAzienda().equals(evento.getOldIdAzineda())) {
			        			Azienda aziendaOldToLoad = new Azienda ();
			        			aziendaOldToLoad.setIdAzienda(evento.getOldIdAzineda());
			        			Azienda aziendaVecchia = transaction.load(aziendaOldToLoad);
			        			if (aziendaVecchia != null) {
			        				lea = aziendaVecchia.getEventiAziendaInt();
				        			EventoAzienda daCanc = null;
				        			if (lea != null) {
					        			for (EventoAzienda e : lea) {
					        				if (e.getIdEvento() == ea.getIdEvento()) daCanc = e;
					        			}
				        			}
				        			if (daCanc != null) lea.remove(daCanc);
				        			if (lea != null) aziendaVecchia.setEventiAziendaInt(lea);
				        			transaction.save(aziendaVecchia);
			        			}
			        			
			        		}
			        		if (aziendaOspitante.getEventiAziendaInt() != null) {
			        			lea = aziendaOspitante.getEventiAziendaInt();
			        			EventoAzienda daCanc = null;
			        			if (lea != null) {
				        			for (EventoAzienda e : lea) {
				        				if (e.getIdEvento() == ea.getIdEvento()) daCanc = e;
				        			}
			        			}
			        			if (daCanc != null) lea.remove(daCanc);
			        		}
			        		lea.add(ea);
			        		aziendaOspitante.setEventiAziendaInt(lea);
			        		transaction.save(aziendaOspitante);
			        	}
	        		}
	        		
	        		//gestione vini
	        		List<VinoEvento> viniEvento = evento.getViniEventoInt();
	        		//per ogni vino associato a questo evento devo associare questo evento al vino
	        		if(viniEvento != null) {
	        			for (VinoEvento vinoEvento : viniEvento) {
	    					String idVino = vinoEvento.getIdVino();
	    					if(idVino != null && !idVino.equals("")){
	    						Vino vinoToLoad = new Vino();
	    						vinoToLoad.setIdVino(idVino);
	    						Vino vino = transaction.load(vinoToLoad);
	    						if(vino != null) {
	    							List<EventoVino> eventiVino = vino.getEventiVinoInt();
	    							if(eventiVino == null) {
	    								eventiVino = new ArrayList<EventoVino>();
	    							}
	    							if (flagOld == false && evento.getDataEvento() == evento.getOldDate()) {
		    							EventoVino dop = null;
		    							for (EventoVino e : eventiVino) {
		    								if (e.getIdEvento().equals(evento.getIdEvento())) {
		    									dop = e;
		    								}
		    							}
		    							eventiVino.remove(dop);
	    							}
	    							EventoVino ev = new EventoVino();
	    							ev.setIdEvento(evento.getIdEvento());
	    							ev.setDataEvento(evento.getDataEvento());
	    							eventiVino.add(ev);
	    							vino.setEventiVinoInt(eventiVino);
	    							transaction.save(vino);
	    						}
	    					}
	    				}
	        		}

		        try {
		        		transaction.save(evento);
				} catch (Exception e) {
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Evento " + input.getEvento().getIdEvento());
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					transaction.rollback();
					return risposta;
				}
		        if (evento.getOldDate() != evento.getDataEvento() && flagOld == false) {
        			RichiestaDeleteGenerica rd = new RichiestaDeleteGenerica();
        			rd.setFunctionName("deleteEventoGen");
        			rd.setIdEvento(evento.getIdEvento());
        			rd.setDataEvento(evento.getOldDate());
        			BeautifulVinoDelete d = new BeautifulVinoDelete();
        			RispostaDeleteGenerica risp = d.handleRequest(rd, context);
        			System.out.println("esito cancellazione evento duplicato" + risp.getEsito().getMessage());
        		}
	        }
	        transaction.commit();
		}	
        risposta.setEsito(esito);
        risposta.setIdEvento(idEventoRisposta);
        return risposta;
    }
}
