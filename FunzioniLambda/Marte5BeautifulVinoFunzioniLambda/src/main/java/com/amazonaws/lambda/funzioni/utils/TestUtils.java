package com.amazonaws.lambda.funzioni.utils;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.marte5.modello2.Vino;

public class TestUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestUtils test = new TestUtils();
		
		test.inserisciVini();
	}
	
	public void inserisciVini() {
		
		
		AmazonDynamoDB client = null;
		try {
			client = AmazonDynamoDBClientBuilder.standard().build();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(client != null) {
			
			Vino vino = new Vino();
			
			vino.setIdVino("1520606560866");
			vino.setAcquistabileVino(1);
			vino.setAnnoVino(2016);
			vino.setNomeVino("Continuum");
			vino.setPrezzoVino(10);
			vino.setRegioneVino("Toscana");
			vino.setUrlImmagineVino("https://s3.eu-central-1.amazonaws.com/beautifulvino-bucket-immagini/1523610039139_logoVinofile.jpeg");
			//prezzoVino (N)	regioneVino (S)	urlImmagineVino (S)	urlLogoVino (S)	uvaggioVino (S)	aziendaVino (M)	inBreveVino (S)	infoVino (S)	eventiVino (L)	oldIdAzienda (S)	utentiVino (L)

			
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			
			mapper.save(vino);
		}
		
		
	}
	
//	public void riordina() {
//		AmazonDynamoDB client = null;
//        try {
//			client = AmazonDynamoDBClientBuilder.standard().build();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		if(client != null) {
//			DynamoDBMapper mapper = new DynamoDBMapper(client);
//			
//			List<VinoEvento> viniEvento = new ArrayList<>();
//			VinoEvento ve = new VinoEvento();
//			ve.setIdVino("1513240133813");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1520606560866");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1513357582823");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1513240022473");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1523358835208");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1521568313260");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1523609840705");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1513241396871");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1523358587707");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1523358937956");
//			viniEvento.add(ve);
//			
//			ve = new VinoEvento();
//			ve.setIdVino("1523608760066");
//			viniEvento.add(ve);
//					
// 			List<Azienda> aziende = FunzioniUtils.riordinaViniAzienda_Evento(viniEvento, mapper);
//			System.out.println(aziende.size());
//		}
//	}

}