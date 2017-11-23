package com.marte5.modello.richieste.put;

import com.marte5.modello.Feed;
import com.marte5.modello.richieste.Richiesta;

public class RichiestaPutFeed extends Richiesta {
	private Feed feed;

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
	
	
}
