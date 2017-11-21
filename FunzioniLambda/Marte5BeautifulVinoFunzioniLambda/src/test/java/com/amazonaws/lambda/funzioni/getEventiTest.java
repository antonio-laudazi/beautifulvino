package com.amazonaws.lambda.funzioni;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.get.getEventi;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.richieste.get.RichiestaGetEventi;
import com.marte5.modello.risposte.get.RispostaGetEventi;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class getEventiTest {

    private static RichiestaGetEventi input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new RichiestaGetEventi();
        input.setIdProvincia(2211);
        input.setNumEventiVisualizzati(10);
        input.setIdUtente(1510937294371L);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testgetEventi() {
        getEventi handler = new getEventi();
        Context ctx = createContext();

        RispostaGetEventi output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output.getEventi().size());
    }
}
