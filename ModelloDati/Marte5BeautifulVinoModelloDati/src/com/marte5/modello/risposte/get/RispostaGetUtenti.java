package com.marte5.modello.risposte.get;

import java.util.List;

import com.marte5.modello.Utente;
import com.marte5.modello.risposte.Risposta;

public class RispostaGetUtenti extends Risposta {
	List<Utente> utenti;

	/**
	 * @return the utenti
	 */
	public List<Utente> getUtenti() {
		return utenti;
	}

	/**
	 * @param utenti the utenti to set
	 */
	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}
}
