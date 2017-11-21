/**
 * 
 */
package com.marte5.modello.richieste.cambiostato;

import com.marte5.modello.richieste.Richiesta;

/**
 * @author paolosalvadori
 *
 */
public class RichiestaCambioStatoEvento extends Richiesta {

	private int idEvento;
	private int idUtente;
	private String newStatoEvento;
	
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
	/**
	 * @return the newStatoEvento
	 */
	public String getNewStatoEvento() {
		return newStatoEvento;
	}
	/**
	 * @param newStatoEvento the newStatoEvento to set
	 */
	public void setNewStatoEvento(String newStatoEvento) {
		this.newStatoEvento = newStatoEvento;
	}
	
	
}
