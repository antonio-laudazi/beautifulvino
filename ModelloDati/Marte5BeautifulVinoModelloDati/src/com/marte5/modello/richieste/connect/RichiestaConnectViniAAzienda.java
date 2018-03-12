package com.marte5.modello.richieste.connect;

import java.util.List;

import com.marte5.modello2.Vino;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaConnectViniAAzienda extends Richiesta {

	private List<Vino> viniAzienda;
	private String idAzienda;
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
}
