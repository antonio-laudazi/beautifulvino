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
import com.marte5.modello.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello.Utente;
import com.marte5.modello.richieste.connect.RichiestaConnectBadgeAUtente;
import com.marte5.modello.risposte.connect.RispostaConnectBadgeAUtente;

public class connectBadgeAUtente implements RequestHandler<RichiestaConnectBadgeAUtente, RispostaConnectBadgeAUtente> {

    @Override
    public RispostaConnectBadgeAUtente handleRequest(RichiestaConnectBadgeAUtente input, Context context) {
        context.getLogger().log("Input: " + input);

        RispostaConnectBadgeAUtente risposta = getRisposta(input);
        return risposta;
    }

	private RispostaConnectBadgeAUtente getRisposta(RichiestaConnectBadgeAUtente input) {
		RispostaConnectBadgeAUtente risposta = new RispostaConnectBadgeAUtente();
		Esito esito = FunzioniUtils.getEsitoPositivo();
        
        long idUtente = input.getIdUtente();
		List<Badge> badgeUtente = input.getBadges();
		
		AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " connectEventoAUtente ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			if(badgeUtente == null || badgeUtente.size() == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " non ci sono badge da associare, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			if(idUtente == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
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
			
			List<Badge> badgeNuovi = new ArrayList<>();
			List<Badge> badgeAttualmenteAssociati = utente.getBadgeUtente();
			
			for (Iterator<Badge> iterator = badgeAttualmenteAssociati.iterator(); iterator.hasNext();) {
				Badge badgePresente = iterator.next();
				for (Iterator<Badge> iterator2 = badgeUtente.iterator(); iterator2.hasNext();) {
					Badge badgeDaAssociare = iterator2.next();
					if(badgePresente.getIdBadge() != badgeDaAssociare.getIdBadge()) {
						badgeNuovi.add(badgeDaAssociare);
					}
				}
			}
			utente.setBadgeUtente(badgeUtente);
			try {
				mapper.save(utente);
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " Errore nel salvataggio dell'utente aggiornato");
		        esito.setTrace(e.getMessage());
		        risposta.setEsito(esito);
		        return risposta;
			}
		}
		
        risposta.setEsito(esito);
		return risposta;
	}
}
