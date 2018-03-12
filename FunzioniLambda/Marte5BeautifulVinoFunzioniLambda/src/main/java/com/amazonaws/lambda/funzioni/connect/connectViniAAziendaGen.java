package com.amazonaws.lambda.funzioni.connect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.transactions.Transaction;
import com.amazonaws.services.dynamodbv2.transactions.TransactionManager;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Azienda.VinoAzienda;
import com.marte5.modello.Esito;
import com.marte5.modello2.Vino;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;

public class connectViniAAziendaGen implements RequestHandler<RichiestaConnectGenerica, RispostaConnectGenerica> {

    @Override
    public RispostaConnectGenerica handleRequest(RichiestaConnectGenerica input, Context context) {
        context.getLogger().log("Input: " + input);

        RispostaConnectGenerica risposta = getRisposta(input);
        // TODO: implement your handler
        return risposta;
    }

	private RispostaConnectGenerica getRisposta(RichiestaConnectGenerica input) {
		RispostaConnectGenerica risposta = new RispostaConnectGenerica();
		Esito esito = new Esito();
		esito.setCodice(100);
        esito.setMessage("Esito corretto per la richiesta connectViniAAzienda");
        
        String idAzienda = input.getIdAzienda();
		List<Vino> viniAzienda = input.getViniAzienda();
		
		AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " connectEventoAUtente ");
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
			
			//DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			if(viniAzienda == null || viniAzienda.size() == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " non ci sono vini da associare, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			if(idAzienda == null || idAzienda.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idAzienda nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			Azienda toLoad = new Azienda();
			toLoad.setIdAzienda(idAzienda);
			Azienda azienda = transaction.load(toLoad);
			//Azienda azienda = mapper.load(Azienda.class, idAzienda);
			if(azienda == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " azienda non trovata sul DB, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			List<Vino> viniNuovi = new ArrayList<>();
			List<Vino> viniAttualmenteAssociati = azienda.getViniAzienda();
			
			for (Iterator<Vino> iterator = viniAttualmenteAssociati.iterator(); iterator.hasNext();) {
				Vino vinoPresente = iterator.next();
				for (Iterator<Vino> iterator2 = viniAzienda.iterator(); iterator2.hasNext();) {
					Vino vinoDaAssociare = iterator2.next();
					if(!vinoPresente.getIdVino().equals(vinoDaAssociare.getIdVino())) {
						viniNuovi.add(vinoDaAssociare);
					}
				}
			}
			azienda.setViniAzienda(viniNuovi);
			
			List<VinoAzienda> viniAziendaInt = new ArrayList<>();
			for(Iterator<Vino> iterator = viniNuovi.iterator(); iterator.hasNext();) {
				Vino vinoNuovo = iterator.next();
				VinoAzienda vinoAzienda = new VinoAzienda();
				vinoAzienda.setIdVino(vinoNuovo.getIdVino());
				vinoAzienda.setNomeVino(vinoNuovo.getNomeVino());
				vinoAzienda.setAnnoVino(vinoNuovo.getAnnoVino());
				viniAziendaInt.add(vinoAzienda);
			}
			azienda.setViniAziendaInt(viniAziendaInt);
			
			
			try {
				transaction.save(azienda);
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " Errore nel salvataggio dell'azienda aggiornata");
		        esito.setTrace(e.getMessage());
		        risposta.setEsito(esito);
		        transaction.rollback();
		        return risposta;
			}
			
			for (Iterator<Vino> iterator = viniNuovi.iterator(); iterator.hasNext();) {
				Vino vino = iterator.next();
				Vino vinoToload = new Vino();
				vinoToload.setIdVino(vino.getIdVino());
				Vino vinoSuDb = transaction.load(vinoToload);
				if(vinoSuDb != null) {
					Azienda aziendaAssociata = vinoSuDb.getAziendaVino();
					if(aziendaAssociata == null) {
						//il vino era stato inserito senza una azienda associata (non dovrebbe succedere)
						vinoSuDb.setAziendaVino(azienda);
					} else {
						//ce l'ha già un'azienda il vino. Ma se è differente a quella proposta? per ora aggiorno VERIFICARE SE LA LOGICA è CORRETTA
						vinoSuDb.setAziendaVino(azienda);
					}
					try {
						transaction.save(vinoSuDb);
					} catch (Exception e) {
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
				        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " Errore nel salvataggio dell'aggiornamento sul vino " + vino.getNomeVino());
				        esito.setTrace(e.getMessage());
				        risposta.setEsito(esito);
				        transaction.rollback();
				        return risposta;
					}
				}
			}
			transaction.commit();
		}
		
        risposta.setEsito(esito);
		return risposta;
	}
}
