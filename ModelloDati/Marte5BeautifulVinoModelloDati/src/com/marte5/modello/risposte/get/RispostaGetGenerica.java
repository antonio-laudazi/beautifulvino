package com.marte5.modello.risposte.get;

import java.util.List;

import com.marte5.modello2.AssociazioneAziendaUtente;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Badge;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Feed;
import com.marte5.modello2.Provincia;
import com.marte5.modello.Token;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Vino;
import com.marte5.modello2.ProfiloAzienda;
import com.marte5.modello.risposte.Risposta;

public class RispostaGetGenerica extends Risposta {

	private String stato;
	private Azienda azienda;
	private List<Evento> eventiAzienda;
	private List<Vino> viniAzienda;
	
	private List<Azienda> aziende;
	
	private Badge badge;
	
	private int numTotEventi;
	private List<Evento> eventi;
	
	private Evento evento;
	
	private int numTotFeed;
	private List<Feed> feed;
	
	List<Provincia> province;
	
	private Token newToken;
	
	private Utente utente;
	
	List<Utente> utenti;
	
	List<Vino> vini;
	
	List<Badge> badges;
	
	List<AssociazioneAziendaUtente> associazioni;
	
	private Vino vino;
	
	private Token token;
	
	private int utentePresente;
	
	private ProfiloAzienda profiloAzienda;

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
	 * @return the stato
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param stato the stato to set
	 */
	public void setStato(String stato) {
		this.stato = stato;
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

	/**
	 * @return the aziende
	 */
	public List<Azienda> getAziende() {
		return aziende;
	}

	/**
	 * @param aziende the aziende to set
	 */
	public void setAziende(List<Azienda> aziende) {
		this.aziende = aziende;
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
	 * @return the numTotEventi
	 */
	public int getNumTotEventi() {
		return numTotEventi;
	}

	/**
	 * @param numTotEventi the numTotEventi to set
	 */
	public void setNumTotEventi(int numTotEventi) {
		this.numTotEventi = numTotEventi;
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
	 * @return the numTotFeed
	 */
	public int getNumTotFeed() {
		return numTotFeed;
	}

	/**
	 * @param numTotFeed the numTotFeed to set
	 */
	public void setNumTotFeed(int numTotFeed) {
		this.numTotFeed = numTotFeed;
	}

	/**
	 * @return the feed
	 */
	public List<Feed> getFeed() {
		return feed;
	}

	/**
	 * @param feed the feed to set
	 */
	public void setFeed(List<Feed> feed) {
		this.feed = feed;
	}

	/**
	 * @return the province
	 */
	public List<Provincia> getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(List<Provincia> province) {
		this.province = province;
	}

	/**
	 * @return the newToken
	 */
	public Token getNewToken() {
		return newToken;
	}

	/**
	 * @param newToken the newToken to set
	 */
	public void setNewToken(Token newToken) {
		this.newToken = newToken;
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
	 * @return the utenti
	 */
	public List<Utente> getUtenti() {
		return utenti;
	}

	/**
	 * @param utenti the utenti to set
	 */
	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}

	/**
	 * @return the vini
	 */
	public List<Vino> getVini() {
		return vini;
	}

	/**
	 * @param vini the vini to set
	 */
	public void setVini(List<Vino> vini) {
		this.vini = vini;
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

	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
	}

	/**
	 * @return the associazioni
	 */
	public List<AssociazioneAziendaUtente> getAssociazioniAziendeUtenti() {
		return associazioni;
	}

	/**
	 * @param associazioni the associazioni to set
	 */
	public void setAssociazioniAziendeUtenti(List<AssociazioneAziendaUtente> associazioni) {
		this.associazioni = associazioni;
	}
	
	/**
	 * @return the badges
	 */
	public List<Badge> getBadges() {
		return badges;
	}

	/**
	 * @param badges the badges to set
	 */
	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}

	/**
	 * @return the utentePresente
	 */
	public int getUtentePresente() {
		return utentePresente;
	}

	/**
	 * @param utentePresente the utentePresente to set
	 */
	public void setUtentePresente(int utentePresente) {
		this.utentePresente = utentePresente;
	}

	/**
	 * @return the profiloAzienda
	 */
	public ProfiloAzienda getProfiloAzienda() {
		return profiloAzienda;
	}

	/**
	 * @param profiloAzienda the profiloAzienda to set
	 */
	public void setProfiloAzienda(ProfiloAzienda profiloAzienda) {
		this.profiloAzienda = profiloAzienda;
	}
}
