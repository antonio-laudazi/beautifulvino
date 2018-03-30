package com.amazonaws.lambda.funzioni;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.put.backup.putEvento;
import com.amazonaws.lambda.funzioni.put.backup.putImage;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.richieste.put.RichiestaPutEvento;
import com.marte5.modello.richieste.put.RichiestaPutImage;
import com.marte5.modello.risposte.put.RispostaPutEvento;
import com.marte5.modello.risposte.put.RispostaPutImage;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class putImmagineTest {

    private static RichiestaPutImage input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
    	String base64Image = "";
    	
        input = new RichiestaPutImage();
        input.setFilename("testfilename.jpg");
        input.setTipoEntita("");
        input.setBase64Image(base64Image);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testputEvento() {
        putImage handler = new putImage();
        Context ctx = createContext();

        RispostaPutImage output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
