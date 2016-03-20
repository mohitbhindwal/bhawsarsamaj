/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import sun.applet.Main;

/**
 *
 * @author m
 */

public class Post {
    
    LinkedHashMap<Long,Comments> comments = new LinkedHashMap<Long,Comments>();
    LinkedHashMap<Long,String> likeby = new LinkedHashMap<Long,String>();
    
    
    
    private Long id ;
    private String post ;
    private Long imageid ;
    private User user ;
    private String username;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    private String avtarsrc;
    private Long userid ;
    private String url;
    private String creationDate;
    
    public   LinkedHashMap<Long,String> addAllLikeByOFPost(){
    likeby.putAll(SamajUtils.getaddAllLikeByOFPost(id,true));
    return likeby;
    }
    

    public String getAvtarsrc() {
        String src =  SamajUtils.getAvtarSrcFromUserID(userid);
        return src;
    }

    public void setAvtarsrc(String avtarsrc) {
        this.avtarsrc = avtarsrc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
    
  
  
    public Long getImageid() {
        return imageid;
    }

    public void setImageid(Long imageid) {
        this.imageid = imageid;
    }
  

    public Post() {
       
    }
    
    

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

   

    public Comments getComments(Long commentid) {
        return comments.get(commentid);
    }

    public void setComments(Long commentid , Comments comment ) {
        comments.put(commentid, comment);
    }
    
    public Comments addComments(String commenttext,Long userid,String username) {
        SamajUtils utils = new SamajUtils();
        System.out.println("p1.Post.addComments()"+id);
        Comments comment = utils.postComments(commenttext,id,username,userid,this);
        comments.put(comment.getId(), comment);
        return comment;
    }
    
    public   LinkedHashMap<Long,Comments> addAllDBComments(){
    comments.putAll(SamajUtils.getALLCommentsOfPost(id));
    return comments;
    }
    
  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Post(User user) {
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
   
    
}
