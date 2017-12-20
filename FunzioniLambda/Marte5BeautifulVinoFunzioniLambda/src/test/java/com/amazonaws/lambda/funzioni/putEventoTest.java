package com.amazonaws.lambda.funzioni;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.put.backup.putEvento;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.Azienda;
import com.marte5.modello.Evento;
import com.marte5.modello.Evento.AziendaEvento;
import com.marte5.modello.Evento.ProvinciaEvento;
import com.marte5.modello.richieste.put.RichiestaPutEvento;
import com.marte5.modello.risposte.put.RispostaPutEvento;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class putEventoTest {

    private static RichiestaPutEvento input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new RichiestaPutEvento();
        
        Evento evento = new Evento();
        evento.setDataEvento(1511342922747L);
        evento.setCittaEvento("a");
        evento.setTitoloEvento("e");
        evento.setTemaEvento("e");
        evento.setPrezzoEvento(10);
        evento.setTestoEvento("e");
        evento.setLatitudineEvento(1);
        evento.setLongitudineEvento(2);
        evento.setIndirizzoEvento("e");
        evento.setTelefonoEvento("e");
        evento.setEmailEvento("e");
        evento.setNumMaxPartecipantiEvento(10);
        
        ProvinciaEvento provincia = new ProvinciaEvento();
        provincia.setNomeProvincia("Livorno");
        provincia.setSiglaProvincia("LI");
        evento.setProvinciaEventoInt(provincia);
        
        Azienda aziendaFornitrice = new Azienda();
        aziendaFornitrice.setIdAzienda(1513240527034L);
        evento.setAziendaFornitriceEvento(aziendaFornitrice);
        
        Azienda aziendaOspitante = new Azienda();
        aziendaOspitante.setIdAzienda(1513240527034L);
        evento.setAziendaOspitanteEvento(aziendaOspitante);
        
        input.setEvento(evento);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testputEvento() {
        putEvento handler = new putEvento();
        Context ctx = createContext();

        RispostaPutEvento output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
