/**
 * 
 */
package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

/**
 * @author paolosalvadori
 *
 */
public class RichiestaGetVino extends Richiesta {

	private int idVino;
	private int idUtente;

	/**
	 * @return the idVino
	 */
	public int getIdVino() {
		return idVino;
	}

	/**
	 * @param idVino the idVino to set
	 */
	public void setIdVino(int idVino) {
		this.idVino = idVino;
	}

	/**
	 * @return the idUtente
	 */
	public int getIdUtente() {
		return idUtente;
	}

	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
}
