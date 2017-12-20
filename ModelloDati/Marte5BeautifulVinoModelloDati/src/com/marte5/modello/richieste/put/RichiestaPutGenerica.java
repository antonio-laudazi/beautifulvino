package com.marte5.modello.richieste.put;

import com.marte5.modello.Azienda;
import com.marte5.modello.Badge;
import com.marte5.modello.Evento;
import com.marte5.modello.Feed;
import com.marte5.modello.Provincia;
import com.marte5.modello.Utente;
import com.marte5.modello.Vino;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaPutGenerica extends Richiesta {
	
	
	private Provincia provincia;
	private Azienda azienda;
	private Badge badge;
	private Evento evento;
	private Feed feed;
	private String base64Image;
	private String tipoEntita;
	private String filename;
	private Utente utente;
	private Vino vino;
	
	
	/**
	 * @return the provincia
	 */
	public Provincia getProvincia() {
		return provincia;
	}
	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	/**
	 * @return the azienda
	 */
	public Azienda getAzienda() {
		return azienda;
	}
	/**
	 * @param azienda the azienda to set
	 */
	public void setAzienda(Azienda azienda) {
		this.azienda = azienda;
	}
	/**
	 * @return the badge
	 */
	public Badge getBadge() {
		return badge;
	}
	/**
	 * @param badge the badge to set
	 */
	public void setBadge(Badge badge) {
		this.badge = badge;
	}
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
	/**
	 * @return the feed
	 */
	public Feed getFeed() {
		return feed;
	}
	/**
	 * @param feed the feed to set
	 */
	public void setFeed(Feed feed) {
		this.feed = feed;
	}
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
	/**
	 * @return the utente
	 */
	public Utente getUtente() {
		return utente;
	}
	/**
	 * @param utente the utente to set
	 */
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
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
