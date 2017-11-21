/**
 * 
 */
package com.marte5.modello.risposte.get;

import com.marte5.modello.Evento;
import com.marte5.modello.risposte.Risposta;

/**
 * @author paolosalvadori
 *
 */
public class RispostaGetEvento extends Risposta {

	private Evento evento;
	
	/**
	 * @return the evento
	 */
	public Evento getEvento() {
		return evento;
	}
	/**
	 * @param evento the evento to set
	 */
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
}
