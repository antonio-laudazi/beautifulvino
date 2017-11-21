package com.marte5.modello.risposte.get;

import com.marte5.modello.Utente;
import com.marte5.modello.risposte.Risposta;

public class RispostaGetUtente extends Risposta {

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
