package com.marte5.modello2;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/***
 * 
 * @author paolosalvadori
 *
 */
@DynamoDBTable(tableName="BVino_Provincia")
public class Provincia {
	
	private String idProvincia;
	private String siglaProvincia;
	private String nomeProvincia;
	
	/**
	 * @return the idProvincia
	 */
	@DynamoDBHashKey(attributeName="idProvincia")
	public String getIdProvincia() {
		return idProvincia;
	}
	/**
	 * @param idProvincia the idProvincia to set
	 */
	public void setIdProvincia(String idProvincia) {
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
