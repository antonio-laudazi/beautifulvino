package com.marte5.modello2;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="BVino_Credenziali")
public class Credenziali {
	private int id ;
	private String key;
	private String secretKey;
	
	/**
	 * @return the 
	 */
	@DynamoDBHashKey(attributeName="id")
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the key
	 */
	@DynamoDBAttribute(attributeName="key")
	public String getKey() {
		return key;
	}
	/**
	 * @param Key the Key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the secretkey
	 */
	@DynamoDBAttribute(attributeName="secretKey")
	public String getSecretKey() {
		return secretKey;
	}
	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
