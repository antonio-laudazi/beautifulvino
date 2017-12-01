package com.marte5.modello.richieste.put;

import com.marte5.modello.Badge;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaPutBadge extends Richiesta {

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
