package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Azienda.EventoAzienda;
import com.marte5.modello2.Azienda.VinoAzienda;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Vino;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getAziendaGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaGetGenerica risposta = getRisposta(input);
        //RispostaGetAzienda risposta = getRispostaDiTest(input);
        return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
        String idAzienda = input.getIdAzienda();
        
        Azienda azienda = new Azienda();
    		
        Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
        
        AmazonDynamoDB client = null;
        try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEvento ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idAzienda == null || idAzienda.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idAzienda nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			azienda = mapper.load(Azienda.class, idAzienda);
			
			List<EventoAzienda> eventiAzienda = azienda.getEventiAziendaInt();//ci sono solo gli id qui
			List<Evento> eventiCompletiAzienda = new ArrayList<>();
			
			if(eventiAzienda != null) {
				
				for (Iterator<EventoAzienda> iterator = eventiAzienda.iterator(); iterator.hasNext();) {
					
					EventoAzienda evento = iterator.next();
					String idEventoDB = evento.getIdEvento();
					long dataEventoDB = evento.getDataEvento();
					
					if(idEventoDB != null && !idEventoDB.equals("") && dataEventoDB != 0) {
						
						Evento eventoCompleto = new Evento();
						Evento eventoEstratto = mapper.load(Evento.class, evento.getIdEvento(), evento.getDataEvento());
						
						if(eventoEstratto != null) {
							eventoCompleto.setIdEvento(eventoEstratto.getIdEvento());
							eventoCompleto.setDataEvento(eventoEstratto.getDataEvento());
							eventoCompleto.setCittaEvento(eventoEstratto.getCittaEvento());
							eventoCompleto.setTitoloEvento(eventoEstratto.getTitoloEvento());
							eventoCompleto.setTemaEvento(eventoEstratto.getTemaEvento());
							eventoCompleto.setPrezzoEvento(eventoEstratto.getPrezzoEvento());
							eventoCompleto.setUrlFotoEvento(eventoEstratto.getUrlFotoEvento());
							
							eventoCompleto.setStatoEvento("N");
							//gestire lo stato dell'evento in base all'associazione con l'utente chiamante
							
							eventoCompleto.setLatitudineEvento(eventoEstratto.getLatitudineEvento());
							eventoCompleto.setLongitudineEvento(eventoEstratto.getLongitudineEvento());
							eventoCompleto.setNumMaxPartecipantiEvento(eventoEstratto.getNumMaxPartecipantiEvento());
							eventoCompleto.setNumPostiDisponibiliEvento(eventoEstratto.getNumPostiDisponibiliEvento());
							
							eventiCompletiAzienda.add(eventoCompleto);
						}
					}
				}
			}
			azienda.setEventiAzienda(eventiCompletiAzienda);
			
			List<VinoAzienda> viniAzienda = azienda.getViniAziendaInt();
			List<Vino> viniCompletiAzienda = new ArrayList<>();
			if(viniAzienda != null) {
				for (Iterator<VinoAzienda> iterator = viniAzienda.iterator(); iterator.hasNext();) {
					VinoAzienda vino = iterator.next();
					Vino vinoCompleto = new Vino();
					Vino vinoEstratto = mapper.load(Vino.class, vino.getIdVino());
					
					if(vinoEstratto != null) {
						vinoCompleto.setIdVino(vinoEstratto.getIdVino());
						vinoCompleto.setNomeVino(vinoEstratto.getNomeVino());
						vinoCompleto.setInfoVino(vinoEstratto.getInfoVino());
						vinoCompleto.setUrlLogoVino(vinoEstratto.getUrlLogoVino());
						vinoCompleto.setUvaggioVino(vinoEstratto.getUvaggioVino());
						vinoCompleto.setUrlLogoVino(vinoEstratto.getUrlLogoVino());
						vinoCompleto.setInBreveVino(vinoEstratto.getInBreveVino());
						viniCompletiAzienda.add(vinoCompleto);
					}
					
				}
			}
			azienda.setViniAzienda(viniCompletiAzienda);
			
		}
        
        risposta.setAzienda(azienda);
        risposta.setEsito(esito);
        return risposta;
    }
    
}
