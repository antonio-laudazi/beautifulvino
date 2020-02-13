package com.amazonaws.lambda.funzioni.connect.backup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello2.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Vino;
import com.marte5.modello.richieste.connect.RichiestaConnectViniAUtente;
import com.marte5.modello.risposte.connect.RispostaConnectViniAUtente;

public class connectViniAUtente implements RequestHandler<RichiestaConnectViniAUtente, RispostaConnectViniAUtente> {

    @Override
    public RispostaConnectViniAUtente handleRequest(RichiestaConnectViniAUtente input, Context context) {
        context.getLogger().log("Input: " + input);

        RispostaConnectViniAUtente risposta = getRisposta(input);
        // TODO: implement your handler
        return risposta;
    }

	private RispostaConnectViniAUtente getRisposta(RichiestaConnectViniAUtente input) {
		RispostaConnectViniAUtente risposta = new RispostaConnectViniAUtente();
		Esito esito = new Esito();
		esito.setCodice(100);
        esito.setMessage("Esito corretto per la richiesta getEventi");
        
        String idUtente = input.getIdUtente();
		List<Azienda> aziendeViniDaAssociare = input.getAziendeViniDaAssociare();
		
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
			
			if(aziendeViniDaAssociare == null || aziendeViniDaAssociare.size() == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " non ci sono vini da associare, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			if(idUtente == null || idUtente.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			Utente utente = mapper.load(Utente.class, idUtente);
			if(utente == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente non troovato sul DB, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			List<Azienda> aziendeNuove = new ArrayList<>();
			for (Iterator<Azienda> iterator = aziendeViniDaAssociare.iterator(); iterator.hasNext();) {
				Azienda aziendaDaAssociare = iterator.next();
				String idAzienda = aziendaDaAssociare.getIdAzienda();
				
				List<Azienda> aziendeViniUtente = utente.getAziendeUtente();
				for (Iterator<Azienda> iteratorAziendeUtente = aziendeViniUtente.iterator(); iterator.hasNext();) {
					
					Azienda aziendaUtente = iteratorAziendeUtente.next();
					
					//per ogni azienda devo
					//1.controllare che non ci sia già
					//	-se c'è gia' vado a fare i controlli sui vini
					//	-se non c'è la aggiungo completa
					if(aziendaUtente.getIdAzienda() == idAzienda) {
						//l'azienda c'è gia'
						//controllo i vini dell'azienda in questione
						aziendaUtente.setViniAzienda(getListaViniAggiornata(aziendaDaAssociare.getViniAzienda(), aziendaUtente.getViniAzienda()));
					} else {
						aziendaUtente.getViniAzienda().addAll(aziendaDaAssociare.getViniAzienda());
					}
					aziendeNuove.add(aziendaUtente);
					//eventiNuovi.add(azienda);
				}
			}
			utente.setAziendeUtente(aziendeNuove);
			mapper.save(utente);
		}
		
        risposta.setEsito(esito);
		return risposta;
	}
	
	private List<Vino> getListaViniAggiornata(List<Vino> listaRicevuta, List<Vino> listaAttuale){
		List<Vino> risultato = listaAttuale;
		
		for (Iterator<Vino> iterator = listaRicevuta.iterator(); iterator.hasNext();) {
			Vino vinoRicevuto = iterator.next();
			String idVinoRicevuto = vinoRicevuto.getIdVino();
			for (Iterator<Vino> iterator2 = listaAttuale.iterator(); iterator2.hasNext();) {
				Vino vinoAttuale = iterator2.next();
				String idVinoAttuale = vinoAttuale.getIdVino();
				if(!idVinoAttuale.equals(idVinoRicevuto)) {
					risultato.add(vinoRicevuto);
				} 
			}
		}
		
		return risultato;
	}

}
