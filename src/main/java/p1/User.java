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
import java.util.HashMap;
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
    private Long avtaroid = null ;
    private Long pic1oid = null ;
    private Long pic2oid = null ;
    private Long pic3oid = null ;
    private String sessionId;
    private Long id ;
    private HashMap friendRequest = new HashMap();
    
    public User(){
    
    }
    
    public User(Long id){
    String sql = "select * from users where id = "+id;
        DataSet ds = SessioniUtils.query(sql);
        if(ds!=null&&ds.size()>0){
            for(int i = 0 ;i<ds.size();i++){
         setUsername(ds.get(0).get("username").toString());
         setName(ds.get(0).get("name").toString()); 
         setId(Long.valueOf(ds.get(0).get("id").toString()));
         setAvtaroid(Long.parseLong(ds.get(0).get("avtar").toString()));
         setPic1oid(Long.parseLong(ds.get(0).get("pic1").toString()));
         setPic2oid(Long.parseLong(ds.get(0).get("pic2").toString()));
         setPic3oid(Long.parseLong(ds.get(0).get("pic3").toString()));
        }
    }
}
    public Long getAvtaroid() {
        return avtaroid;
    }
    
    public String getAvtarsrc() {
        String src =  SamajUtils.getImagesrcfromID(getAvtaroid());
        return src;
    }


    public void setAvtaroid(Long avtaroid) {
        this.avtaroid = avtaroid;
    }
    

    public Long getPic1oid() {
        return pic1oid;
    }

    public void setPic1oid(Long pic1oid) {
        this.pic1oid = pic1oid;
    }

    public Long getPic2oid() {
        return pic2oid;
    }

    public void setPic2oid(Long pic2oid) {
        this.pic2oid = pic2oid;
    }

    public Long getPic3oid() {
        return pic3oid;
    }

    public void setPic3oid(Long pic3oid) {
        this.pic3oid = pic3oid;
    }
    
    
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
         user.setAvtaroid(Long.parseLong(ds.get(0).get("avtar").toString()));
         user.setPic1oid(Long.parseLong(ds.get(0).get("pic1").toString()));
         user.setPic2oid(Long.parseLong(ds.get(0).get("pic2").toString()));
         user.setPic3oid(Long.parseLong(ds.get(0).get("pic3").toString()));
 
         return user ;
    }
    
    public void loadFriendsOFUser(){
    
      String sql = "select * from users where id in (SELECT CASE WHEN requestsenttoid = "+ id +" THEN requestsentbyid WHEN requestsentbyid= " +id + "  THEN requestsenttoid END FROM friendrequest where status = 'accepted')";
      
      
      
      
      System.out.println("Executing sql"+sql);
        DataSet ds = SessioniUtils.query(sql);
           if(ds!=null&&ds.size()>0){
            for(int i = 0 ;i<ds.size();i++){
            User user = new User();
            user.setId(Long.valueOf(ds.get(i).get("id").toString()));
            user.setUsername(ds.get(i).get("username").toString());
            user.setName(ds.get(i).get("name").toString());
            user.setAvtaroid(Long.parseLong(ds.get(i).get("avtar").toString()));
            friends.put(user.getId(),user);
            }
        }
           ds = null;
    }
    
    
 /*   public void loadFriendsOFUser(){
        System.out.println("p1.User.loadFriendsOFUser()");
        String sql = "select friends,pendingrequest from users where id = "+id;
        Connection con =  null;
        Statement smt = null;
        ResultSet rs = null;
        try{
          con = ConnectionFactory.getConnection();
          smt = con.createStatement();
          rs = smt.executeQuery(sql);
          rs.next();
          Array array =  rs.getArray(1);
          Array friendrequest =  rs.getArray(2);
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
            user.setAvtaroid(Long.parseLong(ds.get(i).get("avtar").toString()));
            friends.put(user.getId(),user);
            }
        } 
     }
              System.out.println("p1.User.loadFriendsOFUser()friends"+friends);
 }
          if(friendrequest!=null){
          
           Object [] farrays = (Object [])friendrequest.getArray();
          
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
    }*/
    
      public void loadFriendsRequestOFUser(){
          DataSet ds =  SessioniUtils.query("select  * from friendrequest where requestsenttoid = "+id + " and status = 'pending'");
           for (int i = 0; i <  ds.size(); i++) {
              DataObject dob = ds.get(i);
              
              
              friendRequest.put(dob.get("id")+"_"+dob.get("requestsentbyid") + "#" +SamajUtils.getAvtarSrcFromUserID(Long.valueOf(dob.get("requestsentbyid").toString())) , dob.get("requestsentbyname"));
          }
      }

    public HashMap getFriendRequestMap(){
    return friendRequest;
    }
    
}
