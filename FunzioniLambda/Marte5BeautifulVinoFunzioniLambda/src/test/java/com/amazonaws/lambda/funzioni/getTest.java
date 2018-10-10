package com.amazonaws.lambda.funzioni;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.common.BeautifulVinoGet;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.Risposta;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class getTest {

    private static RichiestaGetGenerica input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new RichiestaGetGenerica();
        
//        input.setFunctionName("getUtenteGen");
//        input.setIdUtentePadre("eu-central-1:9ed038f2-edbd-4d68-a2de-ede25efe2892");
        input.setIdUtente("eu-central-1:9ed038f2-edbd-4d68-a2de-ede25efe2892");
        
          input.setFunctionName("getEventoGen");
          input.setIdEvento("1536663137862");
          input.setDataEvento(1538240400000l);
//        input.setFunctionName("getViniEventoGen");
//        input.setIdEvento(1513789129406L);
//        input.setDataEvento(1513724400000L);
        
//        input.setFunctionName("getUtenteGen");
//        input.setIdUtente("1522320091366");
//        input.setIdUtenteLog("1521197385816");
        
        /*	"functionName":"getViniEventoGen",
	"idEvento":"1513789129406",
	"dataEvento": 1513724400000*/
        
//        input.setFunctionName("getEventoGen");
//        input.setIdEvento("1525255289722");
//        input.setDataEvento(1528531200000L);
//        input.setIdUtente("eu-central-1:cd7292d1-a224-4e7a-af59-b1a5878c06ea");
        
//<<<<<<< HEAD
//        input.setFunctionName("getUtenteGen");
//        input.setIdUtente("eu-central-1:cd7292d1-a224-4e7a-af59-b1a5878c06ea");
        
//       input.setFunctionName("getEventiGen");
//  //    input.setIdUltimoEvento("1523609733534");
// //    input.setDataUltimoEvento(1524261600000L);
//       input.setIdUtente("eu-central-1:cd7292d1-a224-4e7a-af59-b1a5878c06ea");
//        input.setIdProvincia("X");
//        
        
        /*{"functionName":"getUtenteGen","idUtente":"1ba3b3c5-2b98-46c5-a670-c5eb25131a11","idUtentePadre":"1ba3b3c5-2b98-46c5-a670-c5eb25131a11"}*/
//=======
//        input.setFunctionName("getAziendaGen");
//        input.setIdAzienda("1520603900300");
//>>>>>>> branch 'master' of https://github.com/oloap1981/beautifulvino
        //input.setIdUtente("eu-central-1:2b62862a-01d4-4a20-8651-ca8ab601972e");
        
       
//      input.setFunctionName("getFeedGen");
//     input.setElencoCompleto("S");
//        input.setIdUtente("eu-central-1:3172e705-6ea1-420e-95f3-d93a11556228");
//        input.setDataUltimoFeed(1526921342336l);
//        input.setIdUltimoFeed("1526921342336");
//        input.setIdAzienda(1513241364518L);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testgetEventi() {
        BeautifulVinoGet handler = new BeautifulVinoGet();
        Context ctx = createContext();

        Risposta output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals(100, output.getEsito().getCodice());
    }
}
