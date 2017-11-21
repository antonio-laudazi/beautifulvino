/**
 * 
 */
package com.marte5.modello.risposte.get;

import java.util.List;

import com.marte5.modello.Evento;
import com.marte5.modello.risposte.Risposta;

/**
 * @author paolosalvadori
 *
 */
public class RispostaGetEventi extends Risposta {

	private int numTotEventi;
	private List<Evento> eventi;
	
	/**
	 * @return the numeroTotaleEventi
	 */
	public int getNumTotEventi() {
		return numTotEventi;
	}
	/**
	 * @param numeroTotaleEventi the numeroTotaleEventi to set
	 */
	public void setNumTotEventi(int numeroTotaleEventi) {
		this.numTotEventi = numeroTotaleEventi;
	}
	/**
	 * @return the eventi
	 */
	public List<Evento> getEventi() {
		return eventi;
	}
	/**
	 * @param eventi the eventi to set
	 */
	public void setEventi(List<Evento> eventi) {
		this.eventi = eventi;
	}
	
	
}
