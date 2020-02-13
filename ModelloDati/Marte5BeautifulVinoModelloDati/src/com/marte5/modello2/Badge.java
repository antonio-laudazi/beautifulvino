/**
 * 
 */
package com.marte5.modello2;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author paolosalvadori
 *
 */
@DynamoDBTable(tableName="BVino_Badge")
public class Badge {

	private String idBadge;
	private String nomeBadge;
	private String infoBadge;	
	private String urlLogoBadge;
	private String tuoBadge;//S=guadagnato, N=da guadagnare
	private long dataBadge;
	private EventoBadge eventoBadge;
	
	/**
	 * @return the idBadge
	 */
	@DynamoDBHashKey(attributeName="idBadge")
	public String getIdBadge() {
		return idBadge;
	}
	/**
	 * @param idBadge the idBadge to set
	 */
	public void setIdBadge(String idBadge) {
		this.idBadge = idBadge;
	}
	/**
	 * @return the nomeBadge
	 */
	@DynamoDBAttribute(attributeName="nomeBadge")
	public String getNomeBadge() {
		return nomeBadge;
	}
	/**
	 * @param nomeBadge the nomeBadge to set
	 */
	public void setNomeBadge(String nomeBadge) {
		this.nomeBadge = nomeBadge;
	}
	/**
	 * @return the infoBadge
	 */
	@DynamoDBAttribute(attributeName="infoBadge")
	public String getInfoBadge() {
		return infoBadge;
	}
	/**
	 * @param infoBadge the infoBadge to set
	 */
	public void setInfoBadge(String infoBadge) {
		this.infoBadge = infoBadge;
	}
	/**
	 * @return the urlLogoBadge
	 */
	@DynamoDBAttribute(attributeName="urlLogoBadge")
	public String getUrlLogoBadge() {
		return urlLogoBadge;
	}
	/**
	 * @param urlLogoBadge the urlLogoBadge to set
	 */
	public void setUrlLogoBadge(String urlLogoBadge) {
		this.urlLogoBadge = urlLogoBadge;
	}
	/**
	 * @return the tuoBadge
	 */
	@DynamoDBAttribute(attributeName="tuoBadge")
	public String getTuoBadge() {
		return tuoBadge;
	}
	/**
	 * @param tuoBadge the tuoBadge to set
	 */
	public void setTuoBadge(String tuoBadge) {
		this.tuoBadge = tuoBadge;
	}
	/**
	 * @return the dataBadge
	 */
	@DynamoDBAttribute(attributeName="dataBadge")
	public long getDataBadge() {
		return dataBadge;
	}
	/**
	 * @param tuoBadge the tuoBadge to set
	 */
	public void setDataBadge(long dataBadge) {
		this.dataBadge = dataBadge;
	}
	
	/**
	 * @return the dataBadge
	 */
	@DynamoDBAttribute(attributeName="eventoBadge")
	public EventoBadge getEventoBadge() {
		return eventoBadge;
	}
	/**
	 * @param tuoBadge the tuoBadge to set
	 */
	public void setEventoBadge(EventoBadge eventoBadge) {
		this.eventoBadge = eventoBadge;
	}
	
	
	@DynamoDBDocument
	public static class EventoBadge{
		private String idEvento;
		private long dataEvento;
		private String statoEvento;
		private boolean pubblicatoEvento;

		@DynamoDBAttribute(attributeName="idEvento")
		public String getIdEvento() {
			return idEvento;
		}

		public void setIdEvento(String idEvento) {
			this.idEvento = idEvento;
		}

		/**
		 * @return the statoUtente
		 */
		@DynamoDBAttribute(attributeName="statoEvento")
		public String getStatoEvento() {
			return statoEvento;
		}

		/**
		 * @param statoUtente the statoUtente to set
		 */
		public void setStatoEvento(String statoUtente) {
			this.statoEvento = statoUtente;
		}

		/**
		 * @return the dataEvento
		 */
		@DynamoDBAttribute(attributeName="dataEvento")
		public long getDataEvento() {
			return dataEvento;
		}

		/**
		 * @param dataEvento the dataEvento to set
		 */
		public void setDataEvento(long dataEvento) {
			this.dataEvento = dataEvento;
		}
		/**
		 * @return the pubblicato
		 */
		@DynamoDBAttribute(attributeName="pubblicatoEvento")
		public boolean getPubblicatoEvento() {
			return pubblicatoEvento;
		}

		/**
		 * @param dataEvento the pubblicato to set
		 */
		public void setPubblicatoEvento(boolean pubblicatoEvento) {
			this.pubblicatoEvento = pubblicatoEvento;
		}
	}
	
}
