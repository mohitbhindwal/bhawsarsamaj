/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author m
 */

public class Post {
    
    LinkedHashMap<Long,Comments> comments = new LinkedHashMap<Long,Comments>();
    
    
    
    private Long id ;
    private String post ;
    private Long imageid ;
    private User user ;
    private String username;
    private String avtarsrc;
    private Integer userid ;

    public String getAvtarsrc() {
        String src =  SamajUtils.getImagesrcfromID(getUser().getAvtaroid());
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
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
    
    public Long addPost(String sessionid , String posttext,Long userid,Long imageid) {
       Long postid = null;
        SamajUtils utils = new SamajUtils();
       postid = utils.postText(user.getName(), sessionid, posttext,userid,imageid);
       return postid;
    }
    
    

   

    public Comments getComments(Long commentid) {
        return comments.get(commentid);
    }

    public void setComments(Long commentid , Comments comment ) {
        comments.put(commentid, comment);
    }
    
    public Comments addComments(String commenttext,Long userid,String username) {
        Long commentid = new Long(0L);
        Comments comment = new Comments(this);
        comment.setCommentText(commenttext);
         SamajUtils utils = new SamajUtils();
         System.out.println("p1.Post.addComments()"+id);
        commentid = utils.postComments(commenttext,id,username,userid);
        comments.put(commentid, comment);
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
