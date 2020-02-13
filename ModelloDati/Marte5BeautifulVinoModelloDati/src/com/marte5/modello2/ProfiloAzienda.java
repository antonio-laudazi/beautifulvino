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
@DynamoDBTable(tableName="BVino_ProfiloAzienda")
public class ProfiloAzienda {

	private String IdProfiloAzienda;
	private String logo;
	private String colorePrimario;
	private String coloreSecondario;
	private String splashScreen;
	private String paypalCode;
	private String idAzienda;
	
	
	//@DynamoDBAttribute(attributeName="")
	
	/**
	 * @return the idAzienda
	 */
	@DynamoDBHashKey(attributeName="IdProfiloAzienda")
	public String getIdProfiloAzienda() {
		return IdProfiloAzienda;
	}
	/**
	 * @param idAzienda the idAzienda to set
	 */
	public void setIdProfiloAzienda(String IdProfiloAzienda) {
		this.IdProfiloAzienda = IdProfiloAzienda;
	}
	
	/**
	 * @return the logo
	 */
	@DynamoDBAttribute(attributeName="logo")
	public String getLogo() {
		return logo;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * @return the colorePrimario
	 */
	@DynamoDBAttribute(attributeName="colorePrimario")
	public String getColorePrimario() {
		return colorePrimario;
	}
	/**
	 * @param colorePrimario the colorePrimario to set
	 */
	public void setColorePrimario(String colorePrimario) {
		this.colorePrimario = colorePrimario;
	}
	/**
	 * @return the coloreSecondario
	 */
	@DynamoDBAttribute(attributeName="coloreSecondario")
	public String getColoreSecondario() {
		return coloreSecondario;
	}
	/**
	 * @param coloreSecondario the coloreSecondario to set
	 */
	public void setColoreSecondario(String coloreSecondario) {
		this.coloreSecondario = coloreSecondario;
	}
	/**
	 * @return the splashScreen
	 */
	@DynamoDBAttribute(attributeName="splashScreen")
	public String getSplashScreen() {
		return splashScreen;
	}
	/**
	 * @param splashScreen the splashScreen to set
	 */
	public void setSplashScreen(String splashScreen) {
		this.splashScreen = splashScreen;
	}
	/**
	 * @return the paypalCode
	 */
	@DynamoDBAttribute(attributeName="paypalCode")
	public String getPaypalCode() {
		return paypalCode;
	}
	/**
	 * @param paypalCode the paypalCode to set
	 */
	public void setPaypalCode(String paypalCode) {
		this.paypalCode = paypalCode;
	}
	/**
	 * @return the azienda
	 */
	public String getIdAzienda() {
		return idAzienda;
	}
	/**
	 * @param azienda the azienda to set
	 */
	public void setIdAzienda(String idAzienda) {
		this.idAzienda = idAzienda;
	}

	
}
