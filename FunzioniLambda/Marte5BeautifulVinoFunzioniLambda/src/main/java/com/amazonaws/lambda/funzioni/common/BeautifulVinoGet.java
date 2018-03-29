package com.amazonaws.lambda.funzioni.common;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;


public class BeautifulVinoGet implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

	private final static String FUNCTION_NAME_GET_AZIENDA = "getAziendaGen";
	private final static String FUNCTION_NAME_GET_AZIENDE = "getAziendeGen";
	private final static String FUNCTION_NAME_GET_BADGE = "getBadgeGen";
	private final static String FUNCTION_NAME_GET_BADGES= "getBadgesGen";
	private final static String FUNCTION_NAME_GET_EVENTI = "getEventiGen";
	private final static String FUNCTION_NAME_GET_EVENTI_UTENTE = "getEventiUtenteGen";
	private final static String FUNCTION_NAME_GET_EVENTO = "getEventoGen";
	private final static String FUNCTION_NAME_GET_FEED = "getFeedGen";
	private final static String FUNCTION_NAME_GET_PROVINCE = "getProvinceGen";
	private final static String FUNCTION_NAME_GET_TOKEN = "getTokenGen";
	private final static String FUNCTION_NAME_GET_UTENTE = "getUtenteGen";
	private final static String FUNCTION_NAME_GET_UTENTI = "getUtentiGen";
	private final static String FUNCTION_NAME_GET_VINI = "getViniGen";
	private final static String FUNCTION_NAME_GET_VINO = "getVinoGen";
	private final static String FUNCTION_NAME_GET_VINIEVENTO = "getViniEventoGen";
	private final static String FUNCTION_NAME_GET_PRESENZAUTENTE = "getPresenzaUtente";
	
	
	private static final String PACKAGE_NAME_GET = "com.amazonaws.lambda.funzioni.get.";
	
    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaGetGenerica risposta = new RispostaGetGenerica();

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
			RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> handler = (RequestHandler<RichiestaGetGenerica, RispostaGetGenerica>) Class.forName(PACKAGE_NAME_GET + functionName).newInstance();
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
    		
    		funzioni.add(FUNCTION_NAME_GET_AZIENDA);
    		funzioni.add(FUNCTION_NAME_GET_AZIENDE);
    		funzioni.add(FUNCTION_NAME_GET_BADGE);
    		funzioni.add(FUNCTION_NAME_GET_BADGES);
    		funzioni.add(FUNCTION_NAME_GET_EVENTI);
    		funzioni.add(FUNCTION_NAME_GET_EVENTO);
    		funzioni.add(FUNCTION_NAME_GET_FEED);
    		funzioni.add(FUNCTION_NAME_GET_PROVINCE);
    		funzioni.add(FUNCTION_NAME_GET_TOKEN);
    		funzioni.add(FUNCTION_NAME_GET_UTENTE);
    		funzioni.add(FUNCTION_NAME_GET_UTENTI);
    		funzioni.add(FUNCTION_NAME_GET_VINI);
    		funzioni.add(FUNCTION_NAME_GET_VINO);
    		funzioni.add(FUNCTION_NAME_GET_VINIEVENTO);
    		funzioni.add(FUNCTION_NAME_GET_EVENTI_UTENTE);
    		funzioni.add(FUNCTION_NAME_GET_PRESENZAUTENTE);
    		
    		
    		return funzioni.contains(nomeFunzione);
    		
    }
}
