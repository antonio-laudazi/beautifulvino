package com.marte5.modello.richieste.notifica;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaNotificaGenerica extends Richiesta{
	private String messaggio;
	
	/**
	 * @param messaggio the messaggio to set
	 */
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	/**
	 * @return the Messaggio
	 */
	public String getMessaggio() {
		return messaggio;
	}
}
