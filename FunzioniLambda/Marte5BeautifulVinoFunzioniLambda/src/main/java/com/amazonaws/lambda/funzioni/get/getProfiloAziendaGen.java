package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.regions.Regions;
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
import com.marte5.modello2.ProfiloAzienda;
import com.marte5.modello2.Vino;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class getProfiloAziendaGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
        context.getLogger().log("Input: " + input);
        RispostaGetGenerica risposta = getRisposta(input);
        //RispostaGetAzienda risposta = getRispostaDiTest(input);
        return risposta;
    }
    
    private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {
    		RispostaGetGenerica risposta = new RispostaGetGenerica();
        String idProfiloAzienda = input.getIdProfiloAzienda();
        
        ProfiloAzienda azienda = new ProfiloAzienda();
    		
        Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
        
        AmazonDynamoDB client = null;
        try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
			//client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).withCredentials(new ProfileCredentialsProvider("BeautifulVino")).build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getProfiloAzienda ");
			esito.setTrace(e1.getMessage());
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			if(idProfiloAzienda == null || idProfiloAzienda.equals("")) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
		        esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " idProfiloAzienda nullo, non posso procedere");
		        risposta.setEsito(esito);
		        return risposta;
			}
			
			azienda = mapper.load(ProfiloAzienda.class, idProfiloAzienda);
			
		}
        
        risposta.setProfiloAzienda(azienda);
        risposta.setEsito(esito);
        return risposta;
    }
    
}
