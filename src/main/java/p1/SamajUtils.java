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
import java.io.File;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
select p.id,p.post,p.imageid,p.username,p.userid,p.fdatetime,s.sharetime from post p 
left join share s on p.id=s.sharebyid


 * @author m
 */
public class SamajUtils {
    
    
    public HashMap<Long,Post> loadPostOfUser(User user ,int noOfPost){
        HashMap<Long,Post> posts = new HashMap<Long,Post>();
            
        String sql = 
                    
                    "select id,post,imageid,username,userid,fdatetime from post where userid = "+user.getId()+
                    
                    " or id in ( select sharepostid from share where sharebyid = "+user.getId() +" limit 10 ) order by fdatetime desc limit "+noOfPost ;
                        
            
         System.out.println("sql------->"+sql);       
            
            
         IntrobSession session = new IntrobSession(user.getName());
         try{
             session.open();
             DataSet ds = session.query(sql);
             System.out.println("ds loadPostOfUser==>"+ds);
             for(int i = 0 ;i<ds.size();i++){
             Post post = new Post();
             post.setId(Long.valueOf(ds.get(i).get("id").toString()));
             post.setPost(ds.get(i).get("post").toString());
             post.setCreationDate(Comments.sdf.format(ds.get(i).getDate("fdatetime")));
             post.setUsername(ds.get(i).get("username").toString());
             post.setUserid(Long.valueOf(ds.get(i).get("userid").toString()));
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
    
    
    public Comments postComments(String commenttext , Long postid,String username,Long userid,Post post) {
        System.out.println("post Comments of SamajUtils");
         Long id = new Long(0);
         Comments comment = null ;
       IntrobSession session = new IntrobSession(username);
        try{
             session.open();
            DataObject dataObj = new DataObject();
            dataObj.set("username", username);
            dataObj.set("comment", commenttext);
            dataObj.set("postid", postid);
            dataObj.set("userid", userid);
             try {
                dataObj.set("fdatetime",Comments.sdf.parse(Comments.sdf.format(new Date())));
             } catch (ParseException ex) {
                 Logger.getLogger(SamajUtils.class.getName()).log(Level.SEVERE, null, ex);
             }
            System.out.println("Before"+dataObj);
            session.insert(dataObj, "comment", "id");
            System.out.println("After"+dataObj);
            id = dataObj.getLong("id");
             comment = new Comments(post);
           comment.setCommentText(commenttext);
          
           comment.setCreationDate(Comments.sdf.format(dataObj.getDate("fdatetime")));
            System.out.println("p1.Post.addComments()"+id);
          } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{try{
        if(session!=null)session.close();
        }catch(Exception e){e.printStackTrace();}
        }
       return comment ;
    }
    
    
    public static Integer sharepost(Long sharebyid ,String sharebyname , Long sharefromid , String sharefromname,Integer postid){
    
     IntrobSession session = new IntrobSession();
     System.out.println("post Comments of SamajUtils");
         Integer id = new Integer(0);
   
        try{
            session.open();
            DataObject dataObj = new DataObject();
            dataObj.set("sharebyid", sharebyid);
            dataObj.set("sharebyname", sharebyname);
            dataObj.set("sharefromid", sharefromid);
            dataObj.set("sharefromname", sharefromname);
            dataObj.set("sharepostid", postid);
        
            System.out.println("Before"+dataObj);
            session.insert(dataObj, "share", "id");
            System.out.println("After"+dataObj);
            id = dataObj.getInteger("id");
            System.out.println("p1.SamajUtils.sharepost() = "+id);
          } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{try{
        if(session!=null)session.close();
        }catch(Exception e){e.printStackTrace();}
        }
       return id ;
    
    }
    
    public static String getAvtarSrcFromUserID(Long userid){
       return  getImagesrcfromID(Integer.parseInt(SessioniUtils.query("select avtar from users where id = "+userid).get(0).get("avtar").toString()));
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
                comment.setCreationDate(Comments.sdf.format(dob.getDate("fdatetime")));
                comment.setCommentoravtarID(Integer.parseInt(dob.get("avtar").toString()));
                comments.put(Long.valueOf(dob.get("id").toString()), comment);
            }
          return comments;
        }
     
          
    public static Integer registreuser(String firstname , String lastname , String gender,String email ,
             String password, String day,String month,String year,String myhour, String minute ,String seconds, 
             String birthplace , String myaddress)   
           {
               SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        System.out.println("Registreuser of SamajUtils");
        Integer id = null ;
        IntrobSession session = new IntrobSession();
        try {
            session.open();
            DataObject dataObj = new DataObject();
            dataObj.set("name", firstname+" "+lastname);
            //dataObj.set("lastname", lastname);
            dataObj.set("gender",gender);
            dataObj.set("password", password);
            dataObj.set("username", email);
                   try {
                       dataObj.set("dob", sdf.parse(day+"-"+month+"-"+year+" "+myhour+":"+minute+":"+seconds));
                   } catch (ParseException ex) {
                      ex.printStackTrace();
                   }
            dataObj.set("pob", birthplace);
            dataObj.set("address", myaddress);
            dataObj.set("password", password);
            System.out.println("Before"+dataObj);
            session.insert(dataObj, "users", "id");
            System.out.println("After"+dataObj);
            id = Integer.valueOf(dataObj.get("id").toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{try{
        if(session!=null)session.close();
        }catch(Exception e){e.printStackTrace();}
        }
        return id;
    }
    
    
       public static boolean isFriends(Long userid1 , Long userid2 ){
        System.out.println("p1.User.isFriends() for "+userid1+"  and "+userid2);
        String sql = "select friends from users where id = "+userid1;
        Connection con =  null;
        Statement smt = null;
        ResultSet rs = null;
        if(userid1==userid2)return true;
        try{
          con = ConnectionFactory.getConnection();
          smt = con.createStatement();
          rs = smt.executeQuery(sql);
          rs.next();
          Array array =  rs.getArray(1);
          if(array!=null){
          Object [] arrays = (Object [])array.getArray();
          if(arrays!=null)
          for(Object  id : arrays){
              System.out.println("p1.User.loadFriendsOFUser()aaa"+((String[])id)[0]);
               if( userid2.toString().equals(((String[])id)[0]))
                   return true ;
             }    
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
          return false ;
       }
    
    
    
    
 

//{"title":"/bhawsarsamaj/images/D:/ramout/test.jpg","id":1213123123,"name":"222 bhindwal"}
    public static String getJSON(String searchby,String contextpath){
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
                title = "\""+contextpath+"/images/"+outpath+"\",";
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
    
    public static void main(String[] args){
    
        getaddAllLikeByOFPost(2L,true);
    }
    
        public static LinkedHashMap<Long,String> getaddAllLikeByOFPost(Long postid,boolean frompost){
       LinkedHashMap<Long, String> likeby = new LinkedHashMap<Long, String>();
        String sql = "select likeby from "+ (frompost==true?"post":"comment") + " where   id ="+postid;
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
          
          if(arrays!=null)
          for(Object  id : arrays){
              System.out.println("p1.User.loadFriendsOFUser()aaa"+((String[])id)[0]);
                       
          }
          
               for (int i = 0; i < arrays.length/2; i++) {
                   Object id = arrays[i*2];
                   Object name = arrays[i*2+1];
                   likeby.put(Long.valueOf(((String[])id)[0]), ((String[])name)[0]);
               }
          
          
          
               System.out.println("p1.SamajUtils.getaddAllLikeByOFPost()llllllllll"+likeby);
          
            
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
        return likeby;
    }
  }
    public static void addLike(Long userid,String username , Long postid , boolean frompost){
    
        String sql = "update "+ (frompost==true?"post":"comment") + " set likeby = likeby || '{{"+userid+"},{"+username+"}}'  where id ="+postid;
        try {
            SessioniUtils.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(SamajUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     public static void addPendingRequest(String requestsendbyuserid,String requestsendbyusername 
              ,String requestsendtouserid){
        
          String sql = "update users set pendingrequest = pendingrequest || '{{"+requestsendbyuserid+"},{"+requestsendbyusername+"}}'  where id ="+requestsendtouserid;
        try {
            SessioniUtils.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(SamajUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
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


    
    
        public Post postText(String username , String sessionid , String posttext,Long userid,Long imageid , User user){
            System.out.println("Post Text of SamajUtils");
        Long id = new Long(0);
        IntrobSession session = new IntrobSession(username);
         Post post = null;
        try {
          //SessioniUtils.executeUpdate(sql);
        
              session.open();
            DataObject dataObj = new DataObject();
            dataObj.set("username", username);
            dataObj.set("sessionid", sessionid);
            if(imageid!=null)
            dataObj.set("imageid",imageid);
            dataObj.set("post", posttext);
            dataObj.set("userid", userid);  try {
            dataObj.set("fdatetime",Comments.sdf.parse(Comments.sdf.format(new Date())));
             } catch (ParseException ex) {
                 Logger.getLogger(SamajUtils.class.getName()).log(Level.SEVERE, null, ex);
             }
            
            System.out.println("Before"+dataObj);
            session.insert(dataObj, "post", "id");
            System.out.println("After"+dataObj);
            id = Long.valueOf(dataObj.get("id").toString());
            
            post = new Post(user);
            post.setId(id);
            post.setUsername(username);
            post.setCreationDate(Comments.sdf.format(dataObj.getDate("fdatetime")));
            post.setPost(posttext);
            post.setUserid(userid);
            post.setImageid(imageid);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{try{
        if(session!=null)session.close();
        }catch(Exception e){e.printStackTrace();}
        }
        
        
        return post ;
        
    }
}
