package com.amazonaws.lambda.funzioni.utils;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.marte5.modello2.Azienda;
import com.marte5.modello2.Azienda.VinoAzienda;
import com.marte5.modello.Esito;
import com.marte5.modello2.Evento.VinoEvento;
import com.marte5.modello2.Provincia;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Utente.EventoUtente;
import com.marte5.modello2.Utente.VinoUtente;
import com.marte5.modello2.Vino;
import com.marte5.modello2.Vino.AziendaVino;

public class TestUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AmazonDynamoDB client = null;
        try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(client != null) {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			List<VinoEvento> viniEvento = new ArrayList<>();
			VinoEvento ve = new VinoEvento();
			ve.setIdVino("1513240133813");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1520606560866");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1513357582823");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1513240022473");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1523358835208");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1521568313260");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1523609840705");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1513241396871");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1523358587707");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1523358937956");
			viniEvento.add(ve);
			
			ve = new VinoEvento();
			ve.setIdVino("1523608760066");
			viniEvento.add(ve);
					
 			List<Azienda> aziende = FunzioniUtils.riordinaViniAzienda_Evento(viniEvento, mapper);
			System.out.println(aziende.size());
		}

	}

}