package com.amazonaws.lambda.funzioni.get.backup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Azienda;
import com.marte5.modello.Esito;
import com.marte5.modello.Evento;
import com.marte5.modello.Vino;
import com.marte5.modello.richieste.get.RichiestaGetAzienda;
import com.marte5.modello.risposte.get.RispostaGetAzienda;

public class getAzienda implements RequestHandler<RichiestaGetAzienda, RispostaGetAzienda> {

    @Override
    public RispostaGetAzienda handleRequest(RichiestaGetAzienda input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaGetAzienda risposta = getRisposta(input);
        //RispostaGetAzienda risposta = getRispostaDiTest(input);
        return risposta;
    }
    
    private RispostaGetAzienda getRisposta(RichiestaGetAzienda input) {
    		RispostaGetAzienda risposta = new RispostaGetAzienda();
        long idAzienda = input.getIdAzienda();
        
        Azienda azienda = new Azienda();
    		
        Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage("Richiesta Azienda gestit correttamente per l'azienda: " + input.getIdAzienda());
        
        AmazonDynamoDB client = null;
        try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEvento ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idAzienda == 0) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idAzienda nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			azienda = mapper.load(Azienda.class, idAzienda);
			
			List<Evento> eventiAzienda = azienda.getEventiAzienda();//ci sono solo gli id qui
			List<Evento> eventiCompletiAzienda = new ArrayList<>();
			for (Iterator<Evento> iterator = eventiAzienda.iterator(); iterator.hasNext();) {
				Evento evento = iterator.next();
				Evento eventoCompleto = new Evento();
				Evento eventoEstratto = mapper.load(Evento.class, evento.getIdEvento());
				
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
			azienda.setEventiAzienda(eventiCompletiAzienda);
			
			List<Vino> viniAzienda = azienda.getViniAzienda();
			List<Vino> viniCompletiAzienda = new ArrayList<>();
			for (Iterator<Vino> iterator = viniAzienda.iterator(); iterator.hasNext();) {
				Vino vino = iterator.next();
				Vino vinoCompleto = new Vino();
				Vino vinoEstratto = mapper.load(Vino.class, vino.getIdVino());
				
				vinoCompleto.setIdVino(vinoEstratto.getIdVino());
				vinoCompleto.setNomeVino(vinoEstratto.getNomeVino());
				vinoCompleto.setInfoVino(vinoEstratto.getInfoVino());
				vinoCompleto.setUrlLogoVino(vinoEstratto.getUrlLogoVino());
				
				viniCompletiAzienda.add(vinoCompleto);
			}
			azienda.setViniAzienda(viniCompletiAzienda);
			
		}
        
        risposta.setAzienda(azienda);
        risposta.setEsito(esito);
        return risposta;
    }
    
}
