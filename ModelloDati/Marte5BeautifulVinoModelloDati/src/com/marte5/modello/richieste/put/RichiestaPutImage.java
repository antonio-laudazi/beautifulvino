package com.marte5.modello.richieste.put;

import com.marte5.modello.richieste.Richiesta;

public class RichiestaPutImage extends Richiesta {
	
	private String base64Image;
	private String tipoEntita;
	private String filename;
	
	/**
	 * @return the base64Image
	 */
	public String getBase64Image() {
		return base64Image;
	}
	/**
	 * @param base64Image the base64Image to set
	 */
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	/**
	 * @return the tipoEntita
	 */
	public String getTipoEntita() {
		return tipoEntita;
	}
	/**
	 * @param tipoEntita the tipoEntita to set
	 */
	public void setTipoEntita(String tipoEntita) {
		this.tipoEntita = tipoEntita;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
