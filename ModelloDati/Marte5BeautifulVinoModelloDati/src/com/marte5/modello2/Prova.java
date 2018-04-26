package com.marte5.modello2;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="BVino_Prova")
public class Prova {
	private int id;
	private String artista;
	private String titolo;
	private String casa;
	private String part;
	
	/**
	 * @return the idAzienda
	 */
	@DynamoDBHashKey(attributeName="id")
	public int getId() {
		return id;
	}
	/**
	 * @param idAzienda the idAzienda to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the zonaAzienda
	 */
	@DynamoDBAttribute(attributeName="artista")
	public String getArtista() {
		return artista;
	}
	/**
	 * @param zonaAzienda the zonaAzienda to set
	 */
	public void setArtitsa(String artista) {
		this.artista = artista;
	}
	
	/**
	 * @return the zonaAzienda
	 */
	@DynamoDBAttribute(attributeName="titolo")
	public String getTitolo() {
		return titolo;
	}
	/**
	 * @param zonaAzienda the zonaAzienda to set
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	/**
	 * @return the zonaAzienda
	 */
	@DynamoDBAttribute(attributeName="casa")
	public String getCasa() {
		return casa;
	}
	/**
	 * @param zonaAzienda the zonaAzienda to set
	 */
	public void setCasa(String casa) {
		this.casa = casa;
	}
	
	/**
	 * @return the zonaAzienda
	 */
	@DynamoDBAttribute(attributeName="part")
	public String getPart() {
		return part;
	}
	/**
	 * @param zonaAzienda the zonaAzienda to set
	 */
	public void setPart(String part) {
		this.part = part;
	}
}
