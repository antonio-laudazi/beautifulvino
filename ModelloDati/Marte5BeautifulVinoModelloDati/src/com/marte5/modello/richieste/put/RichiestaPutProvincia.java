package com.marte5.modello.richieste.put;

import com.marte5.modello2.Provincia;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaPutProvincia extends Richiesta {

	private Provincia provincia;

	/**
	 * @return the provincia
	 */
	public Provincia getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

}
