package com.marte5.modello.richieste.connect;

import java.util.List;

import com.marte5.modello.Azienda;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaConnectViniAUtente extends Richiesta {
	
	List<Azienda> aziendeViniDaAssociare;
	long idUtente;
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
