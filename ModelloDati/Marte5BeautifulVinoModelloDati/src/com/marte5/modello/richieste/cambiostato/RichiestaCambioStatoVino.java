/**
 * 
 */
package com.marte5.modello.richieste.cambiostato;

import com.marte5.modello.richieste.Richiesta;

/**
 * @author paolosalvadori
 *
 */
public class RichiestaCambioStatoVino extends Richiesta {

	private int idVino;
	private int idUtente; 
	private String newStatoVino;
	
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
	/**
	 * @return the newStatoVino
	 */
	public String getNewStatoVino() {
		return newStatoVino;
	}
	/**
	 * @param newStatoVino the newStatoVino to set
	 */
	public void setNewStatoVino(String newStatoVino) {
		this.newStatoVino = newStatoVino;
	}
	
	
}
