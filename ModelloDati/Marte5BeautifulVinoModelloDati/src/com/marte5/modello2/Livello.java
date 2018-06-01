package com.marte5.modello2;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="BVino_Livello")
public class Livello {
	private String idLivello;
	private String nomeLivello;
	private int min;
	private int max;
	
	/**
	 * @return the idLivello
	 */
	@DynamoDBHashKey(attributeName="idLivello")
	public String getIdLivello() {
		return idLivello;
	}
	/**
	 * @param idLivello the idLivello to set
	 */
	public void setIdLivello(String idLivello) {
		this.idLivello = idLivello;
	}
	/**
	 * @return the nomeLivello
	 */
	@DynamoDBAttribute(attributeName="nomeLivello")
	public String getNomeLivello() {
		return nomeLivello;
	}
	/**
	 * @param nomeLivello the nomeLivello to set
	 */
	public void setNomeLivello(String nomeLivello) {
		this.nomeLivello = nomeLivello;
	}
	/**
	 * @return the min
	 */
	@DynamoDBAttribute(attributeName="min")
	public int getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}
	/**
	 * @return the max
	 */
	@DynamoDBAttribute(attributeName="max")
	public int getMax() {
		return max;
	}
	/**
	 * @param man the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}
}
