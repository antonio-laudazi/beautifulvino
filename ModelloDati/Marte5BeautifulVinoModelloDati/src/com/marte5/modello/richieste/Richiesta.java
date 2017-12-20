/**
 * 
 */
package com.marte5.modello.richieste;

import com.marte5.modello.Token;

/**
 * @author paolosalvadori
 *
 */
public class Richiesta {

	private Token token;
	private String functionName;

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

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	
}
