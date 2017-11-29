package com.amazonaws.lambda.funzioni;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.connect.connectEventoAUtente;
import com.amazonaws.lambda.funzioni.delete.deleteEvento;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.richieste.connect.RichiestaConnectEventoAUtente;
import com.marte5.modello.risposte.connect.RispostaConnectEventoAUtente;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class connectEventoAUtenteTest {

    private static RichiestaConnectEventoAUtente input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new RichiestaConnectEventoAUtente();
        
        input.setIdEvento(1511197222754L);
        input.setIdUtente(1511887612956L);
        input.setStatoEvento("D");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testdeleteEvento() {
        connectEventoAUtente handler = new connectEventoAUtente();
        Context ctx = createContext();

        RispostaConnectEventoAUtente output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
