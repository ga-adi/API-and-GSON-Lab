package com.example.api_gson_lab;


import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;
/**
 * Created by Todo on 3/1/2016.
 */
public class TwoStepOAuth extends DefaultApi10a {
    @Override
    public String getAccessTokenEndpoint() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(Token arg0) {
        return null;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return null;
    }
}
