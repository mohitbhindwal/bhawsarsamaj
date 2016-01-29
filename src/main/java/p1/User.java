/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import introb.ConnectionFactory;
import introb.DataObject;
import introb.DataSet;
import introb.IntrobSession;
import introb.SessioniUtils;
import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
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
    private LinkedHashMap<Long,User>  friends = new LinkedHashMap<Long,User>();

    public LinkedHashMap<Long, User> getFriends() {
        return friends;
    }

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
    
    public void loadFriendsOFUser(){
        System.out.println("p1.User.loadFriendsOFUser()");
        String sql = "select friends from users where id = "+id;
        Connection con =  null;
        Statement smt = null;
        ResultSet rs = null;
        try{
          con = ConnectionFactory.getConnection();
          smt = con.createStatement();
          rs = smt.executeQuery(sql);
          rs.next();
          Array array =  rs.getArray(1);
          if(array!=null){
          Object [] arrays = (Object [])array.getArray();
          String ids = "(";
          if(arrays!=null)
          for(Object  id : arrays){
              System.out.println("p1.User.loadFriendsOFUser()aaa"+((String[])id)[0]);
              ids = ids + ((String[])id)[0] + ",";             
          }
          if(ids.length()>1){
          String sql1 = "select * from users where id in "+ids.substring(0, ids.length()-1)+") order by created_dt desc";
          System.out.println("p1.User.loadFriendsOFUser()sql1"+sql1);
          DataSet ds = SessioniUtils.query(sql1);
        if(ds!=null&&ds.size()>0){
            for(int i = 0 ;i<ds.size();i++){
            User user = new User();
            user.setId(Long.valueOf(ds.get(i).get("id").toString()));
            user.setUsername(ds.get(i).get("username").toString());
            user.setName(ds.get(i).get("name").toString());
            friends.put(user.getId(),user);
            }
        } 
     }
              System.out.println("p1.User.loadFriendsOFUser()friends"+friends);
            }
        } catch (Exception e) {
            System.out.println("p1.User.loadFriendsOFUser()" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (smt != null) {
                    smt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    

    
    
}
