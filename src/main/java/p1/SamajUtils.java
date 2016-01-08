/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import introb.DataObject;
import introb.DataSet;
import introb.IntrobSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m
 */
public class SamajUtils {
    
    
    public HashMap<Long,Post> loadPostOfUser(User user ,int noOfPost){
        HashMap<Long,Post> posts = new HashMap<Long,Post>();
       
         String sql = "select post,id from post where username = '"+user.getName()+"' order by id desc limit "+noOfPost;
         IntrobSession session = new IntrobSession(user.getName());
         
         try{
             session.open();
             DataSet ds = session.query(sql);
             System.out.println("ds loadPostOfUser==>"+ds);
             for(int i = 0 ;i<ds.size();i++){
             Post post = new Post(user);
             post.setPost(ds.get(i).get("post").toString());
             post.setId(Long.valueOf(ds.get(i).get("id").toString()));
             posts.put(post.getId(),post);
             }
         }catch(Exception e){
             e.printStackTrace();
         }finally{
         if(session!=null)try {
             session.close();
         } catch (SQLException ex) {
             ex.printStackTrace();
             Logger.getLogger(SamajUtils.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
        return posts ;
    }
    
    
    public Long postComments(String comment , Long postid,String username){
        System.out.println("post Comments of SamajUtils");
         Long id = new Long(0);
       IntrobSession session = new IntrobSession(username);
        try{
             session.open();
            DataObject dataObj = new DataObject();
            dataObj.set("username", username);
            dataObj.set("comment", comment);
            dataObj.set("postid", postid);
            System.out.println("Before"+dataObj);
            session.insert(dataObj, "comment", "id");
            System.out.println("After"+dataObj);
            id = dataObj.getLong("id");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{try{
        if(session!=null)session.close();
        }catch(Exception e){e.printStackTrace();}
        }
       return id ;
    }
    
        public Long postText(String username , String sessionid , String post){
            System.out.println("Post Text of SamajUtils");
        Long id = new Long(0);
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
            id = Long.valueOf(dataObj.get("id").toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{try{
        if(session!=null)session.close();
        }catch(Exception e){e.printStackTrace();}
        }
        
        
        return id ;
        
    }
}
