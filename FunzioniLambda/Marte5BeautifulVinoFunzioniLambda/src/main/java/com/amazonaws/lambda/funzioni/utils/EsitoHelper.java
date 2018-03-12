package com.amazonaws.lambda.funzioni.utils;

public class EsitoHelper {

	public static final int ESITO_OK_CODICE = 100;
	public static final int ESITO_WARN_CODICE = 101;
	public static final int ESITO_KO_CODICE_ERRORE_SALVATAGGIO = 200;
	public static final int ESITO_KO_CODICE_ERRORE_CANCELLAZIONE = 201;
	public static final int ESITO_KO_CODICE_ERRORE_PROCEDURA_LAMBDA = 300;
	public static final int ESITO_KO_CODICE_ERRORE_INPUT_NULL = 400;
	public static final int ESITO_KO_CODICE_ERRORE_GET = 500;
	
	public static final String ESITO_OK_MESSAGGIO = "Esito dell'operazione positivo";
	public static final String ESITO_KO_MESSAGGIO_ERRORE_SALVATAGGIO = "Errore nel salvataggio per la risorsa: ";
	public static final String ESITO_KO_MESSAGGIO_ERRORE_CANCELLAZIONE = "Errore nell'operazione di cancellazione; ";
	public static final String ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA = "Errore nell'esecuzione della funzione invocata: ";
	public static final String ESITO_KO_MESSAGGIO_ERRORE_INPUT_NULL = "E' stato passato un input nullo: ";
	public static final String ESITO_KO_MESSAGGIO_ERRORE_GET = "Errore nel recupero della risorsa: ";
	public static final String ESITO_KO_MESSAGGIO_WARNING_CANCELLAZIONE = "Warning nell'operazione di cancellazione";
}
