/**
 * 
 */
package com.marte5.modello2;

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
@DynamoDBTable(tableName="BVino_Feed")
public class Feed {

	private String idFeed;
	private String idEntitaFeed;
	private int tipoFeed;
	private long dataFeed;
	private String urlImmagineFeed;
	private String urlVideoFeed;
	private String titoloFeed;
	private String testoLabelFeed;
	private String idEntitaHeaderFeed;
	private long dataEntitaHeaderFeed;
	private String tipoEntitaHeaderFeed;
	private String urlImmagineHeaderFeed;
	private String headerFeed;
	private String sottoHeaderFeed;
	private Vino vinoFeed;
	private String idAziendaFeed;
	private VinoFeed vinoFeedInt;
	private Evento eventoFeed;
	private EventoFeed eventoFeedInt;
	private AziendaFeed aziendaFeedInt;
	private String testoFeed;
	private String visualizzaButtonFeed;
	private int puntiEsperienza;
	private boolean pubblicato;
	
	/**
	 * @return the idFeed
	 */
	@DynamoDBHashKey(attributeName="idFeed")
	public String getIdFeed() {
		return idFeed;
	}
	/**
	 * @param idFeed the idFeed to set
	 */
	public void setIdFeed(String idFeed) {
		this.idFeed = idFeed;
	}
	
	/**
	 * @return the pubblicato
	 */
	@DynamoDBAttribute(attributeName="pubblicato")
	public boolean getPubblicato() {
		return pubblicato;
	}
	/**
	 * @param pubblicato the pubblicato to set
	 */
	public void setPubblicato(boolean pubblicato) {
		this.pubblicato = pubblicato;
	}
	/**
	 * @return the idEntitaFeed
	 */
	@DynamoDBAttribute(attributeName="idEntitaFeed")
	public String getIdEntitaFeed() {
		return idEntitaFeed;
	}
	/**
	 * @param idEntitaFeed the idEntitaFeed to set
	 */
	public void setIdEntitaFeed(String idEntitaFeed) {
		this.idEntitaFeed = idEntitaFeed;
	}
	/**
	 * @return the puntiEsperienza
	 */
	@DynamoDBAttribute(attributeName="puntiEsperienza")
	public int getPuntiEsperienza() {
		return puntiEsperienza;
	}
	/**
	 * @param puntiEsperienza the puntiEsperienza to set
	 */
	public void setPuntiEsperienza(int puntiEsperienza) {
		this.puntiEsperienza = puntiEsperienza;
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
	 * @return the idAziendaFeed
	 */
	@DynamoDBAttribute(attributeName="idAziendaFeed")
	public String getIdAziendaFeed() {
		return idAziendaFeed;
	}
	/**
	 * @param sottoHeaderFeed the sottoHeaderFeed to set
	 */
	public void setIdAziendaFeed(String idAziendaFeed) {
		this.idAziendaFeed = idAziendaFeed;
	}
	/**
	 * @return the viniFeed
	 */
	@DynamoDBIgnore
	public Vino getVinoFeed() {
		return vinoFeed;
	}
	/**
	 * @param viniFeed the viniFeed to set
	 */
	public void setVinoFeed(Vino vinoFeed) {
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
	/**
	 * @return the eventoFeed
	 */
	@DynamoDBIgnore
	public Evento getEventoFeed() {
		return eventoFeed;
	}
	/**
	 * @param eventoFeed the eventoFeed to set
	 */
	public void setEventoFeed(Evento eventoFeed) {
		this.eventoFeed = eventoFeed;
	}
	/**
	 * @return the eventoFeedInt
	 */
	@DynamoDBAttribute(attributeName="eventoFeed")
	public EventoFeed getEventoFeedInt() {
		return eventoFeedInt;
	}
	/**
	 * @param eventoFeedInt the eventoFeedInt to set
	 */
	public void setEventoFeedInt(EventoFeed eventoFeedInt) {
		this.eventoFeedInt = eventoFeedInt;
	}
	
	@DynamoDBDocument
	public static class EventoFeed{
		
		private String idEvento;
		private long dataEvento;
		private String titoloEvento;
		private String temaEvento;
		private String urlFotoEvento;
		
		/**
		 * @return the idEvento
		 */
		@DynamoDBAttribute(attributeName="idEvento")
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
		@DynamoDBAttribute(attributeName="dataEvento")
		public long getDataEvento() {
			return dataEvento;
		}
		/**
		 * @param idEvento the idEvento to set
		 */
		public void setDataEvento(long dataEvento) {
			this.dataEvento = dataEvento;
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
	
	@DynamoDBDocument
	public static class AziendaFeed{
		
		private String idAzienda;
		private String nomeAzienda;
		private String cittaAzienda;
		private boolean active;
		/**
		 * @return the idAzienda
		 */
		@DynamoDBAttribute(attributeName="idAzienda")
		public String getIdAzienda() {
			return idAzienda;
		}
		/**
		 * @param idAzienda the idAzienda to set
		 */
		public void setIdAzienda(String idAzienda) {
			this.idAzienda = idAzienda;
		}
		/**
		 * @return the dataEvento
		 */
		@DynamoDBAttribute(attributeName="nomeAzienda")
		public String getNomeAzienda() {
			return nomeAzienda;
		}
		/**
		 * @param idEvento the idEvento to set
		 */
		public void setNomeAzienda (String nomeAzienda) {
			this.nomeAzienda = nomeAzienda;
		}
		/**
		 * @return the dataEvento
		 */
		@DynamoDBAttribute(attributeName="cittaAzienda")
		public String getCittaAzienda() {
			return cittaAzienda;
		}
		/**
		 * @param idEvento the idEvento to set
		 */
		public void setCittaAzienda (String cittaAzienda) {
			this.cittaAzienda = cittaAzienda;
		}
		/**
		 * @return the active flag
		 */
		@DynamoDBAttribute(attributeName="active")
		public boolean getActive() {
			return active;
		}
		/**
		 * @param active the activeFlag to set
		 */
		public void setActive (boolean active) {
			this.active = active;
		}
	}
	
	@DynamoDBDocument
	public static class VinoFeed{
		private String idVino;
		private String nomeVino;
		private String infoVino;
		private String urlImmagineVino;
		private String urlLogoVino;
		private AziendaVino aziendaVino;
		
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
		public static class AziendaVino {
			private String idAzienda;
			private String nomeAzienda;
			
			/**
			 * @return the idAzienda
			 */
			@DynamoDBAttribute(attributeName="idAzienda")
			public String getIdAzienda() {
				return idAzienda;
			}
			/**
			 * @param idAzienda the idAzienda to set
			 */
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
	
	/**
	 * @return the urlVideoFeed
	 */
	@DynamoDBAttribute(attributeName="urlVideoFeed")
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
	@DynamoDBAttribute(attributeName="idEntitaHeaderFeed")
	public String getIdEntitaHeaderFeed() {
		return idEntitaHeaderFeed;
	}
	/**
	 * @param idEntitaHeaderFeed the idEntitaHeaderFeed to set
	 */
	public void setIdEntitaHeaderFeed(String idEntitaHeaderFeed) {
		this.idEntitaHeaderFeed = idEntitaHeaderFeed;
	}
	/**
	 * @return the tipoEntitaHeaderFeed
	 */
	@DynamoDBAttribute(attributeName="tipoEntitaHeaderFeed")
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
	 * @return the visualizzaButtonFeed
	 */
	@DynamoDBAttribute(attributeName="visualizzaButtonFeed")
	public String getVisualizzaButtonFeed() {
		return visualizzaButtonFeed;
	}
	/**
	 * @param visualizzaButtonFeed the visualizzaButtonFeed to set
	 */
	public void setVisualizzaButtonFeed(String visualizzaButtonFeed) {
		this.visualizzaButtonFeed = visualizzaButtonFeed;
	}
	/**
	 * @return the vinoFeedInt
	 */
	@DynamoDBAttribute(attributeName="vinoFeed")
	public VinoFeed getVinoFeedInt() {
		return vinoFeedInt;
	}
	/**
	 * @param vinoFeedInt the vinoFeedInt to set
	 */
	public void setVinoFeedInt(VinoFeed vinoFeedInt) {
		this.vinoFeedInt = vinoFeedInt;
	}
	/**
	 * @return the aziendaFeedInt
	 */
	@DynamoDBAttribute(attributeName="aziendaFeed")
	public AziendaFeed getAziendaFeedInt() {
		return aziendaFeedInt;
	}
	/**
	 * @param aziendaFeedInt the vinoFeedInt to set
	 */
	public void setAziendaFeedInt(AziendaFeed aziendaFeedInt) {
		this.aziendaFeedInt = aziendaFeedInt;
	}
	/**
	 * @return the dataEntitaHeaderFeed
	 */
	@DynamoDBAttribute(attributeName="dataEntitaHeaderFeed")
	public long getDataEntitaHeaderFeed() {
		return dataEntitaHeaderFeed;
	}
	/**
	 * @param dataEntitaHeaderFeed the dataEntitaHeaderFeed to set
	 */
	public void setDataEntitaHeaderFeed(long dataEntitaHeaderFeed) {
		this.dataEntitaHeaderFeed = dataEntitaHeaderFeed;
	}
	
}
