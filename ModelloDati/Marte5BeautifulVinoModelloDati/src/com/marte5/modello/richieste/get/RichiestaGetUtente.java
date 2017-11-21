package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

/***
 * 
 * @author paolosalvadori
 *
 */
public class RichiestaGetUtente extends Richiesta {

	private int idUtente;

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
