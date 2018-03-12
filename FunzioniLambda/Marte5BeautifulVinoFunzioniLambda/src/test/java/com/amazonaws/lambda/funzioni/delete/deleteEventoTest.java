package com.amazonaws.lambda.funzioni.delete;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.delete.backup.deleteEvento;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.richieste.delete.RichiestaDeleteEvento;
import com.marte5.modello.risposte.delete.RispostaDeleteEvento;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class deleteEventoTest {

    private static RichiestaDeleteEvento input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new RichiestaDeleteEvento();
        
        input.setDataEvento(1513724400000L);
        input.setIdEvento(1513784891703L);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testdeleteUtente() {
        deleteEvento handler = new deleteEvento();
        Context ctx = createContext();

        RispostaDeleteEvento output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
