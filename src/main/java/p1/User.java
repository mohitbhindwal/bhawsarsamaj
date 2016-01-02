/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import introb.DataObject;
import introb.DataSet;
import introb.IntrobSession;
import introb.SessioniUtils;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m
 */
public class User implements Serializable{
    
    private String name ;
    private String pwd ;
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public boolean validate(){
        System.out.println("p1.User.validate().......");
    boolean isValid = false ;
        String sql = "select username from profile where username = '"+name+"' and password = '"+pwd+"'";
         DataSet ds = SessioniUtils.query(sql);
         if(ds==null||ds.size()==0)
             return false ;
         return true;
    }
    

    
    
}
