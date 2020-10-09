package com.amazonaws.lambda.funzioni.get;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Utente;

public class getEventiGen implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

	@Override
	public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
		context.getLogger().log("Input: " + input);

		RispostaGetGenerica risposta = getRisposta(input);
		return risposta;
	}

	private RispostaGetGenerica getRisposta(RichiestaGetGenerica input) {

		// controllo del token
		RispostaGetGenerica risposta = new RispostaGetGenerica();
		String idUltimoEvento = input.getIdUltimoEvento();
		long dataUltimoEvento = input.getDataUltimoEvento();
		String idUtente = input.getIdUtente();

		String elencoCompleto = input.getElencoCompleto();

		Esito esito = FunzioniUtils.getEsitoPositivo();
		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());

		// scan del database per estrarre tutti gli eventi (per ora, poi da filtrare)
		AmazonDynamoDB client = null;
		int scannedCount = 0;
		try {
			client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
			esito.setMessage(
					this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET + " getEventi ");
			esito.setTrace(e1.getMessage());
			esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
		if (client != null) {
			//DynamoDB dynamoDB = new DynamoDB(client);
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression expr = new DynamoDBScanExpression();
			// expr.withIndexName("orarioEvento-dataEvento-index");
			//DynamoDBQueryExpression<Evento> qexpr = new DynamoDBQueryExpression<Evento>();
			
			Utente utente  = null;
			if (idUtente!= null && !idUtente.equals("") )utente = mapper.load(Utente.class, idUtente);

//			scannedCount = mapper.count(Evento.class, expr);
//			if (!(elencoCompleto != null && elencoCompleto.equalsIgnoreCase("S"))) {
//				//expr.withLimit(12);
//				//qexpr.withLimit(12);
//				
//			}

//			if ((idUltimoEvento != "" || !idUltimoEvento.equals(null)) && dataUltimoEvento != 0) {
				
//				Table table = dynamoDB.getTable("BVino_Evento");
//				Index index = table.getIndex("orarioEvento-dataEvento-index");
//			
//				QuerySpec spec = new QuerySpec()
//						.withKeyConditionExpression("orarioEvento = :v_id and dataEvento = :v_dt")
//						.withValueMap(new ValueMap()
//								.withString(":v_id", "a")
//								.withNumber(":v_dt", dataUltimoEvento));

//				QuerySpec spec = new QuerySpec().withKeyConditionExpression("orarioEvento = :v_id and #d = :v_dt").withNameMap(new NameMap().with("#i", "orarioEvento").with("#d", "dataEvento"))
//						.withValueMap(new ValueMap().withString(":v_id", "a").withLong(":v_dt", dataUltimoEvento));
			            
//				ItemCollection<QueryOutcome> items = index.query(spec);
//				
//				Iterator<Item> iterator = items.iterator();
//		        while (iterator.hasNext()) {
//		            System.out.println(iterator.next().toJSONPretty());
//		        }
//				exprq = QuerySpec();
//				mapper.query(clazz, queryExpression)
//				//configuro la paginazione
//				Map<String, AttributeValue> attribute = new HashMap<>();
//				AttributeValue av1 = new AttributeValue();
//				av1.setS(dataUltimoEvento);
//				
//				attribute.put("dataEvento", av1);
//				expr.set(attribute);
//				
//				String filter = "idEvento = " + "id";
//				expr.set(filter);
//
//				Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
//				AttributeValue av1 = new AttributeValue();
//				av1.setS("a");
//				AttributeValue av2 = new AttributeValue();
//				av2.setN("" + dataUltimoEvento);
//				exclusiveStartKey.put("orarioEvento", av1);
//				exclusiveStartKey.put("dataEvento", av2);
//				
//				expr.setExclusiveStartKey(exclusiveStartKey);
//				qexpr.setExclusiveStartKey(exclusiveStartKey);
				
//			}
			// ottengo la 'pagina'
			//QueryResultPage<Evento> qpage = mapper.queryPage(Evento.class, qexpr);
			//recupero tutti gli eventi
			ScanResultPage<Evento> page = mapper.scanPage(Evento.class, expr);
			List<Evento> eventiTot = new ArrayList<>();
			eventiTot = page.getResults();
			List<Evento> eventi = new ArrayList<>();
			
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			if (c.get(Calendar.HOUR_OF_DAY) < 8) {
				c.add(Calendar.DATE, -1);
			}
			c.set(Calendar.HOUR_OF_DAY, 8);
			long time = c.getTimeInMillis();
			
			long timeMetaAprile = 1586901600L;
			
			//long time = Calendar.getInstance().getTimeInMillis();
			
			//elimino eventi vecchi, quelli non nella provincia e quelli non pubblicati
			if (utente != null) {
				String s = input.getIdProvincia();
				if (s == null)s = "X";
				if (s.equals("0"))s = "X";
				for (Evento e : eventiTot) {
					String s1 = "";
					if (e.getProvinciaEventoInt()!= null) {
						s1 = e.getProvinciaEventoInt().getIdProvincia();
					}
					if (s1 == null) s1 = "";
					if ((e.getDataEvento() >= time || e.getDataEvento() >= timeMetaAprile) && (s.equals("X") || s1.equals(s)) && e.getPubblicatoEvento()) {
						eventi.add(e);
					}
				}
			}else {
				eventi = eventiTot;
			}
			scannedCount = eventi.size();
			//riordino gli eventi per data
			Collections.sort(eventi, new Comparator<Evento>(){
				@Override
				public int compare(Evento arg0, Evento arg1) {
					long d0 = arg0.getDataEvento();
					long d1 = arg1.getDataEvento();
					return (Long.compare(d0, d1));
				}
		      });
			//ne invio solo 13 dalla data ultimo evento
			if (eventi != null && (!(elencoCompleto != null && elencoCompleto.equalsIgnoreCase("S"))) ) {
				if ((idUltimoEvento != "" || !idUltimoEvento.equals(null)) && dataUltimoEvento != 0) {
					for (Evento e : eventi) {
						System.out.println(e.getIdEvento());
						System.out.println(e.getDataEvento());
						if (e.getIdEvento().equals(idUltimoEvento)) {
							int ide = eventi.indexOf(e);
							List<Evento> temp = new ArrayList<Evento>();
							for (int i = ide + 1 ; i < ide + 13 ; i++) {
								if (i >= eventi.size()) break;
								if (eventi.get(i) != null) {
									temp.add(eventi.get(i));
								}
							}
							eventi = temp;
						}
					}
				}else {
					List<Evento> temp = new ArrayList<Evento>();
					for (int i = 0; i < 12 ; i++) {
						if (i >= eventi.size()) break;
						if (eventi.get(i) != null) {
							temp.add(eventi.get(i));
						}
					}
					eventi = temp;
				}
			}
			//aggiungo la relazione fra l'evento e l'utente loggato
			if (eventi != null && utente != null) {
				for (Evento evento : eventi) {
					String statoEvento = FunzioniUtils.EVENTO_STATO_NEUTRO;
					try {
						statoEvento = FunzioniUtils.getStatoEvento(utente, evento, evento.getDataEvento(),
								mapper);
					} catch (Exception e) {
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
						esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET
								+ " getEventi: errore nell'estrazione delle associazioni degli eventi preferiti");
						esito.setTrace(e.getMessage());
						esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
						risposta.setEsito(esito);
						return risposta;
					}
					evento.setStatoEvento(statoEvento);
					try {
						statoEvento = FunzioniUtils.getStatoEventoPreferito(utente, evento, evento.getDataEvento(),
								mapper);
					} catch (Exception e) {
						esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_GET);
						esito.setMessage(this.getClass().getName() + " - " + EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_GET
								+ " getEventi: errore nell'estrazione delle associazioni degli eventi preferiti");
						esito.setTrace(e.getMessage());
						esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
						risposta.setEsito(esito);
						return risposta;
					}
					evento.setStatoPreferitoEvento(statoEvento);
				}
			}
			risposta.setEventi(eventi);
		}

		risposta.setNumTotEventi(scannedCount);
		esito.setMessage(this.getClass().getName() + " - " + esito.getMessage());
		risposta.setEsito(esito);
		return risposta;
	}
}
