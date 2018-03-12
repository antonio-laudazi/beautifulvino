package com.amazonaws.lambda.funzioni.common;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;


public class BeautifulVinoConnect implements RequestHandler<RichiestaConnectGenerica, RispostaConnectGenerica> {

	private final static String FUNCTION_NAME_CONNECT_BADGE_A_UTENTE = "connectBadgeAUtenteGen";
	private final static String FUNCTION_NAME_CONNECT_EVENTO_A_UTENTE = "connectEventoAUtenteGen";
	private final static String FUNCTION_NAME_CONNECT_VINI_A_AZIENDA = "connectViniAAziendaGen";
	private final static String FUNCTION_NAME_CONNECT_VINI_A_UTENTE = "connectViniAUtenteGen";
	private final static String FUNCTION_NAME_CONNECT_UTENTI_A_UTENTE = "connectUtentiAUtenteGen";
	
	private static final String PACKAGE_NAME_CONNECT = "com.amazonaws.lambda.funzioni.connect.";
	
    @Override
    public RispostaConnectGenerica handleRequest(RichiestaConnectGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaConnectGenerica risposta = new RispostaConnectGenerica();

        String functionName = input.getFunctionName();
        Esito esito = FunzioniUtils.getEsitoPositivo();
        
        if(!isFunctionNameValid(functionName)) {
        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " la funzione richiesta non esiste ");
			risposta.setEsito(esito);
			return risposta;
        }
        
        try {
			@SuppressWarnings("unchecked")
			RequestHandler<RichiestaConnectGenerica, RispostaConnectGenerica> handler = (RequestHandler<RichiestaConnectGenerica, RispostaConnectGenerica>) Class.forName(PACKAGE_NAME_CONNECT + functionName).newInstance();
			risposta = handler.handleRequest(input, context);
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " get generica ");
			esito.setTrace(e.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
        
        return risposta;
    }
    
    private boolean isFunctionNameValid(String nomeFunzione) {
    		List<String> funzioni = new ArrayList<>();
    		
    		funzioni.add(FUNCTION_NAME_CONNECT_BADGE_A_UTENTE);
    		funzioni.add(FUNCTION_NAME_CONNECT_EVENTO_A_UTENTE);
    		funzioni.add(FUNCTION_NAME_CONNECT_VINI_A_AZIENDA);
    		funzioni.add(FUNCTION_NAME_CONNECT_VINI_A_UTENTE);
    		funzioni.add(FUNCTION_NAME_CONNECT_UTENTI_A_UTENTE);
    		
    		return funzioni.contains(nomeFunzione);
    		
    }
}
