package com.amazonaws.lambda.funzioni.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.transactions.Transaction;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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

public class FunzioniUtils {
	
	public static final String DATE_FORMAT = "dd MMM yyyy";
	public static final Locale FUNCTIONS_LOCALE = Locale.ITALY;
	
	public static final String EVENTO_STATO_NEUTRO = "N";
	public static final String EVENTO_STATO_ACQUISTATO = "A";
	public static final String EVENTO_STATO_PREFERITO = "P";
	public static final String EVENTO_STATO_CANCELLATO = "D";
	
	public static final String VINO_STATO_NEUTRO = "N";
	public static final String VINO_STATO_ACQUISTATO = "A";
	public static final String VINO_STATO_PREFERITO = "P";
	public static final String VINO_STATO_CANCELLATO = "D";
	
	public static final String AMAZON_S3_BASE_URL = "https://s3.eu-central-1.amazonaws.com/";
	
	public static String getStringVersion(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, FUNCTIONS_LOCALE);
		return sdf.format(date);
	}
	
	public static String getEntitaId() {
		Date actualDate = new Date();
        return actualDate.getTime()  +"";
	}
	
	public static Esito getEsitoPositivo() {
		Esito esito = new Esito();
        esito.setCodice(EsitoHelper.ESITO_OK_CODICE);
        esito.setMessage(EsitoHelper.ESITO_OK_MESSAGGIO);
        return esito;
	}
	
	public static String getStatoEvento(Utente utente, String idEvento, long dataEvento, DynamoDBMapper mapper) throws Exception {
		String statoEvento = FunzioniUtils.EVENTO_STATO_NEUTRO;
		if(utente == null) {
			throw new Exception("Utente null, non posso procedere");
		}
		List<EventoUtente> eventiUtente = utente.getEventiUtenteInt();
		if(eventiUtente != null) {
			for (Iterator<EventoUtente> iterator = eventiUtente.iterator(); iterator.hasNext();) {
				EventoUtente eventoUtente = iterator.next();
				if(eventoUtente.getIdEvento().equals(idEvento)) {
					//l'evento in questione è tra quelli associati all'utente
					statoEvento = eventoUtente.getStatoEvento();
				}
			}
		}
		
		return statoEvento;
	}
	
	public static Esito cancellaImmagine(String urlImmagine) {
		
		AmazonS3 client = null;
		Esito esito = FunzioniUtils.getEsitoPositivo();
        
		URL url = null;
		try {
			url = new URL(urlImmagine);
		} catch (MalformedURLException e) {
			esito.setCodice(EsitoHelper.ESITO_WARN_CODICE);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_WARNING_CANCELLAZIONE + " deleteImage ");
			esito.setTrace(e.getMessage());
		}
		String path = url.getPath();
		String[] split = path.split("/");
		String bucket = split[0];
		String key = split[1];
		
        try {
        		client = AmazonS3ClientBuilder.standard().build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_WARN_CODICE);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_WARNING_CANCELLAZIONE + " deleteImage ");
			esito.setTrace(e1.getMessage());
			
		}
        if(client != null) {
        		try {
					client.deleteObject(new DeleteObjectRequest(bucket, key));
				} catch (AmazonServiceException e) {
					esito.setCodice(EsitoHelper.ESITO_WARN_CODICE);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_WARNING_CANCELLAZIONE + " deleteImage ");
					esito.setTrace(e.getMessage());
				} catch (SdkClientException e) {
					esito.setCodice(EsitoHelper.ESITO_WARN_CODICE);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_WARNING_CANCELLAZIONE + " deleteImage ");
					esito.setTrace(e.getMessage());
				}
        }
		
		return esito;
	}
	
	public static String aggiungiProvincia(Provincia provincia, DynamoDBMapper mapper) {
		
		String idProvincia = provincia.getIdProvincia();
		if(idProvincia == null || idProvincia.equals("")) {
			//devo inserire la provincia sul DB
			idProvincia = FunzioniUtils.getEntitaId();
			provincia.setIdProvincia(idProvincia);
			
		}
		mapper.save(provincia);
		return idProvincia;
	}
	
	public static String aggiungiProvincia(Provincia provincia, Transaction transaction) {
		
		String idProvincia = provincia.getIdProvincia();
		if(idProvincia == null || idProvincia.equals("")) {
			//devo inserire la provincia sul DB
			idProvincia = FunzioniUtils.getEntitaId();
			provincia.setIdProvincia(idProvincia);
			
		}
		transaction.save(provincia);
		return idProvincia;
	}
	
	//da testare
	public static List<Azienda> riordinaViniAzienda(List<Vino> vini){
		List<Azienda> aziende1 = new ArrayList<>();
		List<Azienda> aziende2 = new ArrayList<>();
		
		for (Iterator<Vino> iterator = vini.iterator(); iterator.hasNext();) {
			Vino vino = iterator.next();
			AziendaVino aziendaVino = vino.getAziendaVinoInt();
			
			Azienda nuovaAzienda = new Azienda();
			nuovaAzienda.setIdAzienda(aziendaVino.getIdAzienda());
			nuovaAzienda.setNomeAzienda(aziendaVino.getNomeAzienda());
			aziende1.add(nuovaAzienda);
		}
		
		for (Iterator<Azienda> iterator = aziende1.iterator(); iterator.hasNext();) {
			Azienda azienda = iterator.next();
			List<Vino> viniAziendaNuova = new ArrayList<>();
			for (Iterator<Vino> iterator2 = vini.iterator(); iterator2.hasNext();) {
				Vino vino = iterator2.next();
				if(vino.getAziendaVinoInt().getIdAzienda().equals(azienda.getIdAzienda())) {
					viniAziendaNuova.add(vino);
				}
			}
			azienda.setViniAzienda(viniAziendaNuova);
			aziende2.add(azienda);
		}
		return aziende2;
	}
	
	public static List<Azienda> riordinaViniAzienda(List<VinoEvento> vini, boolean x){
		List<Azienda> aziende1 = new ArrayList<>();
		List<Azienda> aziende2 = new ArrayList<>();
		
		for (Iterator<VinoEvento> iterator = vini.iterator(); iterator.hasNext();) {
			VinoEvento vino = iterator.next();
			
			Azienda nuovaAzienda = new Azienda();
			nuovaAzienda.setIdAzienda(vino.getIdAziendaVino());
			nuovaAzienda.setNomeAzienda(vino.getNomeAziendaVino());
			aziende1.add(nuovaAzienda);
		}
		
		for (Iterator<Azienda> iterator = aziende1.iterator(); iterator.hasNext();) {
			Azienda azienda = iterator.next();
			List<Vino> viniAziendaNuova = new ArrayList<>();
			for (Iterator<VinoEvento> iterator2 = vini.iterator(); iterator2.hasNext();) {
				VinoEvento vino = iterator2.next();
				Vino vinoNuovo = new Vino();
				if(vino != null && vino.getIdAziendaVino() != null) {
					if(azienda != null && azienda.getIdAzienda() != null) {
						if(vino.getIdAziendaVino().equals(azienda.getIdAzienda())) {
							vinoNuovo.setIdVino(vino.getIdVino());
							vinoNuovo.setNomeVino(vino.getNomeVino());
							viniAziendaNuova.add(vinoNuovo);
						}
					}
				}
			}
			azienda.setViniAzienda(viniAziendaNuova);
			aziende2.add(azienda);
		}
		return aziende2;
	}
	
	public static List<Azienda> riordinaViniAzienda_Utente(List<VinoUtente> vini, DynamoDBMapper mapper){
		List<Azienda> aziende1 = new ArrayList<>();
		List<Azienda> aziende2 = new ArrayList<>();
		
		for (Iterator<VinoUtente> iterator = vini.iterator(); iterator.hasNext();) {
			VinoUtente vinoUtente = iterator.next();
			
			Vino vino = mapper.load(Vino.class, vinoUtente.getIdVino());
			
			if(vino != null) {
				Azienda nuovaAzienda = new Azienda();
				nuovaAzienda.setIdAzienda(vino.getAziendaVinoInt().getIdAzienda());
				nuovaAzienda.setNomeAzienda(vino.getAziendaVinoInt().getNomeAzienda());
				aziende1.add(nuovaAzienda);
			}
			
		}
		
		for (Iterator<Azienda> iterator = aziende1.iterator(); iterator.hasNext();) {
			Azienda azienda = iterator.next();
			List<Vino> viniAziendaNuova = new ArrayList<>();
			for (Iterator<VinoUtente> iterator2 = vini.iterator(); iterator2.hasNext();) {
				VinoUtente vino = iterator2.next();
				String idAzienda = getIdAziendaFromVino(vino.getIdVino(), mapper);
				if(!idAzienda.equals("")) {
					Vino vinoNuovo = new Vino();
					if(idAzienda.equals(azienda.getIdAzienda())) {
						vinoNuovo.setIdVino(vino.getIdVino());
						vinoNuovo.setNomeVino(vino.getNomeVino());
						vinoNuovo.setStatoVino(vino.getStatoVino());
						vinoNuovo.setUvaggioVino(vino.getUvaggioVino());
						vinoNuovo.setInBreveVino(vino.getInBreveVino());
						vinoNuovo.setUrlLogoVino(vino.getUrlLogoVino());
						viniAziendaNuova.add(vinoNuovo);
					}
				}
				
			}
			azienda.setViniAzienda(viniAziendaNuova);
			aziende2.add(azienda);
		}
		return aziende2;
	}
	
	public static List<Azienda> riordinaViniAzienda_Azienda(List<VinoAzienda> vini, DynamoDBMapper mapper){
		List<Azienda> aziende1 = new ArrayList<>();
		List<Azienda> aziende2 = new ArrayList<>();
		
		for (Iterator<VinoAzienda> iterator = vini.iterator(); iterator.hasNext();) {
			VinoAzienda vinoUtente = iterator.next();
			
			Vino vino = mapper.load(Vino.class, vinoUtente.getIdVino());
			
			if(vino != null) {
				Azienda nuovaAzienda = new Azienda();
				nuovaAzienda.setIdAzienda(vino.getAziendaVinoInt().getIdAzienda());
				nuovaAzienda.setNomeAzienda(vino.getAziendaVinoInt().getNomeAzienda());
				aziende1.add(nuovaAzienda);
			}
			
		}
		
		for (Iterator<Azienda> iterator = aziende1.iterator(); iterator.hasNext();) {
			Azienda azienda = iterator.next();
			List<Vino> viniAziendaNuova = new ArrayList<>();
			for (Iterator<VinoAzienda> iterator2 = vini.iterator(); iterator2.hasNext();) {
				VinoAzienda vino = iterator2.next();
				String idAzienda = getIdAziendaFromVino(vino.getIdVino(), mapper);
				if(!idAzienda.equals("")) {
					Vino vinoNuovo = new Vino();
					if(idAzienda.equals(azienda.getIdAzienda())) {
						vinoNuovo.setIdVino(vino.getIdVino());
						vinoNuovo.setNomeVino(vino.getNomeVino());
						vinoNuovo.setStatoVino(vino.getStatoVino());
						vinoNuovo.setUvaggioVino(vino.getUvaggioVino());
						vinoNuovo.setInBreveVino(vino.getInBreveVino());
						vinoNuovo.setUrlLogoVino(vino.getUrlLogoVino());
						viniAziendaNuova.add(vinoNuovo);
					}
				}
				
			}
			azienda.setViniAzienda(viniAziendaNuova);
			aziende2.add(azienda);
		}
		return aziende2;
	}
	
	public static List<Azienda> riordinaViniAzienda_Evento(List<VinoEvento> vini, DynamoDBMapper mapper){
		List<Azienda> aziende1 = new ArrayList<>();
		List<Azienda> aziende2 = new ArrayList<>();
		
		for (Iterator<VinoEvento> iterator = vini.iterator(); iterator.hasNext();) {
			VinoEvento vinoUtente = iterator.next();
			
			Vino vino = mapper.load(Vino.class, vinoUtente.getIdVino());
			
			if(vino != null) {
				Azienda nuovaAzienda = new Azienda();
				nuovaAzienda.setIdAzienda(vino.getAziendaVinoInt().getIdAzienda());
				nuovaAzienda.setNomeAzienda(vino.getAziendaVinoInt().getNomeAzienda());
				aziende1.add(nuovaAzienda);
			}
			
		}
		
		for (Iterator<Azienda> iterator = aziende1.iterator(); iterator.hasNext();) {
			Azienda azienda = iterator.next();
			List<Vino> viniAziendaNuova = new ArrayList<>();
			for (Iterator<VinoEvento> iterator2 = vini.iterator(); iterator2.hasNext();) {
				VinoEvento vino = iterator2.next();
				String idAzienda = getIdAziendaFromVino(vino.getIdVino(), mapper);
				if(!idAzienda.equals("")) {
					Vino vinoNuovo = new Vino();
					if(idAzienda.equals(azienda.getIdAzienda())) {
						vinoNuovo.setIdVino(vino.getIdVino());
						vinoNuovo.setNomeVino(vino.getNomeVino());
						vinoNuovo.setStatoVino(vino.getStatoVino());
						vinoNuovo.setUvaggioVino(vino.getUvaggioVino());
						vinoNuovo.setInBreveVino(vino.getInBreveVino());
						vinoNuovo.setUrlLogoVino(vino.getUrlLogoVino());
						viniAziendaNuova.add(vinoNuovo);
					}
				}
				
			}
			azienda.setViniAzienda(viniAziendaNuova);
			aziende2.add(azienda);
		}
		return aziende2;
	}
	
	public static String getIdAziendaFromVino(String idVino, DynamoDBMapper mapper) {
		Vino vino = mapper.load(Vino.class, idVino);
		if(vino != null) {
			AziendaVino az = vino.getAziendaVinoInt();
			if(az != null) {
				return az.getIdAzienda();
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
}
