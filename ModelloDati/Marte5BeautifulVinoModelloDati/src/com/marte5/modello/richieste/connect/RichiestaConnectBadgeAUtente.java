package com.marte5.modello.richieste.connect;

import java.util.List;

import com.marte5.modello2.Badge;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaConnectBadgeAUtente extends Richiesta {

	private List<Badge> badges;
	private String idUtente;
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
	 * @return the idUtente
	 */
	public String getIdUtente() {
		return idUtente;
	}
	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
}
