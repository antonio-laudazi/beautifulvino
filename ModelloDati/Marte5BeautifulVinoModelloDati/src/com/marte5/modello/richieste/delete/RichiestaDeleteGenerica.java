package com.marte5.modello.richieste.delete;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaDeleteGenerica extends Richiesta {
	
	private long idAzienda;
	private long idEvento;
	private long dataEvento;
	private long idFeed;
	private long dataFeed;
	private long idUtente;
	
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
