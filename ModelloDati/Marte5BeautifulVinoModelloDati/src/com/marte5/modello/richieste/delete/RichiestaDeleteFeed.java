package com.marte5.modello.richieste.delete;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaDeleteFeed extends Richiesta {

	private long idFeed;
	private long dataFeed;
	/**
	 * @return the idFeed
	 */
	public long getIdFeed() {
		return idFeed;
	}
	/**
	 * @param idFeed the idFeed to set
	 */
	public void setIdFeed(long idFeed) {
		this.idFeed = idFeed;
	}
	/**
	 * @return the dataFeed
	 */
	public long getDataFeed() {
		return dataFeed;
	}
	/**
	 * @param dataFeed the dataFeed to set
	 */
	public void setDataFeed(long dataFeed) {
		this.dataFeed = dataFeed;
	}
}
