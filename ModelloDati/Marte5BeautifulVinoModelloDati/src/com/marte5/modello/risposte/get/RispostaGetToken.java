/**
 * 
 */
package com.marte5.modello.risposte.get;

import com.marte5.modello.Token;
import com.marte5.modello.risposte.Risposta;

/**
 * @author paolosalvadori
 *
 */
public class RispostaGetToken extends Risposta {
	
	private Token newToken;

	/**
	 * @return the token
	 */
	public Token getToken() {
		return newToken;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(Token token) {
		this.newToken = token;
	}
	
}
