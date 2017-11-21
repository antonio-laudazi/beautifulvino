package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.Token;
import com.marte5.modello.Utente;
import com.marte5.modello.richieste.get.RichiestaLogin;
import com.marte5.modello.risposte.get.RispostaLogin;

public class login implements RequestHandler<RichiestaLogin, RispostaLogin> {

    @Override
    public RispostaLogin handleRequest(RichiestaLogin input, Context context) {
    		context.getLogger().log("Input:  " + input);
    	
    		RispostaLogin risposta = getRispostaDiTest(input);
    		return risposta;
    }
    
    private RispostaLogin getRispostaDiTest(RichiestaLogin input) {
    	
    		RispostaLogin risposta = new RispostaLogin();
    		
		Esito esito = new Esito();
		esito.setCodice(100);
		esito.setMessage("Login avvenuto correttamente! dati di input: username: " + input.getEmailUtente() + " password: " + input.getPasswordUtente());
		
		Token token = new Token();
		token.setToken("1a2b3c4d5e");
		
		Utente utente = new Utente();
		utente.setIdUtente(1);
		utente.setNomeUtente("Paolo");
		utente.setCognomeUtente("Salvadori");
		utente.setBiografiaUtente("Mi chiamo Paolo e sono un programmatore e fotografo");
		utente.setCreditiUtente(10);
		utente.setEsperienzaUtente(23);
		utente.setLivelloUtente("Esperto");
		utente.setUrlFotoUtente("");
		
		List<Evento> eventi = new ArrayList<>();//questi devono essere gli eventi relativi all'utente e con i dati dell'utente (stato evento)
		for(int i = 0; i < 5; i++) {
			Evento evento = new Evento();
			
			evento.setIdEvento((new Date()).getTime());
			evento.setTitoloEvento("Titolo Evento " + (i+1));
			evento.setTestoEvento("Questo è il testo per l'evento " + (i+1) + " che descrive quello che troverete durante l'evento.");
			evento.setTemaEvento("Tema evento " + (i+1));
			evento.setIndirizzoEvento("Via Dell'evento " + (i+1) + ", Citta, Provincia, CAP" );
			evento.setLuogoEvento("Luogo dell'evento");
			evento.setDataEvento((new Date()).getTime());
			evento.setDataEventoStringa(FunzioniUtils.getStringVersion(new Date()));
			evento.setUrlFotoEvento("");
			evento.setStatoEvento("N");
			evento.setLatitudineEvento(43.313333);
			evento.setLongitudineEvento(10.518434);
			evento.setNumMaxPartecipantiEvento(20);
			evento.setNumPostiDisponibiliEvento(7);
			
			eventi.add(evento);
			
		}
		utente.setEventiUtente(eventi);
		utente.setNumTotEventi(eventi.size());//mettere il numero totale degli eventi che quell'utente puo' vedere e non il numero degli eventi che sto passando in questo momentoto
		
		risposta.setToken(token);
		risposta.setUtente(utente);
		risposta.setEsito(esito);

		// TODO: implement your handler
		return risposta;
    }

}
