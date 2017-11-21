package com.marte5.modello.richieste.put;

import com.marte5.modello.Utente;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaPutUtente extends Richiesta {

	private Utente utente;

	/**
	 * @return the utente
	 */
	public Utente getUtente() {
		return utente;
	}

	/**
	 * @param utente the utente to set
	 */
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
}
