package com.marte5.modello.richieste.connect;

import java.util.List;

import com.marte5.modello.Vino;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaConnectViniAAzienda extends Richiesta {

	private List<Vino> viniAzienda;
	private long idAzienda;
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
	public long getIdAzienda() {
		return idAzienda;
	}
	/**
	 * @param idAzienda the idAzienda to set
	 */
	public void setIdAzienda(long idAzienda) {
		this.idAzienda = idAzienda;
	}
}
