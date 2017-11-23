package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.Utente;
import com.marte5.modello.Vino;
import com.marte5.modello.richieste.get.RichiestaGetEvento;
import com.marte5.modello.risposte.get.RispostaGetEventi;
import com.marte5.modello.risposte.get.RispostaGetEvento;

public class getEvento implements RequestHandler<RichiestaGetEvento, RispostaGetEvento> {

    @Override
    public RispostaGetEvento handleRequest(RichiestaGetEvento input, Context context) {
        context.getLogger().log("Input: " + input);
        
        //RispostaGetEvento risposta = getRispostaDiTest(input);
        RispostaGetEvento risposta = getRisposta(input);
        
        return risposta;
    }
    
    private RispostaGetEvento getRisposta(RichiestaGetEvento input) {
    		
    		RispostaGetEvento risposta = new RispostaGetEvento();
    		long idEvento = input.getIdEvento();
    		long idUtente = input.getIdUtente();
    		
    		Evento evento = new Evento();
    		
    		Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage("Esito corretto per la richiesta getEventi");
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEvento ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			if(idEvento == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idEvento nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			if(idUtente == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			evento = mapper.load(Evento.class, idEvento);
			Utente utente = mapper.load(Utente.class, idUtente);
			
			//gestione dello stato evento da visualizzare
			String statoEvento = FunzioniUtils.EVENTO_STATO_NEUTRO;
			if(utente == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente non trovato sul database, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			List<Evento> eventiUtente = utente.getEventiUtente();
			for (Iterator<Evento> iterator = eventiUtente.iterator(); iterator.hasNext();) {
				Evento eventoUtente = iterator.next();
				if(eventoUtente.getIdEvento() == idEvento) {
					//l'evento in questione è tra quelli associati all'utente
					statoEvento = eventoUtente.getStatoEvento();
				}
			}
			evento.setStatoEvento(statoEvento);
			
			//gestione degli utenti da visualizzare
			List<Utente> utentiEvento = evento.getIscrittiEvento();
			List<Utente> utentiEventoCompleti = new ArrayList<>();
			for (Iterator<Utente> iterator = utentiEvento.iterator(); iterator.hasNext();) {
				Utente utenteEvento = iterator.next();
				Utente utenteEventoDB = mapper.load(Utente.class, utenteEvento.getIdUtente());
				Utente utenteEventoCompleto = new Utente();
				
				utenteEventoCompleto.setIdUtente(utenteEventoDB.getIdUtente());
				utenteEventoCompleto.setNomeUtente(utenteEventoDB.getNomeUtente());
				utenteEventoCompleto.setCognomeUtente(utenteEventoDB.getCognomeUtente());
				utenteEventoCompleto.setEsperienzaUtente(utenteEventoDB.getEsperienzaUtente());
				utenteEventoCompleto.setLivelloUtente(utente.getLivelloUtente());
				utenteEventoCompleto.setUrlFotoUtente(utente.getUrlFotoUtente());
				
				utentiEventoCompleti.add(utenteEventoCompleto);
			}
			evento.setIscrittiEvento(utentiEventoCompleti);
			
			//gestione dei vini da visualizzare
			List<Vino> viniEvento = evento.getViniEvento();
			List<Vino> viniEventoCompleti = new ArrayList<>();
			for (Iterator<Vino> iterator = viniEvento.iterator(); iterator.hasNext();) {
				Vino vinoEvento = iterator.next();
				Vino vinoEventoDB = mapper.load(Vino.class, vinoEvento.getIdVino());
				Vino vinoEventoCompleto = new Vino();
				
				vinoEventoCompleto.setIdVino(vinoEventoDB.getIdVino());
				vinoEventoCompleto.setNomeVino(vinoEventoDB.getNomeVino());
				vinoEventoCompleto.setInfoVino(vinoEventoDB.getInfoVino());
				vinoEventoCompleto.setUrlLogoVino(vinoEventoDB.getUrlLogoVino());
				
				viniEventoCompleti.add(vinoEventoCompleto);
			}
			evento.setViniEvento(viniEventoCompleti);
		}
    		
    		risposta.setEsito(esito);
    		risposta.setEvento(evento);
    		
    		return risposta;
    }
    
    private RispostaGetEvento getRispostaDiTest(RichiestaGetEvento input) {
    		RispostaGetEvento risposta = new RispostaGetEvento();
        
        Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage("Esito corretto per la richiesta di evento per l'idEvento: " + input.getIdEvento() + " dell'utente: " + input.getIdUtente());
        
        Azienda aziendaEvento = new Azienda();
        aziendaEvento.setIdAzienda(1);
        aziendaEvento.setInfoAzienda("Info relative all'azienda ");
        aziendaEvento.setLatitudineAzienda(43.313333);
        aziendaEvento.setLongitudineAzienda(10.518434);
        aziendaEvento.setLuogoAzienda("Cecina");
        aziendaEvento.setNomeAzienda("Nome azienda Vinicola");
        aziendaEvento.setUrlLogoAzienda("");
        aziendaEvento.setZonaAzienda("Zona azienda");
        
        List<Utente> iscrittiEvento = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
        		Utente utente = new Utente();
        		
        		utente.setIdUtente(i);
        		utente.setNomeUtente("Nomeutente"+i);
        		utente.setCognomeUtente("Cognomeutente"+i);
        		utente.setEsperienzaUtente(4);
        		utente.setLivelloUtente("Principiante");
        		utente.setUrlFotoUtente("");
        		
        		iscrittiEvento.add(utente);
        }
        
        List<Vino> viniEvento = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
        		Vino vino = new Vino();
        		
        		vino.setIdVino(i);
        		vino.setNomeVino("Nomevino"+i);
        		vino.setInfoVino("Informazioni sul vino "+ i);
        		vino.setUrlLogoVino("");
        		
        		viniEvento.add(vino);
        }
        
        Evento evento = new  Evento();
        evento.setConvenzionataEvento(false);
        evento.setDataEvento((new Date()).getTime());
        evento.setIdEvento((new Date()).getTime());
        evento.setIndirizzoEvento("Via dello svolgimento dell'evento");
        evento.setLatitudineEvento(43.313333);
        evento.setLongitudineEvento(10.518434);
        evento.setLuogoEvento("Cecina");
        evento.setNumMaxPartecipantiEvento(50);
        evento.setNumPostiDisponibiliEvento(30);
        evento.setPrezzoEvento(100f);
        evento.setStatoEvento("P");
        evento.setTemaEvento("Tema dell'evento");
        evento.setTestoEvento("Questo è il testo dell'evento in questione a cui vorrei partecipare");
        evento.setTitoloEvento("Evento Vinicolo");
        evento.setUrlFotoEvento("");
        evento.setAziendaEvento(aziendaEvento);
        evento.setIscrittiEvento(iscrittiEvento);
        evento.setViniEvento(viniEvento);
        
        risposta.setEsito(esito);
        risposta.setEvento(evento);
        
        // TODO: implement your handler
        return risposta;
    }

}
