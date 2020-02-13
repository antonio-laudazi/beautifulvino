package com.amazonaws.lambda.funzioni;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.common.BeautifulVinoGet;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class getAziendaTest {

    private static RichiestaGetGenerica input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
    	input = new RichiestaGetGenerica();
		input.setFunctionName("getAziendeGen");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testgetAzienda() {
    	BeautifulVinoGet handler = new BeautifulVinoGet();
        Context ctx = createContext();

        RispostaGetGenerica output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
