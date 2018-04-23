package com.amazonaws.lambda.funzioni.put;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.lambda.funzioni.utils.EsitoHelper;
import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;

public class putImageGen implements RequestHandler<RichiestaPutGenerica, RispostaPutGenerica> {
	
	public static final String IMAGE_TYPE_JPG = "jpg";
	public static final String IMAGE_TYPE_JPEG = "jpeg";
	public static final String IMAGE_TYPE_PNG = "png";
	
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
        
        if(!isImageTypeAccepted(format)) {
        		esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
			esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage - Tipo di immagine " + format + " non supportato. Sono accettati solo jpg, jpeg e png");
			risposta.setEsito(esito);
			return risposta;
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
			int imageSize = data.length;
			float compressionQuality = getCompressionQuality(imageSize);
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			File outputfile;
			try {
				outputfile = File.createTempFile("temp", "temp");
			} catch (IOException e1) {
				esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
				esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage ");
				esito.setTrace(e1.getMessage());
				risposta.setEsito(esito);
				return risposta;
			}
			
			if (format.equalsIgnoreCase("png")) {
				try {				
					PngImage image = new PngImage(bis);
					bis.close();
					// optimize
					PngOptimizer optimizer = new PngOptimizer();
					PngImage optimizedImage = optimizer.optimize(image, true, new Integer(9));
					// export the optimized image to a new file
					ByteArrayOutputStream optimizedBytes = new ByteArrayOutputStream();
					optimizedImage.writeDataOutputStream(optimizedBytes);
					
					ByteArrayInputStream bais = new ByteArrayInputStream(optimizedBytes.toByteArray());
					optimizedBytes.close();
					BufferedImage imout = ImageIO.read(bais);
			        bais.close();
			        System.out.println("dimensione " + imout.getWidth() + " " + imout.getHeight() );
			        if (imout.getWidth() > imout.getHeight()) {
					    if(imout.getWidth() > 1100) {
					    	float w = getYResizeFactor(imout.getWidth(), 1100) * imout.getHeight();
					    	int wi = Math.round(w);
					    	imout = resize(imout, wi , 1100);
					    }
				    }else {
				    	if(imout.getHeight() > 1100) {
					    	float w = getYResizeFactor(imout.getHeight(), 1100) * imout.getWidth();
					    	int wi = Math.round(w);
					    	imout = resize(imout, 1100 , wi);
					    }
				    }
			        System.out.println("dimensione " + imout.getWidth() + " " + imout.getHeight() );
			        ImageIO.write(imout, format, outputfile);
			    } catch (IOException e) {
			    	esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage ");
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
			    }catch(Exception e1){
			    	esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage ");
					esito.setTrace(e1.getMessage());
					risposta.setEsito(esito);
					return risposta;
			    }
				
			}else {
				//l'immagine è un jpg
				try {
				BufferedImage image = null;
				image = ImageIO.read(bis);
				System.out.println("dimensione " + image.getWidth() + " " + image.getHeight() );
				Iterator<ImageWriter>writers = ImageIO.getImageWritersByFormatName(format);
				ImageWriter writer = writers.next();
				bis.close();
				
				ImageWriteParam param = writer.getDefaultWriteParam();
				param.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );
				param.setCompressionQuality(compressionQuality);
				final ImageOutputStream imgOutStream = ImageIO.createImageOutputStream(new FileOutputStream(outputfile));
			    try {
			        writer.setOutput(imgOutStream);
			        writer.write(null, new IIOImage(image, null, null), param);
			    } finally {
			        imgOutStream.close();
			    }
			    System.out.println("dimensione " + image.getWidth() + " " + image.getHeight() );
			    if (image.getWidth() > image.getHeight()) {
				    if(image.getWidth() > 1100) {
				    	float w = getYResizeFactor(image.getWidth(), 1100) * image.getHeight();
				    	int wi = Math.round(w);
				    	image = resize(image, wi , 1100);
				    }
			    }else {
			    	if(image.getHeight() > 1100) {
				    	float w = getYResizeFactor(image.getHeight(), 1100) * image.getWidth();
				    	int wi = Math.round(w);
				    	image = resize(image, 1100 , wi);
				    }
			    }
			    System.out.println("dimensione " + image.getWidth() + " " + image.getHeight() );
				ImageIO.write(image, format, outputfile);	
				}catch (Exception e) {
					esito.setCodice(EsitoHelper.ESITO_KO_CODICE_ERRORE_SALVATAGGIO);
					esito.setMessage(EsitoHelper.ESITO_KO_MESSAGGIO_ERRORE_PROCEDURA_LAMBDA + " putImage ");
					esito.setTrace(e.getMessage());
					risposta.setEsito(esito);
					return risposta;
				}
	    }
		//preparo la richiesta di put aggiungendo l'istruzione che rende pubblico il file
		PutObjectRequest request = new PutObjectRequest(bucketName, filename + "." + format, outputfile);
		request.setCannedAcl(CannedAccessControlList.PublicRead);
		client.putObject(request);
        }
        risposta.setEsito(esito);
        risposta.setImageUrl(FunzioniUtils.AMAZON_S3_BASE_URL + bucketName + "/" + filename + "." + format);
        return risposta;
    }

	private String getBucketName(String tipoEntita) {
		
		return "beautifulvino-bucket-immagini";
	}
	
	private float getCompressionQuality(int imageSize) {
		if(imageSize < 100000) {//immagini piu' piccole di 50 kb
			return 1f;
		} else if(imageSize >= 100000 && imageSize < 500000) {
			return 0.8f;
		} else if(imageSize >= 500000 && imageSize < 1000000) {
			return 0.6f;
		} else if(imageSize >= 1000000 && imageSize < 2000000) {
			return 0.5f;
		} else if(imageSize >= 2000000) {
			return 0.4f;
		}
		
		return 0.4f;
	}
	
	private boolean isImageTypeAccepted(String imageType) {
		ArrayList<String> tipi = new ArrayList<>();
		
		tipi.add(IMAGE_TYPE_JPEG);
		tipi.add(IMAGE_TYPE_JPG);
		tipi.add(IMAGE_TYPE_PNG);
		
		return tipi.contains(imageType.toLowerCase());
	}
	
	private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
	}

	
	private float getYResizeFactor(float x, float max) {
		return (max / x);
	}
}
