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
         String sql = "select id,post,imageid,username,userid from post where username = '"+user.getName()+"' order by id desc limit "+noOfPost;
         IntrobSession session = new IntrobSession(user.getName());
         try{
             session.open();
             DataSet ds = session.query(sql);
             System.out.println("ds loadPostOfUser==>"+ds);
             for(int i = 0 ;i<ds.size();i++){
             Post post = new Post(user);
             post.setId(Long.valueOf(ds.get(i).get("id").toString()));
             post.setPost(ds.get(i).get("post").toString());
             post.setUsername(ds.get(i).get("username").toString());
             post.setUserid(Integer.valueOf(ds.get(i).get("userid").toString()));
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
    
    
    
    public static String getImagesrcfromID(Integer id){
        String outsrc = "/bhawsarsamaj/images/"+displayImage(id, "D:/ramout/");
        System.out.println("p1.SamajUtils.getImagesrcfromID()"+outsrc);
         return outsrc;
    }
    
    public static String displayImage(Integer imageid,String outpath){
         
    String sql1 = "select imgoid,imgname,imgpath from images where id = "+imageid;
    DataSet ds =  SessioniUtils.query(sql1);
    Long date  = System.currentTimeMillis();
    if(ds!=null&&ds.size()>0){
           System.out.println("p1.SamajUtils.displayImage()"+ds);
          // new File(outpath +    date.toString() ).mkdir();
           
           
        outpath = outpath + "/"+ date.toString()+"_"+ds.get(0).get("imgname");
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

      //  String sql = "select id,postid,username,userid,fdatetime,comment from comment where postid = " + postid + " order by fdatetime";
        String sql = "select c.id,c.postid,c.username,c.userid,c.fdatetime,c.comment,u.avtar from comment c , users u where postid = " + postid + " and c.userid = u.id order by fdatetime";
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
                comment.setCommentoravtarID(Integer.parseInt(dob.get("avtar").toString()));
                comments.put(Long.valueOf(dob.get("id").toString()), comment);
            }
          return comments;
        }

//{"title":"/bhawsarsamaj/images/D:/ramout/test.jpg","id":1213123123,"name":"222 bhindwal"}
    public static String getJSON(String searchby){
    StringBuffer json = new StringBuffer("[]");
    DataSet ds = SessioniUtils.query("select id,name,avtar from users where name ilike '%"+searchby+"%'");
    if(ds!=null&&ds.size()>0){
        json = new StringBuffer("");
        json.append("[");
        for (int i = 0; i< ds.size(); i++) {
            DataObject dob=ds.get(i);
            String title = "\"img/default_user.png\",";
            if(dob.get("avtar")!=null){
              String outpath = SamajUtils.displayImage(Integer.parseInt(dob.get("avtar").toString()),"D:/ramout/");
                title = "\"/bhawsarsamaj/images/"+outpath+"\",";
            }
            json.append("{\"title\":"+title);
            json.append("\"id\":"+Integer.parseInt(dob.get("id").toString())+",");
            json.append("\"name\":\""+dob.get("name").toString()+"\"},");
        }
      json = new StringBuffer(json.deleteCharAt(json.length()-1)+"]");
    }
    System.out.println("p1.SamajUtils.getJSON()######"+json.toString());
    return json.toString() ;
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
          //SessioniUtils.executeUpdate(sql);
        
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
