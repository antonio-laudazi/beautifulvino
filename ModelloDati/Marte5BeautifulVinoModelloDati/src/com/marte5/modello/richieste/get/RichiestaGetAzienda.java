/**
 * 
 */
package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

/**
 * @author paolosalvadori
 *
 */
public class RichiestaGetAzienda extends Richiesta {

	private String idAzienda;
	private String idUtente;

	/**
	 * @return the idAzienda
	 */
	public String getIdAzienda() {
		return idAzienda;
	}

	/**
	 * @param idAzienda the idAzienda to set
	 */
	public void setIdAzienda(String idAzienda) {
		this.idAzienda = idAzienda;
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
