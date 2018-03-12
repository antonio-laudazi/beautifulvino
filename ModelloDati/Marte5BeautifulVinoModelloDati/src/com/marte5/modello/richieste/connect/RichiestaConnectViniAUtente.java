package com.marte5.modello.richieste.connect;

import java.util.List;

import com.marte5.modello2.Azienda;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaConnectViniAUtente extends Richiesta {
	
	List<Azienda> aziendeViniDaAssociare;
	String idUtente;
	/**
	 * @return the viniDaAssociare
	 */
	public List<Azienda> getAziendeViniDaAssociare() {
		return aziendeViniDaAssociare;
	}
	/**
	 * @param viniDaAssociare the viniDaAssociare to set
	 */
	public void setAziendeViniDaAssociare(List<Azienda> aziendeViniDaAssociare) {
		this.aziendeViniDaAssociare = aziendeViniDaAssociare;
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
}
