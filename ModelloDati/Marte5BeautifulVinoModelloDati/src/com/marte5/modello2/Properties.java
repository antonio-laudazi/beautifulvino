/**
 * 
 */
package com.marte5.modello2;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author paolosalvadori
 *
 */
@DynamoDBTable(tableName="BVino_Properties")
public class Properties {
	
	public static final String PROPERTY_KEY_SENDMAIL = "SENDMAIL";//"S", "N"

	private String propertyKey;
	private String propertyValue;
	
	/**
	 * @return the propertyKey
	 */
	@DynamoDBHashKey(attributeName="propertyKey")
	public String getPropertyKey() {
		return propertyKey;
	}
	/**
	 * @param propertyKey the propertyKey to set
	 */
	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}
	/**
	 * @return the propertyValue
	 */
	@DynamoDBAttribute(attributeName="propertyValue")
	public String getPropertyValue() {
		return propertyValue;
	}
	/**
	 * @param propertyValue the propertyValue to set
	 */
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	
}
