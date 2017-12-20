package com.amazonaws.lambda.funzioni.common;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;


public class BeautifulVinoPut implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {

	private final static String FUNCTION_NAME_PUT_AZIENDA = "putAziendaGen";
	private final static String FUNCTION_NAME_PUT_BADGE = "putBadgeGen";
	private final static String FUNCTION_NAME_PUT_EVENTO = "putEventoGen";
	private final static String FUNCTION_NAME_PUT_FEED = "putFeedGen";
	private final static String FUNCTION_NAME_PUT_IMAGE = "putImageGen";
	private final static String FUNCTION_NAME_PUT_UTENTE = "putUtenteGen";
	private final static String FUNCTION_NAME_PUT_VINO = "putVinoGen";
	private final static String FUNCTION_NAME_PUT_PROVINCIA = "putProvinciaGen";
	
	private static final String PACKAGE_NAME_GET = "com.amazonaws.lambda.funzioni.put.";
	
    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaPutGenerica risposta = new RispostaPutGenerica();

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
			RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> handler = (RequestHandler<RichiestaPutGenerica, RispostaPutGenerica>) Class.forName(PACKAGE_NAME_GET + functionName).newInstance();
			risposta = handler.handleRequest(input, context);
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " put generica ");
			esito.setTrace(e.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
        
        return risposta;
    }
    
    private boolean isFunctionNameValid(String nomeFunzione) {
    		List<String> funzioni = new ArrayList<>();
    		
    		funzioni.add(FUNCTION_NAME_PUT_AZIENDA);
    		funzioni.add(FUNCTION_NAME_PUT_BADGE);
    		funzioni.add(FUNCTION_NAME_PUT_EVENTO);
    		funzioni.add(FUNCTION_NAME_PUT_FEED);
    		funzioni.add(FUNCTION_NAME_PUT_IMAGE);
    		funzioni.add(FUNCTION_NAME_PUT_UTENTE);
    		funzioni.add(FUNCTION_NAME_PUT_VINO);
    		funzioni.add(FUNCTION_NAME_PUT_PROVINCIA);
    		
    		return funzioni.contains(nomeFunzione);
    		
    }
}
