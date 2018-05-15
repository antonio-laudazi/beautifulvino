/**
 * 
 */
package com.marte5.modello2;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;



/**
 * @author paolosalvadori
 *
 */
@DynamoDBTable(tableName="BVino_Evento")
public class Evento {

	private String idEvento;
	private long dataEvento;
	private String dataEventoStringa;
	private String cittaEvento;
	private String titoloEvento;
	private String temaEvento;
	private float prezzoEvento;
	private String urlFotoEvento;
	private String statoEvento;
	private String testoEvento;
	private double latitudineEvento;
	private double longitudineEvento;
	private String indirizzoEvento;
	private String telefonoEvento;
	private String emailEvento;
	private int numMaxPartecipantiEvento;
	private int numPostiDisponibiliEvento;
	private Badge badgeEvento;
	private Provincia provinciaEvento;
	private Azienda aziendaOspitanteEvento;
	private List<Utente> iscrittiEvento;
	private List<Vino> viniEvento;
	private BadgeEvento badgeEventoInt;
	private ProvinciaEvento provinciaEventoInt;
	private AziendaEvento aziendaOspitanteEventoInt;
	private AziendaEvento aziendaFornitriceEventoInt;
	private List<UtenteEvento> iscrittiEventoInt;//solo uso interno
	private List<VinoEvento> viniEventoInt;//solo uso interno
	private List<Azienda> aziendeViniEvento;
	private String orarioEvento; //inutile
	private long oldDate;
	private String oldIdAzienda;
	private int acquistabileEvento;
	private int creditiEvento;
	private int puntiEsperienza;
	/**
	 * @return the idEvento
	 */
	@DynamoDBHashKey(attributeName="idEvento")
	public String getIdEvento() {
		return idEvento;
	}
	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	
	/**
	 * @return the dataEvento
	 */
	@DynamoDBRangeKey(attributeName="dataEvento")
	public long getDataEvento() {
		return dataEvento;
	}
	/**
	 * @param dataEvento the dataEvento to set
	 */
	public void setDataEvento(long dataEvento) {
		this.dataEvento = dataEvento;
	}
	/**
	 * @return the acquistabileEvento
	 */
	@DynamoDBAttribute(attributeName="acquistabileEvento")
	public int getAcquistabileEvento() {
		return acquistabileEvento;
	}
	/**
	 * @param acquistabileEvento the acquistabileEvento to set
	 */
	public void setAcquistabileEvento(int acquistabileEvento) {
		this.acquistabileEvento = acquistabileEvento;
	}
	/**
	 * @return the creditiEvento
	 */
	@DynamoDBAttribute(attributeName="creditiEvento")
	public int getCreditiEvento() {
		return creditiEvento;
	}
	/**
	 * @param creditiEvento the creditiEvento to set
	 */
	public void setCreditiEvento(int creditiEvento) {
		this.creditiEvento = creditiEvento;
	}
	/**
	 * @return the oldDate
	 */
	@DynamoDBAttribute(attributeName="oldDate")
	public long getOldDate() {
		return oldDate;
	}
	/**
	 * @param oldDate the oldDate to set
	 */
	public void setOldDate(long oldDate) {
		this.oldDate = oldDate;
	}
	/**
	 * @return the puntiEsperienza
	 */
	@DynamoDBAttribute(attributeName="puntiEsperienza")
	public long getPuntiEsperienza() {
		return puntiEsperienza;
	}
	/**
	 * @param puntiEsperienza the puntiEsperienza to set
	 */
	public void setPuntiEsperienza(int puntiEsperienza) {
		this.puntiEsperienza = puntiEsperienza;
	}
	/**
	 * @return the cittaEvento
	 */
	@DynamoDBAttribute(attributeName="cittaEvento")
	public String getCittaEvento() {
		return cittaEvento;
	}
	/**
	 * @param cittaEvento the cittaEvento to set
	 */
	public void setCittaEvento(String cittaEvento) {
		this.cittaEvento = cittaEvento;
	}
	
	/**
	 * @return the orario
	 */
	@DynamoDBAttribute(attributeName="orarioEvento")
	public String getOrarioEvento() {
		return orarioEvento;
	}
	/**
	 * @param orarioEvento the orarioEvento to set
	 */
	public void setOrarioEvento(String orarioEvento) {
		this.orarioEvento = orarioEvento;
	}
	
	/**
	 * @return the aziende vini evento
	 */
	@DynamoDBAttribute(attributeName="aziendeViniEvento")
	public List<Azienda> getAziendeViniEvento() {
		return aziendeViniEvento;
	}
	/**
	 * @param cittaEvento the aziende vini evento to set
	 */
	public void setAziendeViniEvento(List<Azienda> aziendeViniEvento) {
		this.aziendeViniEvento = aziendeViniEvento;
	}
	/**
	 * @return the titoloEvento
	 */
	@DynamoDBAttribute(attributeName="titoloEvento")
	public String getTitoloEvento() {
		return titoloEvento;
	}
	/**
	 * @param titoloEvento the titoloEvento to set
	 */
	public void setTitoloEvento(String titoloEvento) {
		this.titoloEvento = titoloEvento;
	}
	/**
	 * @return the temaEvento
	 */
	@DynamoDBAttribute(attributeName="temaEvento")
	public String getTemaEvento() {
		return temaEvento;
	}
	/**
	 * @param temaEvento the temaEvento to set
	 */
	public void setTemaEvento(String temaEvento) {
		this.temaEvento = temaEvento;
	}
	
	/**
	 * @return the prezzoEvento
	 */
	@DynamoDBAttribute(attributeName="prezzoEvento")
	public float getPrezzoEvento() {
		return prezzoEvento;
	}
	/**
	 * @param prezzoEvento the prezzoEvento to set
	 */
	public void setPrezzoEvento(float prezzoEvento) {
		this.prezzoEvento = prezzoEvento;
	}
	
	/**
	 * @return the urlFotoEvento
	 */
	@DynamoDBAttribute(attributeName="urlFotoEvento")
	public String getUrlFotoEvento() {
		return urlFotoEvento;
	}
	/**
	 * @param urlFotoEvento the urlFotoEvento to set
	 */
	public void setUrlFotoEvento(String urlFotoEvento) {
		this.urlFotoEvento = urlFotoEvento;
	}
	
	/**
	 * @return the statoevento
	 */
	@DynamoDBAttribute(attributeName="statoEvento")
	public String getStatoEvento() {
		return statoEvento;
	}
	/**
	 * @param statoevento the statoevento to set
	 */
	public void setStatoEvento(String statoevento) {
		this.statoEvento = statoevento;
	}
	
	/**
	 * @return the testoEvento
	 */
	@DynamoDBAttribute(attributeName="testoEvento")
	public String getTestoEvento() {
		return testoEvento;
	}
	/**
	 * @param testoEvento the testoEvento to set
	 */
	public void setTestoEvento(String testoEvento) {
		this.testoEvento = testoEvento;
	}
	
	/**
	 * @return the latitudineEvento
	 */
	@DynamoDBAttribute(attributeName="latitudineEvento")
	public double getLatitudineEvento() {
		return latitudineEvento;
	}
	/**
	 * @param latitudineEvento the latitudineEvento to set
	 */
	public void setLatitudineEvento(double latitudineEvento) {
		this.latitudineEvento = latitudineEvento;
	}
	
	/**
	 * @return the longitudineEvento
	 */
	@DynamoDBAttribute(attributeName="longitudineEvento")
	public double getLongitudineEvento() {
		return longitudineEvento;
	}
	/**
	 * @param longitudineEvento the longitudineEvento to set
	 */
	public void setLongitudineEvento(double longitudineEvento) {
		this.longitudineEvento = longitudineEvento;
	}
	
	/**
	 * @return the indirizzoEvento
	 */
	@DynamoDBAttribute(attributeName="indirizzoEvento")
	public String getIndirizzoEvento() {
		return indirizzoEvento;
	}
	/**
	 * @param indirizzoEvento the indirizzoEvento to set
	 */
	public void setIndirizzoEvento(String indirizzoEvento) {
		this.indirizzoEvento = indirizzoEvento;
	}
	
	/**
	 * @return the telefonoEvento
	 */
	@DynamoDBAttribute(attributeName="telefonoEvento")
	public String getTelefonoEvento() {
		return telefonoEvento;
	}
	/**
	 * @param telefonoEvento the telefonoEvento to set
	 */
	public void setTelefonoEvento(String telefonoEvento) {
		this.telefonoEvento = telefonoEvento;
	}
	/**
	 * @return the emailEvento
	 */
	@DynamoDBAttribute(attributeName="emailEvento")
	public String getEmailEvento() {
		return emailEvento;
	}
	/**
	 * @param emailEvento the emailEvento to set
	 */
	public void setEmailEvento(String emailEvento) {
		this.emailEvento = emailEvento;
	}
	/**
	 * @return the badgeEvento
	 */
	@DynamoDBIgnore
	public Badge getBadgeEvento() {
		return badgeEvento;
	}
	/**
	 * @param badgeEvento the badgeEvento to set
	 */
	public void setBadgeEvento(Badge badgeEvento) {
		this.badgeEvento = badgeEvento;
	}
	
	/**
	 * @return the numeroMaxPartecipantiEvento
	 */
	@DynamoDBAttribute(attributeName="numMaxPartecipantiEvento")
	public int getNumMaxPartecipantiEvento() {
		return numMaxPartecipantiEvento;
	}
	/**
	 * @param numeroMaxPartecipantiEvento the numeroMaxPartecipantiEvento to set
	 */
	public void setNumMaxPartecipantiEvento(int numeroMaxPartecipantiEvento) {
		this.numMaxPartecipantiEvento = numeroMaxPartecipantiEvento;
	}
	
	/**
	 * @return the numeroPostiDisponibiliEvento
	 */
	@DynamoDBAttribute(attributeName="numPostiDisponibiliEvento")
	public int getNumPostiDisponibiliEvento() {
		return numPostiDisponibiliEvento;
	}
	/**
	 * @param numeroPostiDisponibiliEvento the numeroPostiDisponibiliEvento to set
	 */
	public void setNumPostiDisponibiliEvento(int numeroPostiDisponibiliEvento) {
		this.numPostiDisponibiliEvento = numeroPostiDisponibiliEvento;
	}
	
	/**
	 * @return the iscrittiEvento
	 */
	@DynamoDBIgnore
	public List<Utente> getIscrittiEvento() {
		return iscrittiEvento;
	}
	/**
	 * @param iscrittiEvento the iscrittiEvento to set
	 */
	public void setIscrittiEvento(List<Utente> iscrittiEvento) {
		this.iscrittiEvento = iscrittiEvento;
	}
	
	/**
	 * @return the oldIdAzineda
	 */
	@DynamoDBAttribute(attributeName="oldIdAzienda")
	public String getOldIdAzienda() {
		return oldIdAzienda;
	}
	/**
	 * @param oldIdAzineda the oldIdAzineda to set
	 */
	public void setOldIdAzienda(String oldIdAzienda) {
		this.oldIdAzienda = oldIdAzienda;
	}
	
	/**
	 * @return the aziendaEvento
	 */
	@DynamoDBIgnore
	public Azienda getAziendaOspitanteEvento() {
		return aziendaOspitanteEvento;
	}
	/**
	 * @param aziendaEvento the aziendaEvento to set
	 */
	public void setAziendaOspitanteEvento(Azienda aziendaOspitanteEvento) {
		this.aziendaOspitanteEvento = aziendaOspitanteEvento;
	}
	
	
	/**
	 * @return the viniEvento
	 */
	@DynamoDBIgnore
	public List<Vino> getViniEvento() {
		return viniEvento;
	}
	/**
	 * @param viniEvento the viniEvento to set
	 */
	public void setViniEvento(List<Vino> viniEvento) {
		this.viniEvento = viniEvento;
	}
	
	/**
	 * @return the dataEventoStringa
	 */
	@DynamoDBAttribute(attributeName="dataEventoStringa")
	public String getDataEventoStringa() {
		return dataEventoStringa;
	}
	/**
	 * @param dataEventoStringa the dataEventoStringa to set
	 */
	public void setDataEventoStringa(String dataEventoStringa) {
		this.dataEventoStringa = dataEventoStringa;
	}
	
	/**
	 * @return the provinciaEvento
	 */
	@DynamoDBIgnore
	public Provincia getProvinciaEvento() {
		return provinciaEvento;
	}
	/**
	 * @param provinciaEvento the provinciaEvento to set
	 */
	public void setProvinciaEvento(Provincia provinciaEvento) {
		this.provinciaEvento = provinciaEvento;
	}
	
	/**
	 * @return the aziendaOspitanteEventoInt
	 */
	@DynamoDBAttribute(attributeName="aziendaOspitanteEvento")
	public AziendaEvento getAziendaOspitanteEventoInt() { return aziendaOspitanteEventoInt; }
	public void setAziendaOspitanteEventoInt(AziendaEvento aziendaOspitanteEventoInt) { this.aziendaOspitanteEventoInt = aziendaOspitanteEventoInt; }
	
	/**
	 * @return the aziendaFornitriceEventoInt
	 */
	@DynamoDBAttribute(attributeName="aziendaFornitriceEvento")
	public AziendaEvento getAziendaFornitriceEventoInt() { return aziendaFornitriceEventoInt; }
	public void setAziendaFornitriceEventoInt(AziendaEvento aziendaFornitriceEventoInt) { this.aziendaFornitriceEventoInt = aziendaFornitriceEventoInt; }
	
	
	/**
	 * @return the badgeEventoInt
	 */
	@DynamoDBAttribute(attributeName="badgeEvento")
	public BadgeEvento getBadgeEventoInt() { return badgeEventoInt; }
	public void setBadgeEventoInt(BadgeEvento badgeEventoInt) { this.badgeEventoInt = badgeEventoInt; }
	
	/**
	 * @return the provinciaEventoInt
	 */
	@DynamoDBAttribute(attributeName="provinciaEvento")
	public ProvinciaEvento getProvinciaEventoInt() { return provinciaEventoInt; }
	public void setProvinciaEventoInt(ProvinciaEvento provinciaEventoInt) { this.provinciaEventoInt = provinciaEventoInt; }
	
	/**
	 * @return the iscrittiEventoInt
	 */
	@DynamoDBAttribute(attributeName="iscrittiEvento")
	public List<UtenteEvento> getIscrittiEventoInt() {
		return iscrittiEventoInt;
	}
	/**
	 * @param iscrittiEventoInt the iscrittiEventoInt to set
	 */
	public void setIscrittiEventoInt(List<UtenteEvento> iscrittiEventoInt) {
		this.iscrittiEventoInt = iscrittiEventoInt;
	}
	
	/**
	 * @return the viniEventoInt
	 */
	@DynamoDBAttribute(attributeName="viniEvento")
	public List<VinoEvento> getViniEventoInt() {
		return viniEventoInt;
	}
	/**
	 * @param viniEventoInt the viniEventoInt to set
	 */
	public void setViniEventoInt(List<VinoEvento> viniEventoInt) {
		this.viniEventoInt = viniEventoInt;
	}
	
	@DynamoDBDocument
	public static class AziendaEvento{
		private String idAzienda;
		private String infoAzienda;

		/**
		 * @return the idAzienda
		 */
		@DynamoDBAttribute(attributeName="idAzienda")
		public String getIdAzienda() { return idAzienda; }
		public void setIdAzienda(String idAzienda) { this.idAzienda = idAzienda; }
		
		/**
		 * @return the infoAzienda
		 */
		@DynamoDBAttribute(attributeName="infoAzienda")
		public String getInfoAzienda() {
			return infoAzienda;
		}
		/**
		 * @param infoAzienda the infoAzienda to set
		 */
		public void setInfoAzienda(String infoAzienda) {
			this.infoAzienda = infoAzienda;
		}
	}
	
	@DynamoDBDocument
	public static class UtenteEvento{
		private String idUtente;

		/**
		 * @return the idUtente
		 */
		@DynamoDBAttribute(attributeName="idUtente")
		public String getIdUtente() {
			return idUtente;
		}
		/**
		 * @param idUtente the idUtente to set
		 */
		public void setIdUtente(String idUtente) {
			this.idUtente = idUtente;
		}
	}
	
	@DynamoDBDocument
	public static class VinoEvento{
		
		private String idVino;
		private String nomeVino;
		private String nomeAziendaVino;
		private String idAziendaVino;
		private int annoVino;
		private String statoVino;
		private String uvaggioVino;
		private String inBreveVino;
		private String urlLogoVino;
		
		
		/**
		 * @return the idVino
		 */
		@DynamoDBAttribute(attributeName="idVino")
		public String getIdVino() {
			return idVino;
		}
		/**
		 * @param idVino the idVino to set
		 */
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
		 * @return the annoVino
		 */
		@DynamoDBAttribute(attributeName="annoVino")
		public int getAnnoVino() {
			return annoVino;
		}
		/**
		 * @param annoVino the annoVino to set
		 */
		public void setAnnoVino(int annoVino) {
			this.annoVino = annoVino;
		}
		/**
		 * @return the nomeAziendaVino
		 */
		@DynamoDBAttribute(attributeName="nomeAziendaVino")
		public String getNomeAziendaVino() {
			return nomeAziendaVino;
		}
		/**
		 * @param nomeAziendaVino the nomeAziendaVino to set
		 */
		public void setNomeAziendaVino(String nomeAziendaVino) {
			this.nomeAziendaVino = nomeAziendaVino;
		}
		/**
		 * @return the idAziendaVino
		 */
		@DynamoDBAttribute(attributeName="idAziendaVino")
		public String getIdAziendaVino() {
			return idAziendaVino;
		}
		/**
		 * @param idAziendaVino the idAziendaVino to set
		 */
		public void setIdAziendaVino(String idAziendaVino) {
			this.idAziendaVino = idAziendaVino;
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
	}
	
	@DynamoDBDocument
	public static class ProvinciaEvento{
		
		private String idProvincia;
		private String nomeProvincia;
		private String siglaProvincia;

		/**
		 * @return the idProvincia
		 */
		@DynamoDBAttribute(attributeName="idProvincia")
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
	}
	
	@DynamoDBDocument
	public static class BadgeEvento{
		private String idBadge;

		/**
		 * @return the idBadge
		 */
		@DynamoDBAttribute(attributeName="idBadge")
		public String getIdBadge() {
			return idBadge;
		}
		/**
		 * @param idBadge the idBadge to set
		 */
		public void setIdBadge(String idBadge) {
			this.idBadge = idBadge;
		}
	}

	
}
