package com.amazonaws.lambda.funzioni.put.backup;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.put.RichiestaPutImage;
import com.marte5.modello.risposte.put.RispostaPutImage;

public class putImage implements RequestHandler<RichiestaPutImage, RispostaPutImage> {
	
    @Override
    public RispostaPutImage handleRequest(RichiestaPutImage input, Context context) {
    	
        context.getLogger().log("Input: " + input);
        RispostaPutImage risposta = new RispostaPutImage();
        
        long idImmagine = FunzioniUtils.getEntitaId();
        Esito esito = FunzioniUtils.getEsitoPositivo(); //inizializzo l'esito a POSITIVO. In caso di problemi sovrascrivo
        
        //logica per salvataggio file su S3
        String base64 = input.getBase64Image();
        
        //pulizia del base64
        String[] splitted = base64.split(",");
        if(splitted.length > 1) {
        		base64 = splitted[1];
        }
        
        String tipoEntita = input.getTipoEntita();
        String filename =  idImmagine + "_" + input.getFilename();
        
        //controlli sui dati ricevuti
        String bucketName = getBucketName(tipoEntita);
        
        AmazonS3 client = null;
        
        try {
        		client = AmazonS3ClientBuilder.standard().build();
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
				ImageIO.write(image, "jpg", outputfile);
				
				//preparo la richiesta di put aggiungendo l'istruzione che rende pubblico il file
				PutObjectRequest request = new PutObjectRequest(bucketName, filename, outputfile);
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
