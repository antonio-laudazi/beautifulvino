/**
 * 
 */
package com.marte5.modello;

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
@DynamoDBTable(tableName="BV_Evento")
public class Evento {

	private long idEvento;
	private long dataEvento;
	private String dataEventoStringa;
	private String luogoEvento;
	private String titoloEvento;
	private String temaEvento;
	private float prezzoEvento;
	private String urlFotoEvento;
	private String statoEvento;
	private String testoEvento;
	private double latitudineEvento;
	private double longitudineEvento;
	private String indirizzoEvento;
	private int numMaxPartecipantiEvento;
	private int numPostiDisponibiliEvento;
	private boolean convenzionataEvento;
	private Azienda aziendaEvento;
	private List<Utente> iscrittiEvento;
	private List<Vino> viniEvento;
	private AziendaEvento aziendaEventoInt;
	private List<UtenteEvento> iscrittiEventoInt;//solo uso interno
	private List<VinoEvento> viniEventoInt;//solo uso interno
	
	/**
	 * @return the idEvento
	 */
	@DynamoDBHashKey(attributeName="idEvento")
	public long getIdEvento() {
		return idEvento;
	}
	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(long idEvento) {
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
	 * @return the luogoEvento
	 */
	@DynamoDBAttribute(attributeName="luogoEvento")
	public String getLuogoEvento() {
		return luogoEvento;
	}
	/**
	 * @param luogoEvento the luogoEvento to set
	 */
	public void setLuogoEvento(String luogoEvento) {
		this.luogoEvento = luogoEvento;
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
	 * @return the convenzionataEvento
	 */
	@DynamoDBAttribute(attributeName="convenzionataEvento")
	public boolean isConvenzionataEvento() {
		return convenzionataEvento;
	}
	/**
	 * @param convenzionataEvento the convenzionataEvento to set
	 */
	public void setConvenzionataEvento(boolean convenzionataEvento) {
		this.convenzionataEvento = convenzionataEvento;
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
	 * @return the aziendaEvento
	 */
	@DynamoDBIgnore
	public Azienda getAziendaEvento() {
		return aziendaEvento;
	}
	/**
	 * @param aziendaEvento the aziendaEvento to set
	 */
	public void setAziendaEvento(Azienda aziendaEvento) {
		this.aziendaEvento = aziendaEvento;
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
	 * @return the aziendaEventoInt
	 */
	@DynamoDBAttribute(attributeName="aziendaEvento")
	public AziendaEvento getAziendaEventoInt() { return aziendaEventoInt; }
	public void setAziendaEventoInt(AziendaEvento aziendaEventoInt) { this.aziendaEventoInt = aziendaEventoInt; }
	
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
		private long idAzienda;

		/**
		 * @return the idAzienda
		 */
		@DynamoDBAttribute(attributeName="idAzienda")
		public long getIdAzienda() { return idAzienda; }
		public void setIdAzienda(long idAzienda) { this.idAzienda = idAzienda; }
	}
	
	@DynamoDBDocument
	public static class UtenteEvento{
		private long idUtente;

		/**
		 * @return the idUtente
		 */
		@DynamoDBAttribute(attributeName="idUtente")
		public long getIdUtente() {
			return idUtente;
		}
		/**
		 * @param idUtente the idUtente to set
		 */
		public void setIdUtente(long idUtente) {
			this.idUtente = idUtente;
		}
		
	}
	
	@DynamoDBDocument
	public static class VinoEvento{
		private long idVino;

		/**
		 * @return the idVino
		 */
		@DynamoDBAttribute(attributeName="idVino")
		public long getIdVino() {
			return idVino;
		}
		/**
		 * @param idVino the idVino to set
		 */
		public void setIdVino(long idVino) {
			this.idVino = idVino;
		}
		
		
	}

	
}
