package com.marte5.modello.richieste.put;

import com.marte5.modello2.Vino;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaPutVino extends Richiesta {
	private Vino vino;

	/**
	 * @return the vino
	 */
	public Vino getVino() {
		return vino;
	}

	/**
	 * @param vino the vino to set
	 */
	public void setVino(Vino vino) {
		this.vino = vino;
	}
	
}
