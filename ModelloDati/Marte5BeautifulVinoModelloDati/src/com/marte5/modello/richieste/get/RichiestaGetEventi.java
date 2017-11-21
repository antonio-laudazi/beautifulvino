/**
 * 
 */
package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

/**
 * @author paolosalvadori
 *
 */
public class RichiestaGetEventi extends Richiesta {
	
	private long idUtente;
	private int idProvincia; //puo' valere -1 oppure un codice. Se vale -1 rilascio il filtro sulla provincia
	private int numEventiVisualizzati;
	private long idUltimoEvento;
	private long dataUltimoEvento;
	
	/**
	 * @return the idUtente
	 */
	public long getIdUtente() {
		return idUtente;
	}
	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	/**
	 * @return the idProvincia
	 */
	public int getIdProvincia() {
		return idProvincia;
	}
	/**
	 * @param idProvincia the idProvincia to set
	 */
	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}
	/**
	 * @return the numEventiVisualizzati
	 */
	public int getNumEventiVisualizzati() {
		return numEventiVisualizzati;
	}
	/**
	 * @param numEventiVisualizzati the numEventiVisualizzati to set
	 */
	public void setNumEventiVisualizzati(int numEventiVisualizzati) {
		this.numEventiVisualizzati = numEventiVisualizzati;
	}
	/**
	 * @return the idUltimoEvento
	 */
	public long getIdUltimoEvento() {
		return idUltimoEvento;
	}
	/**
	 * @param idUltimoEvento the idUltimoEvento to set
	 */
	public void setIdUltimoEvento(long idUltimoEvento) {
		this.idUltimoEvento = idUltimoEvento;
	}
	/**
	 * @return the dataUltimoEvento
	 */
	public long getDataUltimoEvento() {
		return dataUltimoEvento;
	}
	/**
	 * @param dataUltimoEvento the dataUltimoEvento to set
	 */
	public void setDataUltimoEvento(long dataUltimoEvento) {
		this.dataUltimoEvento = dataUltimoEvento;
	}
	
	
}
