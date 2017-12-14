package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

/***
 * 
 * @author paolosalvadori
 *
 */
public class RichiestaGetUtente extends Richiesta {

	private long idUtente;

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
