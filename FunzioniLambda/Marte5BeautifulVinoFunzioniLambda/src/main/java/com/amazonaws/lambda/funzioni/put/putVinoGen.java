package com.amazonaws.lambda.funzioni.put;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
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
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Azienda.VinoAzienda;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Vino.AziendaVino;

public class putVinoGen implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {
	
	/*
	 * PRENDERE COME ESEMPIO PER LA GESTIONE DELLE TRANSAZIONI
	 * */
	
    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaPutGenerica risposta = new RispostaPutGenerica();
        
        String idVinoRisposta = "";
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        AmazonDynamoDB client = null;
		try {
			//client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).withCredentials(new ProfileCredentialsProvider("BeautifulVino")).build();
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
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Problemi con la gestione della transazione ");
				esito.setTrace(e1.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			TransactionManager txManager = new TransactionManager (client, "BV_Transactions","BV_TransactionImages");
			// Create a new transaction from the transaction manager
			Transaction transaction = txManager.newTransaction();
			
			//DynamoDBMapper mapper = new DynamoDBMapper(client);

	        Vino vino = input.getVino();
	        if(vino == null) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Vino NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " Vino NULL");
				risposta.setEsito(esito);
				transaction.rollback();
				return risposta;
	        } else {
		        	String idVino = vino.getIdVino();
		        	if(idVino == null || idVino.equals("")) {
		        		idVino = FunzioniUtils.getEntitaId();
		            } 
		        	idVinoRisposta = idVino;
	        		vino.setIdVino(idVino);
	        		Azienda toLoad = null;
	        		Azienda azienda = null;
	        		if (vino.getAziendaVino()!= null) {
		        		toLoad = new Azienda();
		        		toLoad.setIdAzienda(vino.getAziendaVino().getIdAzienda());
		        		azienda = transaction.load(toLoad);
		        		//Azienda azienda = mapper.load(Azienda.class, vino.getAziendaVino().getIdAzienda());
		        		
		        		if(vino.getAziendaVinoInt() == null){
			        		AziendaVino aziendaVino = new AziendaVino();
			        		aziendaVino.setIdAzienda(azienda.getIdAzienda());
			        		aziendaVino.setNomeAzienda(azienda.getNomeAzienda());
			        		vino.setAziendaVinoInt(aziendaVino);
		        		}
	        		}
		        try {
		        		transaction.save(vino);
				} catch (Exception e) {
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Vino " + input.getVino().getIdVino());
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					transaction.rollback();
					return risposta;
				}		        
		        // a questo punto dovrei inserire il vino tra quelli della lista dell'azienda
		        if (azienda != null) {
		        	//vino nuovo
			        VinoAzienda vinoPerAzienda = new VinoAzienda();
			        	vinoPerAzienda.setIdVino(vino.getIdVino());
			        	vinoPerAzienda.setNomeVino(vino.getNomeVino());
			        	vinoPerAzienda.setAnnoVino(vino.getAnnoVino());
			        	if(azienda.getViniAziendaInt() == null) {
			        		azienda.setViniAziendaInt(new ArrayList<VinoAzienda>());
			        	}
			        azienda.getViniAziendaInt().add(vinoPerAzienda);
			        try {
		        		if (azienda!= null) transaction.save(azienda);
				} catch (Exception e) {
					e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Problemi nel salvare l'azienda aggiornata col vino inserito");
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					transaction.rollback();
					return risposta;
				}
		        }
		        try {
		        	transaction.commit();
        		}catch (Exception e) {
        			e.printStackTrace();
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Problemi nel salvare l'azienda vecchia con il vino eliminato");
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					transaction.rollback();
					return risposta;
        		}
		        if (vino.getOldIdAzienda() != null &&
		        		!vino.getOldIdAzienda().equals("")) {
		        	//rimuovo l'associazione nella azienda oldId sia se non ho cambiato azienda(quindi ha un collegamento duplicato)
		        	//sia se l'ho cambiata 
		        	Transaction transactionOld = txManager.newTransaction();
		        	Azienda toLoadOld = new Azienda();
		        	System.out.println(vino.getOldIdAzienda());
	        		toLoadOld.setIdAzienda(vino.getOldIdAzienda());
	        		Azienda aziendaOld = transactionOld.load(toLoadOld);
	        		List<VinoAzienda> lv = aziendaOld.getViniAziendaInt();
	        		if (lv != null) {
	        			VinoAzienda daCanc = null;
		        		for (VinoAzienda ev : lv) {
		        			if (ev.getIdVino().equals(vino.getIdVino())){
		        				daCanc = ev;
		        			}
		        		}
		        		if (daCanc != null) lv.remove(daCanc);
		        		aziendaOld.setViniAziendaInt(lv);
		        		try {
		        			transactionOld.save(aziendaOld);
		        			transactionOld.commit();
		        		}catch (Exception e) {
		        			e.printStackTrace();
							esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
							esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO + "Problemi nel salvare l'azienda vecchia con il vino eliminato");
							esito.setTrace(e.getMessage());
							risposta.setEsito(esito);
							transactionOld.rollback();
							return risposta;
		        		}
	        		}
		        }
		       
	        }
	        
		}	
        risposta.setEsito(esito);
        risposta.setIdVino(idVinoRisposta);
        return risposta;
    }
}
