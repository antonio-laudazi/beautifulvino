package com.amazonaws.lambda.funzioni.connect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello2.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Utente.AziendaUtente;
import com.marte5.modello2.Utente.EventoUtente;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;

public class connectViniAUtenteGen_Backup implements RequestHandler<RichiestaConnectGenerica, RispostaConnectGenerica> {

    @Override
    public RispostaConnectGenerica handleRequest(RichiestaConnectGenerica input, Context context) {
        context.getLogger().log("Input: " + input);

        RispostaConnectGenerica risposta = getRisposta(input);
        return risposta;
    }

	private RispostaConnectGenerica getRisposta(RichiestaConnectGenerica input) {
		RispostaConnectGenerica risposta = new RispostaConnectGenerica();
		Esito esito = new Esito();
		esito.setCodice(100);
        esito.setMessage("Esito corretto per la richiesta getEventi");
        
        String idUtente = input.getIdUtente();
        String idVino = input.getIdVino();
        String statoVino = input.getStatoVino();
        
		List<Azienda> aziendeViniDaAssociare = creaStrutturaInput(idUtente, idVino, statoVino);
		
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
			
			//CONTROLLO SE IL VINO è GIà ASSOCIATO ALL'UTENTE
			List<AziendaUtente> aziendeUtente = utente.getAziendeUtenteInt();
			boolean vinoGiaAssociato = false;
			if(aziendeUtente != null) {
				for (Iterator<AziendaUtente> iterator = aziendeUtente.iterator(); iterator.hasNext();) {
					AziendaUtente aziendaUtente = iterator.next();
					aziendaUtente.get
					if(eventoUtente.getIdEvento().equals(idEvento)) {
						eventoGiaAssociato = true;
					}
				}
			} else {
				aziendeUtente = new ArrayList<AziendaUtente>();
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
					if(aziendaUtente.getIdAzienda().equals(idAzienda)) {
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
	
	private List<Azienda> creaStrutturaInput(String idUtente, String idVino, String statoVino){
		List<Azienda> aziendeViniDaAssociare = new ArrayList<Azienda>();
		
		String idAzienda = recuperoIdAzienda(idVino);
		if(idAzienda.equals("")) {
			return aziendeViniDaAssociare;
		}
		
		Azienda azienda = new Azienda();
		azienda.setIdAzienda(idAzienda);
		Vino vino = new Vino();
		vino.setIdVino(idVino);
		vino.setStatoVino(statoVino);

		List<Vino> vini = new ArrayList<>();
		vini.add(vino);
		azienda.setViniAzienda(vini);
		
		aziendeViniDaAssociare.add(azienda);
		
		return aziendeViniDaAssociare;
		
	}

	private String recuperoIdAzienda(String idVino) {
		AmazonDynamoDB client = null;
		String idAzienda = "";
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			return idAzienda;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idVino == null || idVino.equals("")) {
				return idAzienda;
			}
			
			Vino vino = mapper.load(Vino.class, idVino);
			if(vino == null) {
				return idAzienda;
			}
			Azienda azienda =  vino.getAziendaVino();
			if(azienda == null) {
				return idAzienda;
			}
			idAzienda = vino.getAziendaVino().getIdAzienda();
		}
		return idAzienda;
	}

}
