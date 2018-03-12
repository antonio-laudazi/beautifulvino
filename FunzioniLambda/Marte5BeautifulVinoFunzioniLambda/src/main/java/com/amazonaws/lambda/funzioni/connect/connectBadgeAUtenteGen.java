package com.amazonaws.lambda.funzioni.connect;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello2.Badge;
import com.marte5.modello.Esito;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.BadgeUtente;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;

public class connectBadgeAUtenteGen implements RequestHandler<RichiestaConnectGenerica, RispostaConnectGenerica> {

    @Override
    public RispostaConnectGenerica handleRequest(RichiestaConnectGenerica input, Context context) {
        context.getLogger().log("Input: " + input);

        RispostaConnectGenerica risposta = getRisposta(input);
        return risposta;
    }

	private RispostaConnectGenerica getRisposta(RichiestaConnectGenerica input) {
		RispostaConnectGenerica risposta = new RispostaConnectGenerica();
		Esito esito = FunzioniUtils.getEsitoPositivo();
        
        String idUtente = input.getIdUtente();
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
			if(idUtente == null || idUtente.equals("")) {
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
			
			List<BadgeUtente> badgesAttualmenteAssociati = utente.getBadgeUtenteInt();
			List<BadgeUtente> badgeNuovi = new ArrayList<>();
			//riempio l'array nuovo con i badge che ho ricevuto
			for (Badge badge : badgeUtente) {
				BadgeUtente b = new BadgeUtente();
				b.setIdBadge(badge.getIdBadge());
				badgeNuovi.add(b);
			}
			
			if(badgesAttualmenteAssociati != null) {
				for (BadgeUtente badgeUtente2 : badgesAttualmenteAssociati) {
					//if(!badgeNuovi.contains(badgeUtente2)) {
					if(!containsBadge(badgeNuovi, badgeUtente2)) {
						badgeNuovi.add(badgeUtente2);
					}
				}
			}
			
			utente.setBadgeUtenteInt(badgeNuovi);
			utente.setNumTotBadge(badgeNuovi.size());
			
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
	
	private boolean containsBadge(List<BadgeUtente> badges, BadgeUtente badge) {
		for (BadgeUtente badgeUtente : badges) {
			if(badgeUtente.getIdBadge().equals(badge.getIdBadge())) {
				return true;
			}
		}
		return false;
	}
}
