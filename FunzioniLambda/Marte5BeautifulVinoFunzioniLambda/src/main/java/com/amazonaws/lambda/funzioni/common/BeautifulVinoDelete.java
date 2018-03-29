package com.amazonaws.lambda.funzioni.common;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.delete.RichiestaDeleteGenerica;
import com.marte5.modello.risposte.delete.RispostaDeleteGenerica;

public class BeautifulVinoDelete implements RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica> {

	private final static String FUNCTION_NAME_DELETE_AZIENDA = "deleteAziendaGen";
	private final static String FUNCTION_NAME_DELETE_EVENTO = "deleteEventoGen";
	private final static String FUNCTION_NAME_DELETE_FEED = "deleteFeedGen";
	private final static String FUNCTION_NAME_DELETE_UTENTE = "deleteUtenteGen";
	private final static String FUNCTION_NAME_DELETE_VINO = "deleteVinoGen";
	
	private static final String PACKAGE_NAME_GET = "com.amazonaws.lambda.funzioni.delete.";
	
    @Override
    public RispostaDeleteGenerica handleRequest(RichiestaDeleteGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaDeleteGenerica risposta = new RispostaDeleteGenerica();

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
			RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica> handler = (RequestHandler<RichiestaDeleteGenerica, RispostaDeleteGenerica>) Class.forName(PACKAGE_NAME_GET + functionName).newInstance();
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
    		
    		funzioni.add(FUNCTION_NAME_DELETE_AZIENDA);
    		funzioni.add(FUNCTION_NAME_DELETE_FEED);
    		funzioni.add(FUNCTION_NAME_DELETE_UTENTE);
    		funzioni.add(FUNCTION_NAME_DELETE_EVENTO);
    		funzioni.add(FUNCTION_NAME_DELETE_VINO);
    		return funzioni.contains(nomeFunzione);
    }
}
