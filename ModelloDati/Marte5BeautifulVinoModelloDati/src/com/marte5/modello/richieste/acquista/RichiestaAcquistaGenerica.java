package com.marte5.modello.richieste.acquista;

import com.marte5.modello.richieste.Richiesta;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Utente;

public class RichiestaAcquistaGenerica extends Richiesta{
	private String idUtente;
	private String nomeUtente;
	private Utente utente;
	private Evento evento;
	private String idEvento;
	private String nomeEvento;
	private int acquista;
	private int numeroPartecipanti;
	
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
	 * @return the NomeUtente
	 */
	public String getNomeUtente() {
		return nomeUtente;
	}
	
	/**
	 * @param nomeUtente the nomeUtente to set
	 */
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
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
	 * @return the idEvento
	 */
	public String getNomeEvento() {
		return nomeEvento;
	}
	
	/**
	 * @param nomeEvento the nomeEvento to set
	 */
	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}
	
	/**
	 * @return the numeroPartecipanti
	 */
	public int getNumeroPartecianti() {
		return numeroPartecipanti;
	}
	
	/**
	 * @param numeroPartecipanti the numeroPartecipanti to set
	 */
	public void setNumeroPartecipanti(int numeroPartecipanti) {
		this.numeroPartecipanti = numeroPartecipanti;
	}
	
	/**
	 * @return the acquista
	 */
	public int getAcquista() {
		return acquista;
	}
	
	/**
	 * @param acquista the acquista to set
	 */
	public void setAcquista(int acquista) {
		this.acquista = acquista;
	}
	/**
	 * @return the acquista
	 */
	public Utente getUtente() {
		return utente;
	}
	
	/**
	 * @param acquista the acquista to set
	 */
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	/**
	 * @return the evento
	 */
	public Evento getEvento() {
		return evento;
	}
	
	/**
	 * @param evento the evento to set
	 */
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
}
