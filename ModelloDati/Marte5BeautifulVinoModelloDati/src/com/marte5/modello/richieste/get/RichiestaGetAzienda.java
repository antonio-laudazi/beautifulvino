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

	private long idAzienda;
	private long idUtente;

	/**
	 * @return the idAzienda
	 */
	public long getIdAzienda() {
		return idAzienda;
	}

	/**
	 * @param idAzienda the idAzienda to set
	 */
	public void setIdAzienda(long idAzienda) {
		this.idAzienda = idAzienda;
	}

	/**
	 * @return the idUtente
	 */
	public long getIdUtente() {
		return idUtente;
	}

	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	
	
}
