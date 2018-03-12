package com.marte5.modello.richieste.connect;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaConnectEventoAUtente extends Richiesta {
	private String idUtente;
	private String idEvento;
	private String statoEvento;
	/**
	 * @return the idUtente
	 */
	public String getIdUtente() {
		return idUtente;
	}
	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	/**
	 * @return the idEvento
	 */
	public String getIdEvento() {
		return idEvento;
	}
	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(String idEvento) {
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
