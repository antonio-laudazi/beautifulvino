package com.marte5.modello.richieste.put;

import com.marte5.modello.Evento;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaPutEvento extends Richiesta {

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
