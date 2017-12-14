package com.marte5.modello.risposte.get;

import java.util.List;

import com.marte5.modello.Vino;
import com.marte5.modello.risposte.Risposta;

public class RispostaGetVini extends Risposta {

	List<Vino> vini;

	/**
	 * @return the vini
	 */
	public List<Vino> getVini() {
		return vini;
	}

	/**
	 * @param vini the vini to set
	 */
	public void setVini(List<Vino> vini) {
		this.vini = vini;
	}
}
