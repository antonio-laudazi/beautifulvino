package com.amazonaws.lambda.funzioni;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.common.BeautifulVinoPut;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutGenerica;
import com.marte5.modello2.Azienda;
import com.marte5.modello2.Evento;
import com.marte5.modello2.Evento.ProvinciaEvento;
import com.marte5.modello2.Evento.VinoEvento;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class putTest {

    private static RichiestaPutGenerica input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new RichiestaPutGenerica();
        
        input.setFunctionName("putEventoGen");
        Evento evento = new Evento();
        evento.setDataEvento(1511342922747L);
        evento.setCittaEvento("a");
        evento.setTitoloEvento("provaK2");
        evento.setTemaEvento("e");
        evento.setPrezzoEvento(10);
        evento.setTestoEvento("e");
        evento.setLatitudineEvento(1);
        evento.setLongitudineEvento(2);
        evento.setIndirizzoEvento("e");
        evento.setTelefonoEvento("e");
        evento.setEmailEvento("e");
        evento.setNumMaxPartecipantiEvento(10);
        evento.setOrarioEvento("5");
        ProvinciaEvento provincia = new ProvinciaEvento();
        provincia.setNomeProvincia("Livorno");
        provincia.setSiglaProvincia("LI");
        provincia.setIdProvincia("1513783245483");
        evento.setProvinciaEventoInt(provincia);

        
        Azienda aziendaOspitante = new Azienda();
        aziendaOspitante.setIdAzienda("1522056860834");
        evento.setAziendaOspitanteEvento(aziendaOspitante);
        
        
        VinoEvento vinoEvento = new VinoEvento();
        vinoEvento.setIdVino("1520606560866");
        List<VinoEvento> listaVini = new ArrayList<VinoEvento>();
        listaVini.add(vinoEvento);
        evento.setViniEventoInt(listaVini);
        input.setEvento(evento);
      
//        Provincia provincia = new Provincia();
//        
//        provincia.setNomeProvincia("Livorno");
//        provincia.setSiglaProvincia("LI");
//     
//        input.setProvincia(provincia);
//        
//        
//        Utente utente = new Utente();
//        //utente.setEmailUtente("aversionn@msn.com");
//        utente.setCittaUtente("Cecina, Toscana, Italy");
//        utente.setIdUtente("eu-central-1:2b62862a-01d4-4a20-8651-ca8axxxxxxxx");
//        utente.setBiografiaUtente("");
//        utente.setCognomeUtente("Maria");
//        utente.setNomeUtente("Concetta");
//        utente.setUsernameUtente("Concetta Maria");
//        input.setUtente(utente);
//        
//        Vino vino = new Vino();
//        
//        vino.setAnnoVino(2);
//        vino.setDescrizioneVino("AAAAAAAAAAAAAAAAA");
//        
//        Azienda azienda = new Azienda();
//        azienda.setIdAzienda("1522056860834");
//        vino.setAziendaVino(azienda);
//        vino.setInBreveVino("SSSSSSSSS");
//        input.setFunctionName("putVinoGen");
//        input.setVino(vino);
//          input.setFilename("testImagefile");
//		  input.setTipoEntita("");
//		  input.setFunctionName("putImageGen"); 
//        Feed feed = new Feed();
//        
//        feed.setTestoFeed("prova long");
//        feed.setTitoloFeed("provaLong");
//        feed.setDataFeed(0L);
//        feed.setTipoFeed(2);
//        AziendaFeed af = new AziendaFeed();
//        af.setIdAzienda("1522143359728");
//        af.setCittaAzienda("ciao");
//        af.setNomeAzienda("provaF");
//        af.setActive(true);
//        feed.setAziendaFeedInt(af);
//        input.setFunctionName("putFeedGen"); 
//        input.setFeed(feed);
//        

//	      Azienda azienda = new Azienda();
//	      azienda.setNomeAzienda("provaihh");
//	      azienda.setCittaAzienda("qwerty");
//	      azienda.setActive(true);
//	      input.setFunctionName("putAziendaGen");
//	      input.setAzienda(azienda);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testputEvento() {
        BeautifulVinoPut handler = new BeautifulVinoPut();
        Context ctx = createContext();

        RispostaPutGenerica output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
