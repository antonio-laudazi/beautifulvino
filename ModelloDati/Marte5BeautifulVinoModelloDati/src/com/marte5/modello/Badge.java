/**
 * 
 */
package com.marte5.modello;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author paolosalvadori
 *
 */
@DynamoDBTable(tableName="BV_Badge")
public class Badge {

	private long idBadge;
	private String nomeBadge;
	private String infoBadge;	
	private String urlLogoBadge;
	private String tuoBadge;
	
	/**
	 * @return the idBadge
	 */
	@DynamoDBHashKey(attributeName="idBadge")
	public long getIdBadge() {
		return idBadge;
	}
	/**
	 * @param idBadge the idBadge to set
	 */
	public void setIdBadge(long idBadge) {
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
}
