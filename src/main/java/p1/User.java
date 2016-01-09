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
    private String username ;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private String sessionId;
    private Long id ;

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
    
    public static User validate(String name ,String pwd){
        System.out.println("p1.User.validate().......");
        User user = null ;
        String sql = "select * from users where username = '"+name+"' and password = '"+pwd+"'";
        DataSet ds = SessioniUtils.query(sql);
        if(ds==null||ds.size()==0)
            return null;
         user = new User();
         user.setId(Long.valueOf(ds.get(0).get("id").toString()));
         user.setUsername(ds.get(0).get("username").toString());
         user.setName(ds.get(0).get("name").toString());
         return user ;
    }
    

    
    
}
