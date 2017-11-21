/**
 * 
 */
package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

/**
 * @author paolosalvadori
 *
 */
public class RichiestaGetToken extends Richiesta {

	private String oldToken;
	private String idUtente;
	
	/**
	 * @return the oldToken
	 */
	public String getOldToken() {
		return oldToken;
	}
	/**
	 * @param oldToken the oldToken to set
	 */
	public void setOldToken(String oldToken) {
		this.oldToken = oldToken;
	}
	/**
	 * @return the idUtente
	 */
	public String getIdUtente() {
		return idUtente;
	}
	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	
	
}
