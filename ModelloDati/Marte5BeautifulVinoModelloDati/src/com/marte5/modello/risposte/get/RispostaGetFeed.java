package com.marte5.modello.risposte.get;

import java.util.List;

import com.marte5.modello.Feed;
import com.marte5.modello.risposte.Risposta;

public class RispostaGetFeed extends Risposta {
	private int numTotFeed;
	private List<Feed> feed;
	
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
	
	
}
