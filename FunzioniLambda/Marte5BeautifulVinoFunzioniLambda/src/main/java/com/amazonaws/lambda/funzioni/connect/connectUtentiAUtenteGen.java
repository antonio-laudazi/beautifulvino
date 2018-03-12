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
import com.marte5.modello.Esito;
import com.marte5.modello2.Utente;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;

public class connectUtentiAUtenteGen implements RequestHandler<RichiestaConnectGenerica, RispostaConnectGenerica> {

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
        esito.setMessage("Esito corretto per la richiesta getEventi");
        
        String idUtente = input.getIdUtente();
		List<Utente> utentiDaAssociare = input.getUtenti();
		String stato = input.getStatoVariazione();//A = aggiungi, D = togli
		
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
			
			if(utentiDaAssociare == null || utentiDaAssociare.size() == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " non ci sono utenti da associare, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			if(idUtente == null || idUtente.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			Utente utente = mapper.load(Utente.class, idUtente);
			if(utente == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente non troovato sul DB, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			List<Utente> utentiAttualmenteAssociati = utente.getUtentiUtente();
			
			if(stato == "A") {
				List<Utente> utentiNuovi = utentiAttualmenteAssociati;
				for (Iterator<Utente> iterator = utentiAttualmenteAssociati.iterator(); iterator.hasNext();) {
					Utente utentePresente = iterator.next();
					for (Iterator<Utente> iterator2 = utentiDaAssociare.iterator(); iterator2.hasNext();) {
						Utente utenteDaAssociare = iterator2.next();
						if(utentePresente.getIdUtente().equals(utenteDaAssociare.getIdUtente())) {
							utentiNuovi.remove(utenteDaAssociare);
						}
					}
				} 
				utente.setUtentiUtente(utentiNuovi);
				
			} else if (stato == "D") {
				List<Utente> utentiNuovi = new ArrayList<>();
				for (Iterator<Utente> iterator = utentiAttualmenteAssociati.iterator(); iterator.hasNext();) {
					Utente utentePresente = iterator.next();
					for (Iterator<Utente> iterator2 = utentiDaAssociare.iterator(); iterator2.hasNext();) {
						Utente utenteDaAssociare = iterator2.next();
						if(utentePresente.getIdUtente().equals(utenteDaAssociare.getIdUtente())) {
							utentiNuovi.add(utenteDaAssociare);
						}
					}

				} 
				utente.setUtentiUtente(utentiNuovi);
			}
			
			
			mapper.save(utente);
		}
		
        risposta.setEsito(esito);
		return risposta;
	}
}
