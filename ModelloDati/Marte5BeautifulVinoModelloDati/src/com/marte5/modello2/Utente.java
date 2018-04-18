/**
 * 
 */
package com.marte5.modello2;

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
@DynamoDBTable(tableName="BVino_Utente")
public class Utente {
	
	private String idUtente;
	private String nomeUtente;
	private String cognomeUtente;
	private int creditiUtente;
	private int esperienzaUtente;
	private String livelloUtente;
	private String biografiaUtente;
	private String cittaUtente;
	private String usernameUtente;
	private String urlFotoUtente;
	private String emailUtente;
	private String professioneUtente;
	private int numTotEventi;
	private int numTotAziende;
	private int numTotBadge;
	private String condivisioneBadge;
	private String condivisioneEventi;
	private String condivisioneVini;
	private String statoUtente;
	private List<Evento> eventiUtente;
	private List<Azienda> aziendeUtente;
	private List<Badge> badgeUtente;
	private List<Utente> utentiUtente;
	private List<EventoUtente> eventiUtenteInt;
	private List<AziendaUtente> aziendeUtenteInt;
	private List<VinoUtente> viniUtenteInt;
	private List<BadgeUtente> badgeUtenteInt;
	private List<UtenteUtente> utentiUtenteInt;
	
	/**
	 * @return the idUtente
	 */
	@DynamoDBHashKey(attributeName="idUtente")
	public String getIdUtente() {
		return idUtente;
	}
	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	/**
	 * @return the nomeUtente
	 */
	@DynamoDBAttribute(attributeName="nomeUtente")
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
	@DynamoDBAttribute(attributeName="cognomeUtente")
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
	@DynamoDBAttribute(attributeName="creditiUtente")
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
	@DynamoDBAttribute(attributeName="esperienzaUtente")
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
	@DynamoDBAttribute(attributeName="livelloUtente")
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
	@DynamoDBAttribute(attributeName="biografiaUtente")
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
	 * @return the cittaUtente
	 */
	@DynamoDBAttribute(attributeName="cittaUtente")
	public String getCittaUtente() {
		return cittaUtente;
	}
	/**
	 * @param cittaUtente the cittaUtente to set
	 */
	public void setCittaUtente(String cittaUtente) {
		this.cittaUtente = cittaUtente;
	}
	/**
	 * @return the usernameUtente
	 */
	@DynamoDBAttribute(attributeName="usernameUtente")
	public String getUsernameUtente() {
		return usernameUtente;
	}
	/**
	 * @param usernameUtente the usernameUtente to set
	 */
	public void setUsernameUtente(String usernameUtente) {
		this.usernameUtente = usernameUtente;
	}
	/**
	 * @return the urlFotoUtente
	 */
	@DynamoDBAttribute(attributeName="urlFotoUtente")
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
	 * @return the emailUtente
	 */
	@DynamoDBAttribute(attributeName="emailUtente")
	public String getEmailUtente() {
		return emailUtente;
	}
	/**
	 * @param emailUtente the emailUtente to set
	 */
	public void setEmailUtente(String emailUtente) {
		this.emailUtente = emailUtente;
	}

	/**
	 * @return the professioneUtente
	 */
	@DynamoDBAttribute(attributeName="professioneUtente")
	public String getProfessioneUtente() {
		return professioneUtente;
	}
	/**
	 * @param professioneUtente the professioneUtente to set
	 */
	public void setProfessioneUtente(String professioneUtente) {
		this.professioneUtente = professioneUtente;
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
	@DynamoDBIgnore
	public List<Utente> getUtentiUtente() {
		return utentiUtente;
	}
	/**
	 * @param badgeUtente the badgeUtente to set
	 */
	public void setUtentiUtente(List<Utente> utentiUtente) {
		this.utentiUtente = utentiUtente;
	}
	/**
	 * @return the numTotaleBadge
	 */
	@DynamoDBAttribute(attributeName="numTotBadge")
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
	@DynamoDBAttribute(attributeName="numTotEventi")
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
	@DynamoDBAttribute(attributeName="numTotAziende")
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
	@DynamoDBAttribute(attributeName="condivisioneBadge")
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
	@DynamoDBAttribute(attributeName="condivisioneEventi")
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
	@DynamoDBAttribute(attributeName="condivisioneVini")
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
	
	@DynamoDBAttribute(attributeName="utentiUtente")
	public List<UtenteUtente> getUtentiUtenteInt() {
		return utentiUtenteInt;
	}
	public void setUtentiUtenteInt(List<UtenteUtente> utentiUtente) {
		this.utentiUtenteInt = utentiUtente;
	}
	
	@DynamoDBDocument
	public static class EventoUtente{
		private String idEvento;
		private long dataEvento;
		private String statoEvento;

		@DynamoDBAttribute(attributeName="idEvento")
		public String getIdEvento() {
			return idEvento;
		}

		public void setIdEvento(String idEvento) {
			this.idEvento = idEvento;
		}

		/**
		 * @return the statoUtente
		 */
		@DynamoDBAttribute(attributeName="statoEvento")
		public String getStatoEvento() {
			return statoEvento;
		}

		/**
		 * @param statoUtente the statoUtente to set
		 */
		public void setStatoEvento(String statoUtente) {
			this.statoEvento = statoUtente;
		}

		/**
		 * @return the dataEvento
		 */
		@DynamoDBAttribute(attributeName="dataEvento")
		public long getDataEvento() {
			return dataEvento;
		}

		/**
		 * @param dataEvento the dataEvento to set
		 */
		public void setDataEvento(long dataEvento) {
			this.dataEvento = dataEvento;
		}
	}
	
	@DynamoDBDocument
	public static class AziendaUtente{
		private String idAzienda;
		private String nomeAzienda;

		@DynamoDBAttribute(attributeName="idAzienda")
		public String getIdAzienda() {
			return idAzienda;
		}

		public void setIdAzienda(String idAzienda) {
			this.idAzienda = idAzienda;
		}

		/**
		 * @return the nomeAzienda
		 */
		@DynamoDBAttribute(attributeName="nomeAzienda")
		public String getNomeAzienda() {
			return nomeAzienda;
		}

		/**
		 * @param nomeAzienda the nomeAzienda to set
		 */
		public void setNomeAzienda(String nomeAzienda) {
			this.nomeAzienda = nomeAzienda;
		}
	}
	
	@DynamoDBDocument
	public static class BadgeUtente{
		private String idBadge;
		private String tuoBadge;

		@DynamoDBAttribute(attributeName="idBadge")
		public String getIdBadge() {
			return idBadge;
		}

		public void setIdBadge(String idBadge) {
			this.idBadge = idBadge;
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
	
	@DynamoDBDocument
	public static class VinoUtente{
		private String idVino;
		private String nomeVino;
		private String statoVino;
		private String inBreveVino;
		private String urlLogoVino;
		private String uvaggioVino;
		
		@DynamoDBAttribute(attributeName="idVino")
		public String getIdVino() {
			return idVino;
		}

		public void setIdVino(String idVino) {
			this.idVino = idVino;
		}

		/**
		 * @return the nomeVino
		 */
		@DynamoDBAttribute(attributeName="nomeVino")
		public String getNomeVino() {
			return nomeVino;
		}

		/**
		 * @param nomeVino the nomeVino to set
		 */
		public void setNomeVino(String nomeVino) {
			this.nomeVino = nomeVino;
		}

		/**
		 * @return the statoVino
		 */
		@DynamoDBAttribute(attributeName="statoVino")
		public String getStatoVino() {
			return statoVino;
		}

		/**
		 * @param statoVino the statoVino to set
		 */
		public void setStatoVino(String statoVino) {
			this.statoVino = statoVino;
		}

		/**
		 * @return the inBreveVino
		 */
		@DynamoDBAttribute(attributeName="inBreveVino")
		public String getInBreveVino() {
			return inBreveVino;
		}

		/**
		 * @param inBreveVino the inBreveVino to set
		 */
		public void setInBreveVino(String inBreveVino) {
			this.inBreveVino = inBreveVino;
		}

		/**
		 * @return the urlLogoVino
		 */
		@DynamoDBAttribute(attributeName="urlLogoVino")
		public String getUrlLogoVino() {
			return urlLogoVino;
		}

		/**
		 * @param urlLogoVino the urlLogoVino to set
		 */
		public void setUrlLogoVino(String urlLogoVino) {
			this.urlLogoVino = urlLogoVino;
		}

		/**
		 * @return the uvaggioVino
		 */
		@DynamoDBAttribute(attributeName="uvaggioVino")
		public String getUvaggioVino() {
			return uvaggioVino;
		}

		/**
		 * @param uvaggioVino the uvaggioVino to set
		 */
		public void setUvaggioVino(String uvaggioVino) {
			this.uvaggioVino = uvaggioVino;
		}
	}

	@DynamoDBDocument
	public static class UtenteUtente{
		private String idUtente;

		@DynamoDBAttribute(attributeName="idUtente")
		public String getIdUtente() {
			return idUtente;
		}

		public void setIdUtente(String idUtente) {
			this.idUtente = idUtente;
		}
	}

	/**
	 * @return the statoUtente
	 */
	@DynamoDBAttribute(attributeName="statoUtente")
	public String getStatoUtente() {
		return statoUtente;
	}
	/**
	 * @param statoUtente the statoUtente to set
	 */
	public void setStatoUtente(String statoUtente) {
		this.statoUtente = statoUtente;
	}
}
