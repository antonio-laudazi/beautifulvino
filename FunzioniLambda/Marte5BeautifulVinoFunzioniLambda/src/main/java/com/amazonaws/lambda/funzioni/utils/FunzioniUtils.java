package com.amazonaws.lambda.funzioni.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.marte5.modello.Esito;

public class FunzioniUtils {
	
	public static final String DATE_FORMAT = "dd MMM yyyy";
	public static final Locale FUNCTIONS_LOCALE = Locale.ITALY;
	
	public static final String EVENTO_STATO_NEUTRO = "N";
	public static final String EVENTO_STATO_ACQUISTATO = "A";
	public static final String EVENTO_STATO_PREFERITO = "P";
	public static final String EVENTO_STATO_CANCELLATO = "D";
	
	public static String getStringVersion(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, FUNCTIONS_LOCALE);
		return sdf.format(date);
	}
	
	public static boolean isTokenExpired(String token) {
		
		
		String testToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlJFUXdPRUV3TURrMFFrSXhRVGt4TlRSR05VRkZNRFpFTnpOR09URTRRak0yTXpBNU5FWXhSQSJ9.eyJpc3MiOiJodHRwczovL3Rlc3RhdXRobWFydGU1LmV1LmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw1YTBiMjFjODlmMDExMzYxMjNhZWFhNDUiLCJhdWQiOiJodHRwczovL2JlYXV0aWZ1bHZpbm9hdXRoZW50aWNhdGlvbi5jb20iLCJpYXQiOjE1MTA4MjY0MTcsImV4cCI6MTUxMDkxMjgxNywiYXpwIjoiVzRQM3BVMFVBNHQ5UUk5VW9NSGVhaFRVVFdVRmQwa1AiLCJndHkiOiJwYXNzd29yZCJ9.ILz33qwV3d39qJhCXNxb1yqfZGBKaHtmASxnuSjVsrBp7jKQ3MxVnGIeLE2ZSitmpGNJ1WuWcass6euuP-DH0dzDuo_Cv0kutMHTHfF-QqU6qlfjYon27ag9MHtDPZpTOApDajUzsEu4ZxpOGKm_ni4zKkwJ7UqS-13sGkmpyHpq8EbVM2SNEbh8pWTbbz24J29jdFgcsWpsDW6uEJhGok2g0Qb6S_qX98rZ0voZrggBKTdrCg6H8mfwM42gw6ulKh8Z1L91vsWrwjbTYcwiEhSJvYZAOhRCVX02jcV-DN8iZc4kJOyl6BgsGOJvfOmcK6hUJY3erVL7PhICsao59w";
		
		
		
		return false;
	}
	
	public static long getEntitaId() {
		Date actualDate = new Date();
        return actualDate.getTime();
	}
	
	public static Esito getEsitoPositivo() {
		Esito esito = new Esito();
        esito.setCodice(EsitoHelper.ESITO_OK_CODICE);
        esito.setMessage(EsitoHelper.ESITO_OK_MESSAGGIO);
        return esito;
	}
}
