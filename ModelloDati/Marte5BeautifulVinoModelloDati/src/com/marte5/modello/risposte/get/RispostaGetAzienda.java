/**
 * 
 */
package com.marte5.modello.risposte.get;

import java.util.List;

import com.marte5.modello.Azienda;
import com.marte5.modello.Evento;
import com.marte5.modello.Vino;
import com.marte5.modello.risposte.Risposta;

/**
 * @author paolosalvadori
 *
 */
public class RispostaGetAzienda extends Risposta {
	
	private Azienda azienda;
	private List<Evento> eventiAzienda;
	private List<Vino> viniAzienda;
	
	/**
	 * @return the azienza
	 */
	public Azienda getAzienda() {
		return azienda;
	}
	/**
	 * @param azienza the azienza to set
	 */
	public void setAzienda(Azienda azienda) {
		this.azienda = azienda;
	}
	/**
	 * @return the eventiAzienda
	 */
	public List<Evento> getEventiAzienda() {
		return eventiAzienda;
	}
	/**
	 * @param eventiAzienda the eventiAzienda to set
	 */
	public void setEventiAzienda(List<Evento> eventiAzienda) {
		this.eventiAzienda = eventiAzienda;
	}
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
	
	
}
