package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Badge;
import com.marte5.modello2.Badge.EventoBadge;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Livello;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.BadgeUtente;
import com.marte5.modello2.Utente.EventoUtente;
import com.marte5.modello2.Utente.UtenteUtente;
import com.marte5.modello2.Utente.VinoUtente;

public class getUtenteGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {
	
	public static final int INFINITI_PUNTI_ESP = -1;
	
    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetGenerica risposta = getRisposta(input);
        
        return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
    		
    		String idUtente = input.getIdUtente();
    		String idUtentePadre = input.getIdUtentePadre();
        Utente utente = new Utente();
    	
        Esito esito = FunzioniUtils.getEsitoPositivo();
        esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
        
        //scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
        AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getUtente ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			if(idUtente == null || idUtente.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idUtente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			utente = mapper.load(Utente.class, idUtente);
			if(utente == null) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " utente nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			//gestione e recupero eventi associati all'utente
			//List<EventoUtente> eventiUtente = utente.getEventiUtenteInt();
			//scommentare per nuova versione connect
			List<EventoUtente> eventiUtente = utente.getPreferitiEventiUtenteInt();
			List<Evento> eventiCompletiUtente = new ArrayList<>();
			if(eventiUtente != null) {
				for (Iterator<EventoUtente> iterator = eventiUtente.iterator(); iterator.hasNext();) {
					EventoUtente evento = iterator.next();
					Evento eventoCompleto = mapper.load(Evento.class, evento.getIdEvento(), evento.getDataEvento());
					if(eventoCompleto != null && eventoCompleto.getPubblicatoEvento() == true) {
						eventoCompleto.setStatoEvento(evento.getStatoEvento());
						try {
							String stato = FunzioniUtils.getStatoEvento(utente, eventoCompleto, eventoCompleto.getDataEvento(), mapper);
							eventoCompleto.setStatoEvento(stato);
							stato = FunzioniUtils.getStatoEventoPreferito(utente, eventoCompleto, eventoCompleto.getDataEvento(), mapper);
							eventoCompleto.setStatoPreferitoEvento(stato);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						eventiCompletiUtente.add(eventoCompleto);
					}
				}
			}
			//ordinamento eventi
			Collections.sort(eventiCompletiUtente, new Comparator<Evento>(){
		         @Override
		         public int compare(Evento o1, Evento o2){
		            if (o1.getDataEvento() < o2.getDataEvento())return -1;
		            if (o1.getDataEvento() == o2.getDataEvento())return 0;
		            else return 1;
		         }
		      });
			utente.setEventiUtente(eventiCompletiUtente);
			
			//gestione e recupero badge associati all'utente
			List<BadgeUtente> badges = utente.getBadgeUtenteInt();
			
				DynamoDBScanExpression expr = new DynamoDBScanExpression();
				List<Badge> tuttiBadge;
				try {
					tuttiBadge = mapper.scan(Badge.class, expr);
				} catch (Exception e) {
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
					esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " problema nell'estrazione di tutti i badge ");
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
				List<Badge> badgesCompleti = new ArrayList<>();
				long data = Calendar.getInstance().getTimeInMillis();
				for (Badge badge : tuttiBadge) {
					Badge nuovo = new Badge();
					nuovo.setIdBadge(badge.getIdBadge());
					nuovo.setNomeBadge(badge.getNomeBadge());
					nuovo.setInfoBadge(badge.getInfoBadge());
					nuovo.setUrlLogoBadge(badge.getUrlLogoBadge());
					nuovo.setDataBadge(badge.getDataBadge());
					nuovo.setEventoBadge(badge.getEventoBadge());
					nuovo.setTuoBadge("N");
					if (badges != null) {
						for (BadgeUtente badgeUtente : badges) {
							if(badgeUtente.getIdBadge().equals(badge.getIdBadge())) {
								nuovo.setTuoBadge("S");
							}
						}
					}
					EventoBadge be = badge.getEventoBadge();
					boolean p  = true;
					if (be != null) {
						p = be.getPubblicatoEvento();
					}
					
					if (
						((input.getIdUtente().equals(input.getIdUtentePadre()) && badge.getDataBadge() >= data) ||
						nuovo.getTuoBadge().equals("S")) && p 
						) {
							badgesCompleti.add(nuovo);
						}
				}
				//riordino i badge per data
				Collections.sort(badgesCompleti, new Comparator<Badge>(){
					@Override
					public int compare(Badge arg0, Badge arg1) {
						long d0 = arg0.getDataBadge();
						long d1 = arg1.getDataBadge();
						return (Long.compare(d0, d1));
					}
			      });
				utente.setBadgeUtente(badgesCompleti);
			//riordino vini
			List<VinoUtente> vini = utente.getViniUtenteInt();
			List<Azienda> aziendeConvertite = new ArrayList<>();
			if(vini != null) {
				aziendeConvertite = FunzioniUtils.riordinaViniAzienda_Utente(vini, mapper);
			}
			utente.setAziendeUtente(aziendeConvertite);
			
			//recupero associazione fra utente loggato e l'utente richiesto
			utente.setStatoUtente("D");
			if(idUtentePadre != null) {
				Utente utentePadre = mapper.load(Utente.class, idUtentePadre);
				if(utentePadre != null) {
					List<UtenteUtente> utentiSeguiti = utentePadre.getUtentiUtenteInt();
					if(utentiSeguiti != null) {
						for(UtenteUtente u : utentiSeguiti) {
							if(u.getIdUtente().equals(idUtente)) {
								utente.setStatoUtente("A");
							}
						}
					}
				}
			}
//			gestione livello utente 
			int esp = utente.getEsperienzaUtente();
			utente.setLivelloUtente("unknown");
			List<Livello> listaLivelli = mapper.scan(Livello.class, expr);
			for (Livello l : listaLivelli) {
				if (l.getMax() != INFINITI_PUNTI_ESP) {
					if (esp >= l.getMin() && esp <= l.getMax() ) {
						utente.setLivelloUtente(l.getNomeLivello());
						int gap = l.getMax() - esp + 1;
						String prox = "";
						for (Livello l1: listaLivelli) {
							if (l1.getMin() == l.getMax() + 1) prox = l1.getNomeLivello();
						}
						utente.setPuntiMancantiProssimoLivelloUtente("Per diventare " + prox + " ti mancano " + gap + " pt" );
						break;
					}
				}else {
					if (esp >= l.getMin() ) {
						utente.setLivelloUtente(l.getNomeLivello());
						utente.setPuntiMancantiProssimoLivelloUtente("Hai raggiunto il massimo livello" );
						break;
					}
				}
			}
		}   
        risposta.setEsito(esito);
        risposta.setUtente(utente);
        return risposta;
    }
    
}
