package com.amazonaws.lambda.funzioni;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.common.BeautifulVinoDelete;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.richieste.delete.RichiestaDeleteGenerica;
import com.marte5.modello.risposte.Risposta;

public class delTest {
	 private static RichiestaDeleteGenerica input;

	    @BeforeClass
	    public static void createInput() throws IOException {
	        // TODO: set up your sample input object here.
	        input = new RichiestaDeleteGenerica();

	        input.setFunctionName("deleteEventoGen");
	        input.setIdEvento("1522315486942");
	        input.setDataEvento(1520809200000L);
	    }

	    private Context createContext() {
	        TestContext ctx = new TestContext();

	        // TODO: customize your context here if needed.
	        ctx.setFunctionName("Your Function Name");

	        return ctx;
	    }

	    @Test
	    public void testgetEventi() {
	        BeautifulVinoDelete handler = new BeautifulVinoDelete();
	        Context ctx = createContext();

	        Risposta output = handler.handleRequest(input, ctx);

	        // TODO: validate output here if needed.
	        Assert.assertEquals(100, output.getEsito().getCodice());
	    }
}
