package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaGetGenerica extends Richiesta {

	private long idAzienda;
	private long idUtente;
	
	private long idBadge;
	
	private int idProvincia; //puo' valere -1 oppure un codice. Se vale -1 rilascio il filtro sulla provincia
	private int numEventiVisualizzati;
	private long idUltimoEvento;
	private long dataUltimoEvento;
	
	private long idEvento;
	private long dataEvento;
	
	private long idUltimoFeed;
	private long dataUltimoFeed;
	
	private String oldToken;
	
	private int idVino;
	
	private String emailUtente;
	private String passwordUtente;
	private String latitudineUtente;
	private String longitudineUtente;
	
	
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
	 * @return the idBadge
	 */
	public long getIdBadge() {
		return idBadge;
	}
	/**
	 * @param idBadge the idBadge to set
	 */
	public void setIdBadge(long idBadge) {
		this.idBadge = idBadge;
	}
	/**
	 * @return the idProvincia
	 */
	public int getIdProvincia() {
		return idProvincia;
	}
	/**
	 * @param idProvincia the idProvincia to set
	 */
	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}
	/**
	 * @return the numEventiVisualizzati
	 */
	public int getNumEventiVisualizzati() {
		return numEventiVisualizzati;
	}
	/**
	 * @param numEventiVisualizzati the numEventiVisualizzati to set
	 */
	public void setNumEventiVisualizzati(int numEventiVisualizzati) {
		this.numEventiVisualizzati = numEventiVisualizzati;
	}
	/**
	 * @return the idUltimoEvento
	 */
	public long getIdUltimoEvento() {
		return idUltimoEvento;
	}
	/**
	 * @param idUltimoEvento the idUltimoEvento to set
	 */
	public void setIdUltimoEvento(long idUltimoEvento) {
		this.idUltimoEvento = idUltimoEvento;
	}
	/**
	 * @return the dataUltimoEvento
	 */
	public long getDataUltimoEvento() {
		return dataUltimoEvento;
	}
	/**
	 * @param dataUltimoEvento the dataUltimoEvento to set
	 */
	public void setDataUltimoEvento(long dataUltimoEvento) {
		this.dataUltimoEvento = dataUltimoEvento;
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
	/**
	 * @return the oldToken
	 */
	public String getOldToken() {
		return oldToken;
	}
	/**
	 * @param oldToken the oldToken to set
	 */
	public void setOldToken(String oldToken) {
		this.oldToken = oldToken;
	}
	/**
	 * @return the idVino
	 */
	public int getIdVino() {
		return idVino;
	}
	/**
	 * @param idVino the idVino to set
	 */
	public void setIdVino(int idVino) {
		this.idVino = idVino;
	}
	/**
	 * @return the emailUtente
	 */
	public String getEmailUtente() {
		return emailUtente;
	}
	/**
	 * @param emailUtente the emailUtente to set
	 */
	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}
	/**
	 * @return the passwordUtente
	 */
	public String getPasswordUtente() {
		return passwordUtente;
	}
	/**
	 * @param passwordUtente the passwordUtente to set
	 */
	public void setPasswordUtente(String passwordUtente) {
		this.passwordUtente = passwordUtente;
	}
	/**
	 * @return the latitudineUtente
	 */
	public String getLatitudineUtente() {
		return latitudineUtente;
	}
	/**
	 * @param latitudineUtente the latitudineUtente to set
	 */
	public void setLatitudineUtente(String latitudineUtente) {
		this.latitudineUtente = latitudineUtente;
	}
	/**
	 * @return the longitudineUtente
	 */
	public String getLongitudineUtente() {
		return longitudineUtente;
	}
	/**
	 * @param longitudineUtente the longitudineUtente to set
	 */
	public void setLongitudineUtente(String longitudineUtente) {
		this.longitudineUtente = longitudineUtente;
	}
}
