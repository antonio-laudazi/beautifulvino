package com.marte5.modello.richieste.put;

import com.marte5.modello2.Azienda;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaPutAzienda extends Richiesta {

	private Azienda azienda;

	/**
	 * @return the azienda
	 */
	public Azienda getAzienda() {
		return azienda;
	}

	/**
	 * @param azienda the azienda to set
	 */
	public void setAzienda(Azienda azienda) {
		this.azienda = azienda;
	}
}
