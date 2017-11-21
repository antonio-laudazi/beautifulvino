/**
 * 
 */
package com.marte5.modello;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author paolosalvadori
 *
 */
@DynamoDBTable(tableName="BV_Utente")
public class Utente {
	
	private long idUtente;
	private String nomeUtente;
	private String cognomeUtente;
	private int creditiUtente;
	private int esperienzaUtente;
	private String livelloUtente;
	private String biografiaUtente;
	private String urlFotoUtente;
	private int numTotEventi;
	private int numTotAziende;
	private int numTotBadge;
	private String condivisioneBadge;
	private String condivisioneEventi;
	private String condivisioneVini;
	private List<Evento> eventiUtente;
	private List<Azienda> aziendeUtente;
	private List<Badge> badgeUtente;
	private List<EventoUtente> eventiUtenteInt;
	private List<AziendaUtente> aziendeUtenteInt;
	private List<VinoUtente> viniUtenteInt;
	private List<BadgeUtente> badgeUtenteInt;
	
	/**
	 * @return the idUtente
	 */
	@DynamoDBHashKey(attributeName="idUtente")
	public long getIdUtente() {
		return idUtente;
	}
	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	/**
	 * @return the nomeUtente
	 */
	@DynamoDBAttribute(attributeName="")
	public String getNomeUtente() {
		return nomeUtente;
	}
	/**
	 * @param nomeUtente the nomeUtente to set
	 */
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	/**
	 * @return the cognomeUtente
	 */
	@DynamoDBAttribute(attributeName="")
	public String getCognomeUtente() {
		return cognomeUtente;
	}
	/**
	 * @param cognomeUtente the cognomeUtente to set
	 */
	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}
	/**
	 * @return the creditiUtente
	 */
	@DynamoDBAttribute(attributeName="")
	public int getCreditiUtente() {
		return creditiUtente;
	}
	/**
	 * @param creditiUtente the creditiUtente to set
	 */
	public void setCreditiUtente(int creditiUtente) {
		this.creditiUtente = creditiUtente;
	}
	/**
	 * @return the esperienzaUtente
	 */
	@DynamoDBAttribute(attributeName="")
	public int getEsperienzaUtente() {
		return esperienzaUtente;
	}
	/**
	 * @param esperienzaUtente the esperienzaUtente to set
	 */
	public void setEsperienzaUtente(int esperienzaUtente) {
		this.esperienzaUtente = esperienzaUtente;
	}
	/**
	 * @return the livelloUtente
	 */
	@DynamoDBAttribute(attributeName="")
	public String getLivelloUtente() {
		return livelloUtente;
	}
	/**
	 * @param livelloUtente the livelloUtente to set
	 */
	public void setLivelloUtente(String livelloUtente) {
		this.livelloUtente = livelloUtente;
	}
	/**
	 * @return the biografiaUtente
	 */
	@DynamoDBAttribute(attributeName="")
	public String getBiografiaUtente() {
		return biografiaUtente;
	}
	/**
	 * @param biografiaUtente the biografiaUtente to set
	 */
	public void setBiografiaUtente(String biografiaUtente) {
		this.biografiaUtente = biografiaUtente;
	}
	/**
	 * @return the urlFotoUtente
	 */
	@DynamoDBAttribute(attributeName="")
	public String getUrlFotoUtente() {
		return urlFotoUtente;
	}
	/**
	 * @param urlFotoUtente the urlFotoUtente to set
	 */
	public void setUrlFotoUtente(String urlFotoUtente) {
		this.urlFotoUtente = urlFotoUtente;
	}
	/**
	 * @return the eventiUtente
	 */
	@DynamoDBIgnore
	public List<Evento> getEventiUtente() {
		return eventiUtente;
	}
	/**
	 * @param eventiUtente the eventiUtente to set
	 */
	public void setEventiUtente(List<Evento> eventiUtente) {
		this.eventiUtente = eventiUtente;
	}
	/**
	 * @return the aziendeUtente
	 */
	@DynamoDBIgnore
	public List<Azienda> getAziendeUtente() {
		return aziendeUtente;
	}
	/**
	 * @param aziendeUtente the aziendeUtente to set
	 */
	public void setAziendeUtente(List<Azienda> aziendeUtente) {
		this.aziendeUtente = aziendeUtente;
	}
	/**
	 * @return the badgeUtente
	 */
	@DynamoDBIgnore
	public List<Badge> getBadgeUtente() {
		return badgeUtente;
	}
	/**
	 * @param badgeUtente the badgeUtente to set
	 */
	public void setBadgeUtente(List<Badge> badgeUtente) {
		this.badgeUtente = badgeUtente;
	}
	/**
	 * @return the numTotaleBadge
	 */
	@DynamoDBAttribute(attributeName="")
	public int getNumTotBadge() {
		return numTotBadge;
	}
	/**
	 * @param numTotaleBadge the numTotaleBadge to set
	 */
	public void setNumTotBadge(int numTotaleBadge) {
		this.numTotBadge = numTotaleBadge;
	}
	/**
	 * @return the numTotaleEventi
	 */
	@DynamoDBAttribute(attributeName="")
	public int getNumTotEventi() {
		return numTotEventi;
	}
	/**
	 * @param numTotaleEventi the numTotaleEventi to set
	 */
	public void setNumTotEventi(int numTotaleEventi) {
		this.numTotEventi = numTotaleEventi;
	}
	/**
	 * @return the numTotaleAziende
	 */
	@DynamoDBAttribute(attributeName="")
	public int getNumTotAziende() {
		return numTotAziende;
	}
	/**
	 * @param numTotaleAziende the numTotaleAziende to set
	 */
	public void setNumTotAziende(int numTotaleAziende) {
		this.numTotAziende = numTotaleAziende;
	}
	/**
	 * @return the condivisioneBadge
	 */
	@DynamoDBAttribute(attributeName="")
	public String getCondivisioneBadge() {
		return condivisioneBadge;
	}
	/**
	 * @param condivisioneBadge the condivisioneBadge to set
	 */
	public void setCondivisioneBadge(String condivisioneBadge) {
		this.condivisioneBadge = condivisioneBadge;
	}
	/**
	 * @return the condivisioneEventi
	 */
	@DynamoDBAttribute(attributeName="")
	public String getCondivisioneEventi() {
		return condivisioneEventi;
	}
	/**
	 * @param condivisioneEventi the condivisioneEventi to set
	 */
	public void setCondivisioneEventi(String condivisioneEventi) {
		this.condivisioneEventi = condivisioneEventi;
	}
	/**
	 * @return the condivisioneVini
	 */
	@DynamoDBAttribute(attributeName="")
	public String getCondivisioneVini() {
		return condivisioneVini;
	}
	/**
	 * @param condivisioneVini the condivisioneVini to set
	 */
	public void setCondivisioneVini(String condivisioneVini) {
		this.condivisioneVini = condivisioneVini;
	}
	
	@DynamoDBAttribute(attributeName="eventiUtente")
	public List<EventoUtente> getEventiUtenteInt() {
		return eventiUtenteInt;
	}
	public void setEventiUtenteInt(List<EventoUtente> eventiUtenteInt) {
		this.eventiUtenteInt = eventiUtenteInt;
	}
	@DynamoDBAttribute(attributeName="aziendeUtente")
	public List<AziendaUtente> getAziendeUtenteInt() {
		return aziendeUtenteInt;
	}
	public void setAziendeUtenteInt(List<AziendaUtente> aziendeUtenteInt) {
		this.aziendeUtenteInt = aziendeUtenteInt;
	}
	@DynamoDBAttribute(attributeName="badgeUtente")
	public List<BadgeUtente> getBadgeUtenteInt() {
		return badgeUtenteInt;
	}
	public void setBadgeUtenteInt(List<BadgeUtente> badgeUtenteInt) {
		this.badgeUtenteInt = badgeUtenteInt;
	}
	@DynamoDBAttribute(attributeName="viniUtente")
	public List<VinoUtente> getViniUtenteInt() {
		return viniUtenteInt;
	}
	public void setViniUtenteInt(List<VinoUtente> viniUtenteInt) {
		this.viniUtenteInt = viniUtenteInt;
	}
	
	@DynamoDBDocument
	public static class EventoUtente{
		private long idEvento;

		@DynamoDBAttribute(attributeName="idEvento")
		public long getIdEvento() {
			return idEvento;
		}

		public void setIdEvento(long idEvento) {
			this.idEvento = idEvento;
		}
	}
	
	@DynamoDBDocument
	public static class AziendaUtente{
		private long idAzienda;

		@DynamoDBAttribute(attributeName="idAzienda")
		public long getIdAzienda() {
			return idAzienda;
		}

		public void setIdAzienda(long idAzienda) {
			this.idAzienda = idAzienda;
		}
	}
	
	@DynamoDBDocument
	public static class BadgeUtente{
		private long idBadge;

		@DynamoDBAttribute(attributeName="idBadge")
		public long getIdBadge() {
			return idBadge;
		}

		public void setIdBadge(long idBadge) {
			this.idBadge = idBadge;
		}
	}
	
	@DynamoDBDocument
	public static class VinoUtente{
		private long idVino;

		@DynamoDBAttribute(attributeName="idVino")
		public long getIdVino() {
			return idVino;
		}

		public void setIdVino(long idVino) {
			this.idVino = idVino;
		}
	}

	

}
