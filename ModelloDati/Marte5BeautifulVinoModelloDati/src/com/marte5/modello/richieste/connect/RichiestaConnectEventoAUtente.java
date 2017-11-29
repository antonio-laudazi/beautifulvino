package com.marte5.modello.richieste.connect;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaConnectEventoAUtente extends Richiesta {
	private long idUtente;
	private long idEvento;
	private String statoEvento;
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
	/**
	 * @return the idEvento
	 */
	public long getIdEvento() {
		return idEvento;
	}
	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(long idEvento) {
		this.idEvento = idEvento;
	}
	/**
	 * @return the statoEvento
	 */
	public String getStatoEvento() {
		return statoEvento;
	}
	/**
	 * @param statoEvento the statoEvento to set
	 */
	public void setStatoEvento(String statoEvento) {
		this.statoEvento = statoEvento;
	}
	
}
