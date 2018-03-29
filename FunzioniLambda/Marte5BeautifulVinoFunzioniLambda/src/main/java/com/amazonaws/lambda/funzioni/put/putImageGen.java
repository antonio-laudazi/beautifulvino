package com.amazonaws.lambda.funzioni.put;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;

public class putImageGen implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {
	
    @Override
    public RispostaPutGenerica handleRequest(RichiestaPutGenerica input, Context context) {
    	
        RispostaPutGenerica risposta = new RispostaPutGenerica();
        
        String idImmagine = FunzioniUtils.getEntitaId();
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        //logica per salvataggio file su S3
        String base64 = input.getBase64Image();
        
        //pulizia del base64
        String[] splitted = base64.split(",");
        if(splitted.length > 1) {
        		base64 = splitted[1];
        }
        String format = "";
        splitted = splitted[0].split("/");
        if (splitted.length > 1 ) {
        	format = splitted[1];
        }
        splitted = format.split(";");
        if (splitted.length > 1 ) {
        	format = splitted[0];
        }
        String tipoEntita = input.getTipoEntita();
        String filename =  idImmagine + "_" + input.getFilename();
        
        //controlli sui dati ricevuti
        String bucketName = getBucketName(tipoEntita);
        
        AmazonS3 client = null;
        
        try {
        
        		client = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
		} catch (Exception e1) {
			esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage ");
			esito.setTrace(e1.getMessage());
			risposta.setEsito(esito);
			return risposta;
		}
        if(client != null) {
			
			//creo un file dal base64 ricevuto
			byte[] data = Base64.decodeBase64(base64);
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			
			// write the image to a file
			
			BufferedImage image = null;
			try {
				image = ImageIO.read(bis);
				bis.close();
				
				File outputfile = File.createTempFile("temp", "temp");
				ImageIO.write(image, format, outputfile);
				
				//preparo la richiesta di put aggiungendo l'istruzione che rende pubblico il file
				PutObjectRequest request = new PutObjectRequest(bucketName, filename + "." + format, outputfile);
				request.setCannedAcl(CannedAccessControlList.PublicRead);
				client.putObject(request);
			} catch (IOException e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage ");
				esito.setTrace(e.getMessage());
				risposta.setEsito(esito);
				return risposta;
			} catch (Exception e) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage");
				esito.setTrace(e.getMessage());
				risposta.setEsito(esito);
				return risposta;
			} finally {
				
			}
	    }
        
        risposta.setEsito(esito);
        risposta.setImageUrl(FunzioniUtils.AMAZON_S3_BASE_URL + bucketName + "/" + filename);
        return risposta;
    }

	private String getBucketName(String tipoEntita) {
		
		return "beautifulvino-bucket-immagini";
	}
}
