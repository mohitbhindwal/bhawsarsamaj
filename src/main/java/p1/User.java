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
    
    public Integer postText(String username , String sessionid , String post){
        Integer id = new Integer(0);
        String sql = "insert into post(username,sessionid,post) values ('"+username+"','"+sessionid+"','"+post+"')";
         IntrobSession session = new IntrobSession(username);
         
        try {
        //    SessioniUtils.executeUpdate(sql);
        
              session.open();
            DataObject dataObj = new DataObject();
            dataObj.set("username", username);
            dataObj.set("sessionid", sessionid);
            dataObj.set("post", post);
            System.out.println("Before"+dataObj);
            session.insert(dataObj, "post", "id");
                    System.out.println("After"+dataObj);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{try{
        if(session!=null)session.close();
        }catch(Exception e){e.printStackTrace();}
        }
        
        
        return id ;
        
    }
    
    
}
