package com.marte5.modello.risposte.get;

import com.marte5.modello.Badge;
import com.marte5.modello.risposte.Risposta;

public class RispostaGetBadge extends Risposta {
	private Badge badge;

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
}
