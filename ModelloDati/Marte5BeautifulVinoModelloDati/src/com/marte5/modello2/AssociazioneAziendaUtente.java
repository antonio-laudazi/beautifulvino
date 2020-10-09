package com.marte5.modello2;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="BVino_AssociazioneAziendaUtente")
public class AssociazioneAziendaUtente {
	
	private String Id;
	private String IdAzienda;
	private String IdUtente;
	
	//@DynamoDBAttribute(attributeName="")
	
	/**
	 * @return the idAzienda
	 */
	@DynamoDBHashKey(attributeName="Id")
	public String getId() {
		return Id;
	}
	
	/**
	 * @return the IdAzienda
	 */
	@DynamoDBAttribute(attributeName="IdAzienda")
	public String getIdAzienda() {
		return IdAzienda;
	}
	/**
	 * @param IdAzienda the IdAzienda to set
	 */
	public void setIdAzienda(String IdAzienda) {
		this.IdAzienda = IdAzienda;
	}
	
	/**
	 * @return the IdUtente
	 */
	@DynamoDBAttribute(attributeName="IdUtente")
	public String getIdUtente() {
		return IdUtente;
	}
	/**
	 * @param IdAzienda the IdAzienda to set
	 */
	public void setIdUtente(String IdUtente) {
		this.IdUtente = IdUtente;
	}
}
