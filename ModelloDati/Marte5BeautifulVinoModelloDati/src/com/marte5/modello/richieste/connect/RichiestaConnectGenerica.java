package com.marte5.modello.richieste.connect;

import java.util.List;

import com.marte5.modello2.Azienda;
import com.marte5.modello2.Badge;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Vino;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaConnectGenerica extends Richiesta {

	private List<Badge> badges;
	private String idUtente;
	private String idEvento;
	private String idVino;
	private long dataEvento;
	private String statoEvento;
	private String statoVino;
	private String statoUtente;
	private List<Vino> viniAzienda;
	private List<Utente> utenti;
	private String idAzienda;
	List<Azienda> aziendeViniDaAssociare;
	private int numeroPartecipanti;
	private int statoPreferitoEvento;
	private int statoAcquistatoEvento;
	
	/**
	 * @return the badges
	 */
	public List<Badge> getBadges() {
		return badges;
	}
	/**
	 * @param badges the badges to set
	 */
	public void setBadges(List<Badge> badges) {
		this.badges = badges;
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
	/**
	 * @return the viniAzienda
	 */
	public List<Vino> getViniAzienda() {
		return viniAzienda;
	}
	/**
	 * @param viniAzienda the viniAzienda to set
	 */
	public void setViniAzienda(List<Vino> viniAzienda) {
		this.viniAzienda = viniAzienda;
	}
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
	 * @return the aziendeViniDaAssociare
	 */
	public List<Azienda> getAziendeViniDaAssociare() {
		return aziendeViniDaAssociare;
	}
	/**
	 * @param aziendeViniDaAssociare the aziendeViniDaAssociare to set
	 */
	public void setAziendeViniDaAssociare(List<Azienda> aziendeViniDaAssociare) {
		this.aziendeViniDaAssociare = aziendeViniDaAssociare;
	}
	/**
	 * @return the utenti
	 */
	public List<Utente> getUtenti() {
		return utenti;
	}
	/**
	 * @param utenti the utenti to set
	 */
	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}
	/**
	 * @return the statoUtente
	 */
	public String getStatoUtente() {
		return statoUtente;
	}
	/**
	 * @param statoUtente the statoUtente to set
	 */
	public void setStatoUtente(String statoUtente) {
		this.statoUtente = statoUtente;
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
	 * @return the statoVino
	 */
	public String getStatoVino() {
		return statoVino;
	}
	/**
	 * @param statoVino the statoVino to set
	 */
	public void setStatoVino(String statoVino) {
		this.statoVino = statoVino;
	}
	/**
	 * @return the statoPreferitoEvento
	 */
	public int getStatoPreferitoEvento() {
		return statoPreferitoEvento;
	}
	/**
	 * @param statoPreferitoEvento the statoPreferitoEvento to set
	 */
	public void setStatoPreferitoEvento(int statoPreferitoEvento) {
		this.statoPreferitoEvento = statoPreferitoEvento;
	}
	/**
	 * @return the statoAcquistatoEvento
	 */
	public int getStatoAcquistatoEvento() {
		return statoAcquistatoEvento;
	}
	/**
	 * @param statoAcquistatoEvento the statoAcquistatoEvento to set
	 */
	public void setStatoAcquistatoEvento(int statoAcquistatoEvento) {
		this.statoAcquistatoEvento = statoAcquistatoEvento;
	}
	/**
	 * @return the dataEvento
	 */
	public int getNumeroPartecipanti() {
		return numeroPartecipanti;
	}
	/**
	 * @param dataEvento the dataEvento to set
	 */
	public void setNumeroPartecipanti(int numeroPartecipanti) {
		this.numeroPartecipanti = numeroPartecipanti;
	}
}
