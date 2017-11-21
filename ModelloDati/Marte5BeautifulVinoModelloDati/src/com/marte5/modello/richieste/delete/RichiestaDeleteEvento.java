package com.marte5.modello.richieste.delete;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaDeleteEvento extends Richiesta {
	
	private long idEvento;
	private long dataEvento;

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
	 * @return the dataEvento
	 */
	public long getDataEvento() {
		return dataEvento;
	}

	/**
	 * @param dataEvento the dataEvento to set
	 */
	public void setDataEvento(long dataEvento) {
		this.dataEvento = dataEvento;
	}
}
