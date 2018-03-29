package com.amazonaws.lambda.funzioni.delete;

import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.delete.RichiestaDeleteGenerica;
import com.marte5.modello.risposte.delete.RispostaDeleteGenerica;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Azienda.EventoAzienda;
import com.marte5.modello2.Azienda.VinoAzienda;
import com.marte5.modello2.Evento.AziendaEvento;
import com.marte5.modello2.Evento.VinoEvento;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Vino.AziendaVino;
import com.marte5.modello2.Vino.EventoVino;

public class deleteAziendaGen implements RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica> {

    @Override
    public RispostaDeleteGenerica handleRequest(RichiestaDeleteGenerica input, Context context) {
    		context.getLogger().log("Input: " + input);
    		RispostaDeleteGenerica risposta = new RispostaDeleteGenerica();

    		String idAzienda = input.getIdAzienda();
        
        Esito esito = FunzioniUtils.getEsitoPositivo();
        
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " deleteAzienda ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idAzienda == null || idAzienda.equals("")) {
	        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " idAzienda NULL");
				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " idAzienda NULL");
				risposta.setEsito(esito);
				return risposta;
	        } else {
	        		Azienda aziendaDaCancellare = mapper.load(Azienda.class, idAzienda);
	        		if(aziendaDaCancellare == null) {
	        			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
	    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Azienda con id: " + idAzienda + " non trovato sul database");
	    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Azienda con id: " + idAzienda + " non trovato sul database");
	    				risposta.setEsito(esito);
	    				return risposta;
	        		} else {
	        			//cancello il collegamento col i vini
	        			List<VinoAzienda> listaVini = aziendaDaCancellare.getViniAziendaInt();
	        			if (listaVini != null) {
		        			for (VinoAzienda av : listaVini) {
		        				Vino vinoDaCanc = mapper.load(Vino.class, av.getIdVino());
		        				vinoDaCanc.setAziendaVinoInt(null);
		        				//Dovrei cancellare il vino???
		        				mapper.save(vinoDaCanc);
		        			}
	        			}
	        			//cancello il collegamento con gli eventi
	        			List<EventoAzienda> listaEventi = aziendaDaCancellare.getEventiAziendaInt();
	        			if (listaEventi != null) {
		        			for (EventoAzienda ev : listaEventi) {
		        				Evento eventoDaCanc = mapper.load(Evento.class, ev.getIdEvento());
		        				//devo cancellare l'evento???		  
		        				AziendaEvento aziendaOsp = eventoDaCanc.getAziendaOspitanteEventoInt();
		        				if (aziendaOsp.getIdAzienda() == aziendaDaCancellare.getIdAzienda() ) {	        				
		        					eventoDaCanc.setAziendaOspitanteEventoInt(null);
		        					mapper.save(eventoDaCanc);
		        				}
		        			}
	        			}
	        			try {
	        			//cancello l'azienda
						mapper.delete(aziendaDaCancellare);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_CANCELLAZIONE);
		    				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + " Eccezione nell'operazione interna di salvataggio");
		    				esito.setTrace(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE + e.getMessage());
		    				risposta.setEsito(esito);
		    				return risposta;
					}
	        			
	        			//cancello eventuale immagine dell'evento
	        			String immagineAziendaUrl = aziendaDaCancellare.getUrlImmagineAzienda();
	        			if(immagineAziendaUrl != null) {
	        				if(!immagineAziendaUrl.equals("")) {
		        				esito = FunzioniUtils.cancellaImmagine(immagineAziendaUrl);
		        			}
	        			}
	        		}
	        }
		}
        
		risposta.setEsito(esito);
        return risposta;
    }
}
