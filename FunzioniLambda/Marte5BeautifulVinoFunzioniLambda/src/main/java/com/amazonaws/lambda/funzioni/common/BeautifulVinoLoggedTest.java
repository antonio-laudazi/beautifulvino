package com.amazonaws.lambda.funzioni.common;

import com.amazonaws.lambda.funzioni.utils.FunzioniUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.richieste.get.RichiestaGetGenerica;
import com.marte5.modello.risposte.get.RispostaGetGenerica;

public class BeautifulVinoLoggedTest implements RequestHandler<RichiestaGetGenerica, RispostaGetGenerica> {

    @Override
    public RispostaGetGenerica handleRequest(RichiestaGetGenerica input, Context context) {
    	
        RispostaGetGenerica risposta = new RispostaGetGenerica();
        Esito esito = FunzioniUtils.getEsitoPositivo();
        esito.setMessage(input.toString());
        risposta.setEsito(esito);
        
        return risposta;
    }
}
