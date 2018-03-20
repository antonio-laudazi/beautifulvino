package com.amazonaws.lambda.funzioni;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.funzioni.common.BeautifulVinoPut;
import com.amazonaws.lambda.funzioni.put.backup.putEvento;
import com.amazonaws.services.lambda.runtime.Context;
import com.marte5.modello.Azienda;
import com.marte5.modello.Evento;
import com.marte5.modello.Provincia;
import com.marte5.modello.Evento.ProvinciaEvento;
import com.marte5.modello.richieste.put.RichiestaPutEvento;
import com.marte5.modello.richieste.put.RichiestaPutGenerica;
import com.marte5.modello.risposte.put.RispostaPutEvento;
import com.marte5.modello.risposte.put.RispostaPutGenerica;
import com.marte5.modello2.Feed;
import com.marte5.modello2.Utente;
import com.marte5.modello2.Vino;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class putTest {

    private static RichiestaPutGenerica input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new RichiestaPutGenerica();
        
//        Evento evento = new Evento();
//        evento.setDataEvento(1511342922747L);
//        evento.setCittaEvento("a");
//        evento.setTitoloEvento("e");
//        evento.setTemaEvento("e");
//        evento.setPrezzoEvento(10);
//        evento.setTestoEvento("e");
//        evento.setLatitudineEvento(1);
//        evento.setLongitudineEvento(2);
//        evento.setIndirizzoEvento("e");
//        evento.setTelefonoEvento("e");
//        evento.setEmailEvento("e");
//        evento.setNumMaxPartecipantiEvento(10);
//        
//        ProvinciaEvento provincia = new ProvinciaEvento();
//        provincia.setNomeProvincia("Livorno");
//        provincia.setSiglaProvincia("LI");
//        evento.setProvinciaEventoInt(provincia);
//        
//        Azienda aziendaFornitrice = new Azienda();
//        aziendaFornitrice.setIdAzienda(1513240527034L);
//        evento.setAziendaFornitriceEvento(aziendaFornitrice);
//        
//        Azienda aziendaOspitante = new Azienda();
//        aziendaOspitante.setIdAzienda(1513240527034L);
//        evento.setAziendaOspitanteEvento(aziendaOspitante);
        
//        Provincia provincia = new Provincia();
//        
//        provincia.setNomeProvincia("Livorno");
//        provincia.setSiglaProvincia("LI");
//        
//        input.setProvincia(provincia);
//        
//        input.setBase64Image("\\/9j\\/4AAQSkZJRgABAQAASABIAAD\\/4QBYRXhpZgAATU0AKgAAAAgAAgESAAMAAAABAAEAAIdpAAQAAAABAAAAJgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAAAyKADAAQAAAABAAAAyAAAAAD\\/7QA4UGhvdG9zaG9wIDMuMAA4QklNBAQAAAAAAAA4QklNBCUAAAAAABDUHYzZjwCyBOmACZjs+EJ+\\/8AAEQgAyADIAwEiAAIRAQMRAf\\/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC\\/\\/EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29\\/j5+v\\/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC\\/\\/EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29\\/j5+v\\/bAEMAHBwcHBwcMBwcMEQwMDBEXERERERcdFxcXFxcdIx0dHR0dHSMjIyMjIyMjKioqKioqMTExMTE3Nzc3Nzc3Nzc3P\\/bAEMBIiQkODQ4YDQ0YOacgJzm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5ubm5v\\/dAAQADf\\/aAAwDAQACEQMRAD8Ay6KWimMSkpaKQCUUUUAFFFFABSUtKFJoASipkUZweasrabjnGKLjsUPwp21x2rZFtGO1Na1HUdaVwsZOAeBwfQ03GKty2rqd\\/Ud6c8O9f9odD6ii4WKVFP2k8d\\/502mISilpKACiiimAUUUUAFFFFAH\\/0MyiiimMKSlpKACkpaSgAzRRRSAKnVcD61HGu5sVdRMu3sMUmwJLaIbQ5rQVaYigDAqcCkUJilxT8UYpiuQlc1VZCjZHQ1fIqJ1ypFS0UmZkkQJIH1H1qrImPm9f51puPlDDtUMq5WlFiZmUUp60laEhSUtFMBKKKKAFpKKSkB\\/\\/0cyjNFGKYxKKMUlABRRRSAKKKUCgC3bLyWPbmrUQ4+pqugxHj+8f0q5H14\\/hGKljRbXAqUGolRcfNyaDGByvFAycUtQqx6GpCaZNgNRmlLY61Bvdj8tJspITGVZagI+XmrK5z83rUZXqtQBivwSPQ0ypZR+8aoq1RIUUUlMBaSiigAoopKQH\\/9LNopaMUxjabT6SgBtFFLQAlOHWkp6DLYoAu42qB9BU8AJVj6k0x1wufSrFqv7sVmihrTlW2KMmmJdu7hdpBPoc1c8v5twHNCwIrbgMGqAaGLc1MT8uaXAA6Uh+7ikBV3FslulMe7EfGDx6CrQVWXBFRTW6ytvbrQkDY2GdZWOODgGpXGH+tVgmyYEDGRirco6GkJmVMnzt+dUT1rVmHzVlyDDGnEBlFFFUIKKKSgAoopKAP\\/\\/TzqWinYpjGGkNOxSEUAMpaKWgAqeBcyD61CKvWq\\/PSYFp1+RvpS2pBjGKfIcDFQWny5XtmoRRpCnU0Ggn0qiRhPOKG+7UE06xNtPNNe7QJSuXYmjOeKmqjbTea3HYVezQhMgkUcH0qRuUBpr9Kd1j\\/CgTKE\\/HNZ0y4Oa05hkCqUy5U0k9QKNJTqStBCUUUUgEooooEf\\/UoAU\\/FAHNPxTAjIppHFSkUxhxQBFilpcUuOKBiAZIFalqvNZyDnNa9uuAKTAWT75HtVG2kJkZPxrQYZdvpWLG+y5ye\\/FQijfU5FPqKM9qcyBu5H0pgPIB+9UBtoCSxHWmeUAfmYn8aDEp\\/jOPSmOxOqIgwgAFKTVHYwOI3I+vNXEQqvzHNIGRu3zAepqwoyv51UHzTDHarg6UIllRhmq0i8GrmOPxqN15xUoDEYYNNxU8i4P41Ea1ER4pKkIphFACUUUUgP\\/VrKOadilUU7FMQwio2FSmmMKAIsUYp2KMUDHxrkitiEYrMiGXHtWtEOBUsBnUsa52UYkNdJj5GP1rAuFw2alFGlaTiRBn7y9a0K5hCyEOpwela0N1xh6Y1qaRUHg1H5Mf90UizIRwafvFMNRAqr0FNdsKailuEQcmsqa7Z+F4pAaduwMhA7CrwrJ0wEh2PcitYUEkGODTXHNSdj9aawoAy7hcMT61UIrRuV4BqkRVoRDimkVMRUZFAyOinEUmKAP\\/1o0HFOxSqOKdimIjxTGFTYqNhzQBDijFPxxSAZNAFiAfrxWovC\\/hVGFfmA9KvMcRk1LGhP8AlkfpWROgP5VrtxHWXcDGD7Gp6lIzR98KemauGMpwagRNzA\\/WtlUWRAD6UMpaFBaRsgcE1YaBlPSonRh1FIZnPktzTQCTgVbWBnagII5Tn+GquS0alogjj2irYqraksm41ZHakIb6ihhxS45NDdKAKU4ypHpg1RxWlIPmx6qao4q0SyIioyKsYphFMCuRTcVMRSbaBn\\/\\/13AcClxSiimITFRNSvNGhwx59BUfmxkZ3CgAxT1xuGccVXadAPl5NQbmYM5PtQM24ACcjn3qxL90L71UshiMn1NW25dRUSGgc\\/IPrWZdHGB\\/sn+daTfdGaz7sZxjsDSGVIDwc9Qa1IW4+lZKfI+OxrQjbB\\/nSZSNGmkA0inilqhDNqryKy5cAnHUmtOU4WsZgZJcDoKQGraf6sVOTzTIQFjwO1KDmkxElB6UxjyaXPymi4FGeXZcKmOoqh9o9RVq4+a8X0GB+tZjYB4OatCLQuEPGCPrTtwNUc+1PWTbweRTFYtkUlKpDDI5FOxTA\\/\\/QkUhh6Y6io55PLjJHU8CopJ\\/Lww5YjBH9aoO7SNuY5NMBlFLSUALT1+5j1NM6VJ0QH3NIZvW4xGtT9ZM1FB\\/qx9KmH3jUsBkvBX61TkGXx7Vck5dfrVdxlz9MVLGjOdcMpHap42y2KRxwDUXKuGH0pblGyn3RUmKpW1wJD5Z4YdvWrtWiWVZ+TtFVI4wDmrbsnnbM5OOfamjA69qllEhbanNNQ5AzUEj7mx6U5W6VDYWJHbmjdwB6mq8j\\/NSeZ09gSaAIZmxMZD2Jx+FZmKuXB+VR3Iyap1stiGFLjjIoFPRgrZ\\/Me1MAjkMbZ7d6sfaE\\/u014VLEKQD296Z9negR\\/9HJJz1ooopgFFFFAxcU\\/qOO3SmqAetSIcNuNAG\\/CMRiph1qG3\\/1Q\\/OpxUAROfmFROOTT25c\\/QUOOtQUVGGPyqJ0wA3vVh8c08pmMfUUkMzHIjuFfOACD+FWJtQZhtgG0ep61Ddpyp9qpVqiS3CQpMrE\\/wCNXC5K1QjGduemST+FWYmzGT7mlJAgByT71IGwM+tV8gcUZ4zWdixJJMk1Hk4Cnq38qbjJyafGNz726VdiWRTndKQO3FRAU8\\/Mc05Qo6sB9OaskBEScnge9SNCsY3Px9ev5UpnWMDyF+b+83J\\/D0qqzEnJOT6mgCRnyAVpm5qD90fjTM0Af\\/\\/SyaKKKYwooooAUHmpogWYZqEVMrYXjqen0pAb0JygqeoYxtRV9qlBqBjCvOaY33Rn6VN1FRHgYPTvSYFZ+n41YIzHmoJARVhSCmPakhsz7pfkJ9CD\\/Ss3IrXlw2FPfj8\\/\\/r1ikEEg9q1RJPnCE+wFTRHC81XUb8L681YTk4pSGgKk5PSlkXy1xVpUBODVe75bioKIY0L\\/ACjua1DAqw8dQM\\/pUVtHgK3pVwnFK4mc5jJxTTzU8q7HYe+KhxlsVqiQfsB2FNAzTjyc9qQnPApgITk0lFFAH\\/\\/TyaSlpKYxaSnU00AKOtTgjzBjtgVAOtTD\\/WfjSA6Duv0p\\/T8KZ6fSnHqfwrNjHZprDI460vpTT1FDAi6fIw4pR8o45GKJOooX7tIZSnJxx2qncLlhIP4xmrk\\/Q1Wm\\/wBVD9K0RJHF3b2wKlthuc\\/TP61FF901Paf60\\/7tDBGoi4JJqpInmAH3zV31+hqsPu\\/hWbKRKoCqKjmfB4p\\/YfjVebqPwqSjOlOTmoBUz9ahrdEMCc0lFFMQlFLRQI\\/\\/2Q==");
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
//        vino.setAnnoVino(2015);
//        vino.setDescrizioneVino("AAAAAAAAAAAAAAAAA");
//        
//        com.marte5.modello2.Azienda azienda = new com.marte5.modello2.Azienda();
//        azienda.setIdAzienda("1520603900300");
//        azienda.setNomeAzienda("Tenuta la Macchia");
//        vino.setAziendaVino(azienda);
//        vino.setInBreveVino("SSSSSSSSS");
//        
//        input.setVino(vino);
        
        Feed feed = new Feed();
        
        feed.setTestoFeed("prova long");
        feed.setTitoloFeed("provaLong");
        feed.setDataFeed(0L);
        
        input.setFeed(feed);
        
        input.setFunctionName("putFeedGen");
        
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
