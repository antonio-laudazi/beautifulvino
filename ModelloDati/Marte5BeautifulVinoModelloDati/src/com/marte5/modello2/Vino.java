package com.marte5.modello2;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/***
 * 
 * @author paolosalvadori
 *
 */
@DynamoDBTable(tableName="BVino_Vino")
public class Vino {

	private String idVino;
	private String nomeVino;
	private int annoVino;
	private String inBreveVino;
	private String infoVino;
	private String uvaggioVino;
	private String regioneVino;
	private String profumoVino;
	private String urlLogoVino;
	private String urlImmagineVino;
	private String statoVino;
	private float prezzoVino;
	private int acquistabileVino;
	private Azienda aziendaVino;
	private List<Evento> eventiVino;
	private List<Utente> utentiVino;
	private AziendaVino aziendaVinoInt;
	private List<EventoVino> eventiVinoInt;
	private List<UtenteVino> utentiVinoInt;
	
	
	/**
	 * @return the idVino
	 */
	@DynamoDBHashKey(attributeName="idVino")
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
	 * @return the prezzoVino
	 */
	@DynamoDBAttribute(attributeName="prezzoVino")
	public float getPrezzoVino() {
		return prezzoVino;
	}
	/**
	 * @param nomeVino the nomeVino to set
	 */
	public void setPrezzoVino(float prezzoVino) {
		this.prezzoVino = prezzoVino;
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
	 * @return the infoVino
	 */
	@DynamoDBAttribute(attributeName="infoVino")
	public String getInfoVino() {
		return infoVino;
	}
	/**
	 * @param infoVino the infoVino to set
	 */
	public void setInfoVino(String infoVino) {
		this.infoVino = infoVino;
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
	 * @return the regioneVino
	 */
	@DynamoDBAttribute(attributeName="regioneVino")
	public String getRegioneVino() {
		return regioneVino;
	}
	/**
	 * @param regioneVino the regioneVino to set
	 */
	public void setRegioneVino(String regioneVino) {
		this.regioneVino = regioneVino;
	}
	/**
	 * @return the profumoVino
	 */
	@DynamoDBAttribute(attributeName="profumoVino")
	public String getProfumoVino() {
		return profumoVino;
	}
	/**
	 * @param profumoVino the profumoVino to set
	 */
	public void setProfumoVino(String profumoVino) {
		this.profumoVino = profumoVino;
	}
	/**
	 * @return the urlImmagineVino
	 */
	@DynamoDBAttribute(attributeName="urlImmagineVino")
	public String getUrlImmagineVino() {
		return urlImmagineVino;
	}
	/**
	 * @param urlImmagineVino the urlImmagineVino to set
	 */
	public void setUrlImmagineVino(String urlImmagineVino) {
		this.urlImmagineVino = urlImmagineVino;
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
	 * @return the aziendaVino
	 */
	@DynamoDBIgnore
	public Azienda getAziendaVino() {
		return aziendaVino;
	}
	/**
	 * @param aziendaVino the aziendaVino to set
	 */
	public void setAziendaVino(Azienda aziendaVino) {
		this.aziendaVino = aziendaVino;
	}
	
	/**
	 * @return the eventoVino
	 */
	@DynamoDBIgnore
	public List<Evento> getEventiVino() {
		return eventiVino;
	}
	/**
	 * @param eventoVino the eventoVino to set
	 */
	public void setEventiVino(List<Evento> eventoVino) {
		this.eventiVino = eventoVino;
	}
	/**
	 * @return the utentiVino
	 */
	@DynamoDBIgnore
	public List<Utente> getUtentiVino() {
		return utentiVino;
	}
	/**
	 * @param utentiVino the utentiVino to set
	 */
	public void setUtentiVino(List<Utente> utentiVino) {
		this.utentiVino = utentiVino;
	}
	
	@DynamoDBAttribute(attributeName="aziendaVino")
	public AziendaVino getAziendaVinoInt() {
		return aziendaVinoInt;
	}
	public void setAziendaVinoInt(AziendaVino aziendaVinoInt) {
		this.aziendaVinoInt = aziendaVinoInt;
	}
	@DynamoDBAttribute(attributeName="eventiVino")
	public List<EventoVino> getEventiVinoInt() {
		return eventiVinoInt;
	}
	public void setEventiVinoInt(List<EventoVino> eventiVinoInt) {
		this.eventiVinoInt = eventiVinoInt;
	}
	@DynamoDBAttribute(attributeName="utentiVino")
	public List<UtenteVino> getUtentiVinoInt() {
		return utentiVinoInt;
	}
	public void setUtentiVinoInt(List<UtenteVino> utentiVinoInt) {
		this.utentiVinoInt = utentiVinoInt;
	}
	
	@DynamoDBDocument
	public static class AziendaVino{
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
	public static class EventoVino{
		private String idEvento;
		private long dataEvento;

		@DynamoDBAttribute(attributeName="idEvento")
		public String getIdEvento() {
			return idEvento;
		}

		public void setIdEvento(String idEvento) {
			this.idEvento = idEvento;
		}

		/**
		 * @return the dataEvento
		 */
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
	public static class UtenteVino{
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
	 * @return the acquistabileVino
	 */
	public int getAcquistabileVino() {
		return acquistabileVino;
	}
	/**
	 * @param acquistabileVino the acquistabileVino to set
	 */
	public void setAcquistabileVino(int acquistabileVino) {
		this.acquistabileVino = acquistabileVino;
	}

}
