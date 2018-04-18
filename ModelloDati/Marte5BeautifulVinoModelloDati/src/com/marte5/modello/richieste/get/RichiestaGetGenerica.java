package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaGetGenerica extends Richiesta {

	private String idUtenteLog;
	private String idUtentePadre;
	private String idAzienda;
	private String idUtente;
	
	private String idBadge;
	
	private String idProvincia; //puo' valere -1 oppure un codice. Se vale -1 rilascio il filtro sulla provincia
	private int numEventiVisualizzati;
	private String idUltimoEvento;
	private long dataUltimoEvento;
	
	private String idEvento;
	private long dataEvento;
	
	private String idUltimoFeed;
	private long dataUltimoFeed;
	
	private String oldToken;
	
	private String idVino;
	
	private String emailUtente;
	private String passwordUtente;
	private String latitudineUtente;
	private String longitudineUtente;
	
	private String elencoCompleto;
	
	
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
	 * @return the idUtenteLog
	 */
	public String getIdUtenteLog() {
		return idUtenteLog;
	}
	/**
	 * @param idUtenteLog the idUtenteLog to set
	 */
	public void setIdUtenteLog(String idUtenteLog) {
		this.idUtenteLog = idUtenteLog;
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
	 * @return the idBadge
	 */
	public String getIdBadge() {
		return idBadge;
	}
	/**
	 * @param idBadge the idBadge to set
	 */
	public void setIdBadge(String idBadge) {
		this.idBadge = idBadge;
	}
	/**
	 * @return the idProvincia
	 */
	public String getIdProvincia() {
		return idProvincia;
	}
	/**
	 * @param idProvincia the idProvincia to set
	 */
	public void setIdProvincia(String idProvincia) {
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
	public String getIdUltimoEvento() {
		return idUltimoEvento;
	}
	/**
	 * @param idUltimoEvento the idUltimoEvento to set
	 */
	public void setIdUltimoEvento(String idUltimoEvento) {
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
	 * @return the idUltimoFeed
	 */
	public String getIdUltimoFeed() {
		return idUltimoFeed;
	}
	/**
	 * @param idUltimoFeed the idUltimoFeed to set
	 */
	public void setIdUltimoFeed(String idUltimoFeed) {
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
	public String getIdVino() {
		return idVino;
	}
	/**
	 * @param idVino the idVino to set
	 */
	public void setIdVino(String idVino) {
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
	/**
	 * @return the elencoCompleto
	 */
	public String getElencoCompleto() {
		return elencoCompleto;
	}
	/**
	 * @param elencoCompleto the elencoCompleto to set
	 */
	public void setElencoCompleto(String elencoCompleto) {
		this.elencoCompleto = elencoCompleto;
	}
	/**
	 * @return the idUtentePadre
	 */
	public String getIdUtentePadre() {
		return idUtentePadre;
	}
	/**
	 * @param idUtentePadre the idUtentePadre to set
	 */
	public void setIdUtentePadre(String idUtentePadre) {
		this.idUtentePadre = idUtentePadre;
	}
}
