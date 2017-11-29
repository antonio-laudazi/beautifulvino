/**
 * 
 */
package com.marte5.modello;

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
	private String urlVideoFeed;
	private String titoloFeed;
	private String testoLabelFeed;
	private long idEntitaHeaderFeed;
	private String tipoEntitaHeaderFeed;
	private String urlImmagineHeaderFeed;
	private String headerFeed;
	private String sottoHeaderFeed;
	private VinoFeed vinoFeed;
	private EventoFeed eventoFeed;
	private String testoFeed;
	private String visualizzaButtonFeed;
	
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
	public VinoFeed getVinoFeed() {
		return vinoFeed;
	}
	/**
	 * @param viniFeed the viniFeed to set
	 */
	public void setVinoFeed(VinoFeed vinoFeed) {
		this.vinoFeed = vinoFeed;
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
	
	@DynamoDBDocument
	public class EventoFeed{
		private long idEvento;
		private String titoloEvento;
		private String temaEvento;
		private String urlFotoEvento;
		/**
		 * @return the idEvento
		 */
		@DynamoDBAttribute(attributeName="idEvento")
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
	}

	/**
	 * @return the urlVideoFeed
	 */
	public String getUrlVideoFeed() {
		return urlVideoFeed;
	}
	/**
	 * @param urlVideoFeed the urlVideoFeed to set
	 */
	public void setUrlVideoFeed(String urlVideoFeed) {
		this.urlVideoFeed = urlVideoFeed;
	}
	/**
	 * @return the idEntitaHeaderFeed
	 */
	public long getIdEntitaHeaderFeed() {
		return idEntitaHeaderFeed;
	}
	/**
	 * @param idEntitaHeaderFeed the idEntitaHeaderFeed to set
	 */
	public void setIdEntitaHeaderFeed(long idEntitaHeaderFeed) {
		this.idEntitaHeaderFeed = idEntitaHeaderFeed;
	}
	/**
	 * @return the tipoEntitaHeaderFeed
	 */
	public String getTipoEntitaHeaderFeed() {
		return tipoEntitaHeaderFeed;
	}
	/**
	 * @param tipoEntitaHeaderFeed the tipoEntitaHeaderFeed to set
	 */
	public void setTipoEntitaHeaderFeed(String tipoEntitaHeaderFeed) {
		this.tipoEntitaHeaderFeed = tipoEntitaHeaderFeed;
	}
	/**
	 * @return the eventoFeed
	 */
	public EventoFeed getEventoFeed() {
		return eventoFeed;
	}
	/**
	 * @param eventoFeed the eventoFeed to set
	 */
	public void setEventoFeed(EventoFeed eventoFeed) {
		this.eventoFeed = eventoFeed;
	}
	/**
	 * @return the visualizzaButtonFeed
	 */
	public String getVisualizzaButtonFeed() {
		return visualizzaButtonFeed;
	}
	/**
	 * @param visualizzaButtonFeed the visualizzaButtonFeed to set
	 */
	public void setVisualizzaButtonFeed(String visualizzaButtonFeed) {
		this.visualizzaButtonFeed = visualizzaButtonFeed;
	}
	
}
