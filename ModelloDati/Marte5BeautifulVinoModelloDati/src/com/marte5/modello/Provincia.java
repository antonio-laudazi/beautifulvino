package com.marte5.modello;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/***
 * 
 * @author paolosalvadori
 *
 */
@DynamoDBTable(tableName="BV_Provincia")
public class Provincia {
	
	private long idProvincia;
	private String siglaProvincia;
	private String nomeProvincia;
	
	/**
	 * @return the idProvincia
	 */
	@DynamoDBHashKey(attributeName="idProvincia")
	public long getIdProvincia() {
		return idProvincia;
	}
	/**
	 * @param idProvincia the idProvincia to set
	 */
	public void setIdProvincia(long idProvincia) {
		this.idProvincia = idProvincia;
	}
	/**
	 * @return the siglaProvincia
	 */
	@DynamoDBAttribute(attributeName="siglaProvincia")
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	/**
	 * @param siglaProvincia the siglaProvincia to set
	 */
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	/**
	 * @return the nomeProvincia
	 */
	@DynamoDBAttribute(attributeName="nomeProvincia")
	public String getNomeProvincia() {
		return nomeProvincia;
	}
	/**
	 * @param nomeProvincia the nomeProvincia to set
	 */
	public void setNomeProvincia(String nomeProvincia) {
		this.nomeProvincia = nomeProvincia;
	}
}
