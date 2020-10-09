package com.amazonaws.lambda.funzionitest;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.common.BeautifulVinoPeriodicEvent;
import com.amazonaws.lambda.funzionitest.common.TestContext;
import com.amazonaws.services.lambda.runtime.Context;

public class BeautifulVinoPeriodicEventTest {
	 private static Map<String, Object> input;

	    @BeforeClass
	    public static void createInput() throws IOException {
	        // TODO: set up your sample input object here.
	        
	    }

	    private Context createContext() {
	        TestContext ctx = new TestContext();

	        // TODO: customize your context here if needed.
	        ctx.setFunctionName("Your Function Name");

	        return ctx;
	    }

	    @Test
	    public void testgetEventi() {
	        BeautifulVinoPeriodicEvent handler = new BeautifulVinoPeriodicEvent();
	        Context ctx = createContext();

	        String output = handler.handleRequest(input, ctx);

	        Assert.assertEquals(100, output);
	    }
}
