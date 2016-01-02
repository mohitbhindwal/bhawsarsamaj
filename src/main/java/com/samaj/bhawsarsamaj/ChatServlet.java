/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samaj.bhawsarsamaj;

import java.io.IOException;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;

/**
 *
 * @author m
 */
@AtmosphereHandlerService(path="/chat")
public class ChatServlet implements AtmosphereHandler{

    @Override
    public void onRequest(AtmosphereResource ar) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStateChange(AtmosphereResourceEvent are) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
 
    
}
/*
Once an AtmosphereResource gets suspended, you can initiate server push (or broadcast) at any moment. 
For example, the following code will broadcast every 10 seconds to all AtmosphereResource associated 
with the /* broadcaster:*/