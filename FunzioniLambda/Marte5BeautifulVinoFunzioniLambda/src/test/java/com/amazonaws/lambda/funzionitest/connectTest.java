package com.amazonaws.lambda.funzionitest;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.connect.connectEventoAUtenteGen1;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.richieste.connect.RichiestaConnectGenerica;
import com.marte5.modello.risposte.connect.RispostaConnectGenerica;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class connectTest {

    private static RichiestaConnectGenerica input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new RichiestaConnectGenerica();
        
//        input.setIdEvento(1511197222754L);
//        input.setIdUtente(1511887612956L);
//        input.setStatoEvento("D");
//        input.setFunctionName("connectBadgeAUtenteGen");
//        input.setIdUtente("b6118563-1486-4448-8994-c121b60534ea");
//        
        input.setFunctionName("connectEventoAUtenteGen1");
        input.setIdUtente("10523a22-9404-453c-9df1-7ad6519c648d");
        input.setIdEvento("1537169878555");       
        input.setDataEvento(1541617200000l);
        input.setNumeroPartecipanti(1);
        input.setStatoAcquistatoEvento(1);
        input.setStatoPreferitoEvento(1);
        
//        {
//        	  "idEvento" : "1525255289722",
//        	  "functionName" : "connectEventoAUtenteGen1",
//        	  "statoPreferitoUtente" : 1,
//        	  "idUtente" : "eu-central-1:cd7292d1-a224-4e7a-af59-b1a5878c06ea",
//        	  "statoAcquistatoUtente" : 0,
//        	  "dataEvento" : 1528531200000,
//        	  "numeroPartecipanti" : 0
//        	}
//        input.setFunctionName("connectViniAUtenteGen");
//        input.setIdVino("1513240022473");
//        input.setIdUtente("eu-central-1:2b62862a-01d4-4a20-8651-ca8ab601972e");
//        input.setStatoVino("A");
        

//        List<Badge> badges = new ArrayList<>();
//        
////        Badge badge = new Badge();
////        badge.setIdBadge("1513700266196");
////        badges.add(badge);
//        
//        Badge badge2 = new Badge();
//        badge2.setIdBadge("1513700051679");
//        badges.add(badge2);
//        
        
//        input.setBadges(badges);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testdeleteEvento() {
        connectEventoAUtenteGen1 handler = new connectEventoAUtenteGen1();
        //BeautifulVinoConnect handler = new BeautifulVinoConnect();
        Context ctx = createContext();

        RispostaConnectGenerica output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
