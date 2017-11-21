/**
 * 
 */
package com.marte5.modello.richieste.get;

import com.marte5.modello.richieste.Richiesta;

/**
 * @author paolosalvadori
 *
 */
public class RichiestaLogin extends Richiesta {

	private String emailUtente;
	private String passwordUtente;
	private String latitudineUtente;
	private String longitudineUtente;
	
	/**
	 * @return the emailUtente
	 */
	public String getEmailUtente() {
		return emailUtente;
	}
	/**
	 * @param emailUtente the emailUtente to set
	 */
	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}
	/**
	 * @return the passwordUtente
	 */
	public String getPasswordUtente() {
		return passwordUtente;
	}
	/**
	 * @param passwordUtente the passwordUtente to set
	 */
	public void setPasswordUtente(String passwordUtente) {
		this.passwordUtente = passwordUtente;
	}
	/**
	 * @return the latitudineUtente
	 */
	public String getLatitudineUtente() {
		return latitudineUtente;
	}
	/**
	 * @param latitudineUtente the latitudineUtente to set
	 */
	public void setLatitudineUtente(String latitudineUtente) {
		this.latitudineUtente = latitudineUtente;
	}
	/**
	 * @return the longitudineUtente
	 */
	public String getLongitudineUtente() {
		return longitudineUtente;
	}
	/**
	 * @param longitudineUtente the longitudineUtente to set
	 */
	public void setLongitudineUtente(String longitudineUtente) {
		this.longitudineUtente = longitudineUtente;
	}
	
	
	
}
