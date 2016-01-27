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
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m
 */
public class SamajUtils {
    
    
    public HashMap<Long,Post> loadPostOfUser(User user ,int noOfPost){
        HashMap<Long,Post> posts = new HashMap<Long,Post>();
       
         String sql = "select post,id,imageid from post where username = '"+user.getName()+"' order by id desc limit "+noOfPost;
         IntrobSession session = new IntrobSession(user.getName());
         
         try{
             session.open();
             DataSet ds = session.query(sql);
             System.out.println("ds loadPostOfUser==>"+ds);
             for(int i = 0 ;i<ds.size();i++){
             Post post = new Post(user);
             post.setPost(ds.get(i).get("post").toString());
             post.setId(Long.valueOf(ds.get(i).get("id").toString()));
             Object imageids = ds.get(i).get("imageid");
             if(imageids!=null)
             post.setImageid(Long.valueOf(imageids.toString()));
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
    
    
    public Long postComments(String comment , Long postid,String username,Long userid){
        System.out.println("post Comments of SamajUtils");
         Long id = new Long(0);
       IntrobSession session = new IntrobSession(username);
        try{
             session.open();
            DataObject dataObj = new DataObject();
            dataObj.set("username", username);
            dataObj.set("comment", comment);
            dataObj.set("postid", postid);
            dataObj.set("userid", userid);
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
    
    
    public static String displayImage(Integer imageid,String outpath){
         
    String sql1 = "select imgoid,imgname,imgpath from images where id = "+imageid;
    DataSet ds =  SessioniUtils.query(sql1);
    Long date  = System.currentTimeMillis();
    if(ds!=null&&ds.size()>0){
           System.out.println("p1.SamajUtils.displayImage()"+ds);
           new File(outpath +    date.toString() ).mkdir();
           
           
        outpath = outpath +    date.toString() + "/"+ ds.get(0).get("imgname");
    String sql2 = "select lo_export("+ds.get(0).get("imgoid")+",'"+outpath+"')";
        System.out.println("p1.SamajUtils.displayImage()"+sql2);
        
    DataSet ds1 =  SessioniUtils.query(sql2);
        
     //   outpath = date.toString() + "/"+ ds.get(0).get("imgname");
        
        
            
        
    }else{
    
        System.out.println("p1.SamajUtils.displayImage() no image to display");
    }
    
    
    return outpath ;
    }
    
    public static  LinkedHashMap<Long, Comments> getALLCommentsOfPost(Long postid) {
        LinkedHashMap<Long, Comments> comments = new LinkedHashMap<Long, Comments>();

        String sql = "select id,postid,username,userid,fdatetime,comment from comment where postid = " + postid + " order by fdatetime";
        DataSet ds = SessioniUtils.query(sql);
        if (ds != null && ds.size() > 0) 
            for (int i = 0; i < ds.size(); i++) {
                DataObject dob = ds.get(i);
                Comments comment = new Comments();
                comment.setCommentText(dob.get("comment").toString());
                comment.setId(Long.valueOf(dob.get("id").toString()));
                comment.setPostid(Long.valueOf(dob.get("postid").toString()));
                comment.setUsername(dob.get("username").toString());
                comment.setUserid(Long.valueOf(dob.get("userid").toString()));
                comments.put(Long.valueOf(dob.get("id").toString()), comment);
            }
          return comments;
        }


    
    
    
    
 
    public static void addLike(Long userid){
    
    
    }
    
    public static Integer insertImage(String path, String imgname, String imgpath, String username) {
        System.out.println("p1.SamajUtils.insertImage()");
        Integer id = null;
        String oidsql = "select lo_import('" + path + "') as imgoid";
        DataSet ds = SessioniUtils.query(oidsql);
        IntrobSession session = new IntrobSession(username);
        try {
            session.open();
            DataObject dob = new DataObject();
            dob.set("imgoid", ds.get(0).get("imgoid"));
            dob.set("imgname", imgname);
            dob.set("imgpath", imgpath);
            session.execute(dob, "images", "id");
            System.out.println("p1.SamajUtils.insertImage()" + dob);
            id = Integer.valueOf(dob.get("id").toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return id;
    }


    
    
        public Long postText(String username , String sessionid , String post,Long userid,Long imageid){
            System.out.println("Post Text of SamajUtils");
        Long id = new Long(0);
        IntrobSession session = new IntrobSession(username);
         
        try {
        //    SessioniUtils.executeUpdate(sql);
        
              session.open();
            DataObject dataObj = new DataObject();
            dataObj.set("username", username);
            dataObj.set("sessionid", sessionid);
            if(imageid!=null)
            dataObj.set("imageid",imageid);
            dataObj.set("post", post);
            dataObj.set("userid", userid);
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
