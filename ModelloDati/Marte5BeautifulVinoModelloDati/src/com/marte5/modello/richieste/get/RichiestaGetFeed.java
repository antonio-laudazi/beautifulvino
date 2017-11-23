package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaGetFeed extends Richiesta {

	private long idUtente;
	private long idUltimoFeed;
	private long dataUltimoFeed;
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
	 * @return the idUltimoFeed
	 */
	public long getIdUltimoFeed() {
		return idUltimoFeed;
	}
	/**
	 * @param idUltimoFeed the idUltimoFeed to set
	 */
	public void setIdUltimoFeed(long idUltimoFeed) {
		this.idUltimoFeed = idUltimoFeed;
	}
	/**
	 * @return the dataUltimoFeed
	 */
	public long getDataUltimoFeed() {
		return dataUltimoFeed;
	}
	/**
	 * @param dataUltimoFeed the dataUltimoFeed to set
	 */
	public void setDataUltimoFeed(long dataUltimoFeed) {
		this.dataUltimoFeed = dataUltimoFeed;
	}
	
	
}
