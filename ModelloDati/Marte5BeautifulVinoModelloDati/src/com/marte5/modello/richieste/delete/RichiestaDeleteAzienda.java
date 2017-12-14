package com.marte5.modello.richieste.delete;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaDeleteAzienda extends Richiesta {
	private long idAzienda;

	/**
	 * @return the idAzienda
	 */
	public long getIdAzienda() {
		return idAzienda;
	}

	/**
	 * @param idAzienda the idAzienda to set
	 */
	public void setIdAzienda(long idAzienda) {
		this.idAzienda = idAzienda;
	}
}
