package com.marte5.modello.richieste.add;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaAddCreditiUtente extends Richiesta {
	
	private int creditiDaAggiungere;
	private long idUtente;
	
	/**
	 * @return the creditiDaAggiungere
	 */
	public int getCreditiDaAggiungere() {
		return creditiDaAggiungere;
	}
	/**
	 * @param creditiDaAggiungere the creditiDaAggiungere to set
	 */
	public void setCreditiDaAggiungere(int creditiDaAggiungere) {
		this.creditiDaAggiungere = creditiDaAggiungere;
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
