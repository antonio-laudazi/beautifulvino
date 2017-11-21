package com.amazonaws.lambda.funzioni.get;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.marte5.modello.Esito;
import com.marte5.modello.Token;
import com.marte5.modello.richieste.get.RichiestaGetToken;
import com.marte5.modello.risposte.get.RispostaGetToken;

public class getToken implements RequestHandler<RichiestaGetToken, RispostaGetToken> {

    @Override
    public RispostaGetToken handleRequest(RichiestaGetToken input, Context context) {
        context.getLogger().log("Input: " + input);
        
        RispostaGetToken risposta = new RispostaGetToken();

        Esito esito = new Esito();
        esito.setCodice(100);
        esito.setMessage("Richiesta token andata a buon fine per l'utente: " + input.getIdUtente());
        
        Token token = new Token();
        token.setToken("1a2b3c4d5e");
        
        risposta.setEsito(esito);
        risposta.setToken(token);
        
        // TODO: implement your handler
        return risposta;
    }

}
