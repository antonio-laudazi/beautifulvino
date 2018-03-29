package com.marte5.modello.richieste.delete;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaDeleteGenerica extends Richiesta {
	
	private String idAzienda;
	private String idEvento;
	private long dataEvento;
	private String idFeed;
	private long dataFeed;
	private String idUtente;
	private String idVino;
	
	/**
	 * @return the idAzienda
	 */
	public String getIdAzienda() {
		return idAzienda;
	}
	/**
	 * @param idAzienda the idAzienda to set
	 */
	public void setIdAzienda(String idAzienda) {
		this.idAzienda = idAzienda;
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
	public String getIdFeed() {
		return idFeed;
	}
	/**
	 * @param idFeed the idFeed to set
	 */
	public void setIdFeed(String idFeed) {
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
	 * @return the idVino
	 */
	public String getIdVino() {
		return idVino;
	}
	/**
	 * @param idVino the idVino to set
	 */
	public void setIdVino(String idVino) {
		this.idVino = idVino;
	}
}
