package com.marte5.modello.risposte.get;

import java.util.List;

import com.marte5.modello.Azienda;
import com.marte5.modello.risposte.Risposta;

public class RispostaGetAziende extends Risposta {

	private List<Azienda> aziende;

	/**
	 * @return the aziende
	 */
	public List<Azienda> getAziende() {
		return aziende;
	}

	/**
	 * @param aziende the aziende to set
	 */
	public void setAziende(List<Azienda> aziende) {
		this.aziende = aziende;
	}
}
