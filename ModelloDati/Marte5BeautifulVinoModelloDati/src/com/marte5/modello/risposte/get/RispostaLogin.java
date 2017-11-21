package com.marte5.modello.risposte.get;

import com.marte5.modello.Token;
import com.marte5.modello.Utente;
import com.marte5.modello.risposte.Risposta;

public class RispostaLogin extends Risposta {
	
	private Utente utente;
	private Token token;
	
	/**
	 * @return the utente
	 */
	public Utente getUtente() {
		return utente;
	}
	/**
	 * @param utente the utente to set
	 */
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
	}
}
