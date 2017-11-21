/**
 * 
 */
package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

/**
 * @author paolosalvadori
 *
 */
public class RichiestaGetEvento extends Richiesta {

	private int idEvento;
	private int idUtente;
	
	/**
	 * @return the idEvento
	 */
	public int getIdEvento() {
		return idEvento;
	}
	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
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
