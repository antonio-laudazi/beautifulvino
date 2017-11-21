/**
 * 
 */
package com.marte5.modello.risposte.get;

import com.marte5.modello.Vino;
import com.marte5.modello.risposte.Risposta;

/**
 * @author paolosalvadori
 *
 */
public class RispostaGetVino extends Risposta {

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
