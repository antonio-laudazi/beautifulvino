/**
 * 
 */
package com.marte5.modello;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author paolosalvadori
 *
 */
@DynamoDBTable(tableName="BV_Feed")
public class Feed {

	private long idFeed;
	private long idEntitaFeed;
	private int tipoFeed;
	private long dataFeed;
	private String urlImmagineFeed;
	private String titoloFeed;
	private String testoLabelFeed;
	private String urlImmagineHeaderFeed;
	private String headerFeed;
	private String sottoHeaderFeed;
	private List<VinoFeed> viniFeed;
	private String testoFeed;
	
	/**
	 * @return the idFeed
	 */
	@DynamoDBHashKey(attributeName="idFeed")
	public long getIdFeed() {
		return idFeed;
	}
	/**
	 * @param idFeed the idFeed to set
	 */
	public void setIdFeed(long idFeed) {
		this.idFeed = idFeed;
	}
	
	/**
	 * @return the idEntitaFeed
	 */
	@DynamoDBAttribute(attributeName="idEntitaFeed")
	public long getIdEntitaFeed() {
		return idEntitaFeed;
	}
	/**
	 * @param idEntitaFeed the idEntitaFeed to set
	 */
	public void setIdEntitaFeed(long idEntitaFeed) {
		this.idEntitaFeed = idEntitaFeed;
	}
	
	/**
	 * @return the tipoFeed
	 */
	@DynamoDBAttribute(attributeName="idTipoFeed")
	public int getTipoFeed() {
		return tipoFeed;
	}
	/**
	 * @param tipoFeed the tipoFeed to set
	 */
	public void setTipoFeed(int tipoFeed) {
		this.tipoFeed = tipoFeed;
	}
	/**
	 * @return the dataFeed
	 */
	@DynamoDBRangeKey(attributeName="dataFeed")
	public long getDataFeed() {
		return dataFeed;
	}
	/**
	 * @param dataFeed the dataFeed to set
	 */
	public void setDataFeed(long dataFeed) {
		this.dataFeed = dataFeed;
	}
	/**
	 * @return the urlImmagineFeed
	 */
	@DynamoDBAttribute(attributeName="urlImmagineFeed")
	public String getUrlImmagineFeed() {
		return urlImmagineFeed;
	}
	/**
	 * @param urlImmagineFeed the urlImmagineFeed to set
	 */
	public void setUrlImmagineFeed(String urlImmagineFeed) {
		this.urlImmagineFeed = urlImmagineFeed;
	}
	/**
	 * @return the titoloFeed
	 */
	@DynamoDBAttribute(attributeName="titoloFeed")
	public String getTitoloFeed() {
		return titoloFeed;
	}
	/**
	 * @param titoloFeed the titoloFeed to set
	 */
	public void setTitoloFeed(String titoloFeed) {
		this.titoloFeed = titoloFeed;
	}
	/**
	 * @return the testoLabelFeed
	 */
	@DynamoDBAttribute(attributeName="testoLabelFeed")
	public String getTestoLabelFeed() {
		return testoLabelFeed;
	}
	/**
	 * @param testoLabelFeed the testoLabelFeed to set
	 */
	public void setTestoLabelFeed(String testoLabelFeed) {
		this.testoLabelFeed = testoLabelFeed;
	}
	/**
	 * @return the urlImmagineHeaderFeed
	 */
	@DynamoDBAttribute(attributeName="urlImmagineHeaderFeed")
	public String getUrlImmagineHeaderFeed() {
		return urlImmagineHeaderFeed;
	}
	/**
	 * @param urlImmagineHeaderFeed the urlImmagineHeaderFeed to set
	 */
	public void setUrlImmagineHeaderFeed(String urlImmagineHeaderFeed) {
		this.urlImmagineHeaderFeed = urlImmagineHeaderFeed;
	}
	/**
	 * @return the headerFeed
	 */
	@DynamoDBAttribute(attributeName="headerFeed")
	public String getHeaderFeed() {
		return headerFeed;
	}
	/**
	 * @param headerFeed the headerFeed to set
	 */
	public void setHeaderFeed(String headerFeed) {
		this.headerFeed = headerFeed;
	}
	/**
	 * @return the sottoHeaderFeed
	 */
	@DynamoDBAttribute(attributeName="sottoHeaderFeed")
	public String getSottoHeaderFeed() {
		return sottoHeaderFeed;
	}
	/**
	 * @param sottoHeaderFeed the sottoHeaderFeed to set
	 */
	public void setSottoHeaderFeed(String sottoHeaderFeed) {
		this.sottoHeaderFeed = sottoHeaderFeed;
	}
	/**
	 * @return the viniFeed
	 */
	@DynamoDBAttribute(attributeName="viniFeed")
	public List<VinoFeed> getViniFeed() {
		return viniFeed;
	}
	/**
	 * @param viniFeed the viniFeed to set
	 */
	public void setViniFeed(List<VinoFeed> viniFeed) {
		this.viniFeed = viniFeed;
	}
	/**
	 * @return the testoFeed
	 */
	@DynamoDBAttribute(attributeName="testoFeed")
	public String getTestoFeed() {
		return testoFeed;
	}
	/**
	 * @param testoFeed the testoFeed to set
	 */
	public void setTestoFeed(String testoFeed) {
		this.testoFeed = testoFeed;
	}
	
	@DynamoDBDocument
	public class VinoFeed{
		private long idVino;
		private String nomeVino;
		private String infoVino;
		private AziendaVino aziendaVino;
		
		/**
		 * @return the idVino
		 */
		@DynamoDBAttribute(attributeName="")
		public long getIdVino() {
			return idVino;
		}
		/**
		 * @param idVino the idVino to set
		 */
		public void setIdVino(long idVino) {
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
		 * @return the aziendaVino
		 */
		@DynamoDBAttribute(attributeName="aziendaVino")
		public AziendaVino getAziendaVino() {
			return aziendaVino;
		}
		/**
		 * @param aziendaVino the aziendaVino to set
		 */
		public void setAziendaVino(AziendaVino aziendaVino) {
			this.aziendaVino = aziendaVino;
		}
		
		@DynamoDBDocument
		public class AziendaVino {
			private long idAzienda;
			private String nomeAzienda;
			
			/**
			 * @return the idAzienda
			 */
			@DynamoDBAttribute(attributeName="idAzienda")
			public long getIdAzienda() {
				return idAzienda;
			}
			/**
			 * @param idAzienda the idAzienda to set
			 */
			public void setIdAzienda(long idAzienda) {
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
		
	}
	
}
