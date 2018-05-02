package com.marte5.modello.richieste.acquista;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaAcquistaGenerica extends Richiesta{
	private String idUtente;
	private String nomeUtente;
	private String idEvento;
	private String nomeEvento;
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
}
