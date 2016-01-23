/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author m
 */

public class Post {
    
    HashMap<Long,Comments> comments = new HashMap<Long,Comments>();
    
    
    
    Long id ;
    String post ;

    public Long getImageid() {
        return imageid;
    }

    public void setImageid(Long imageid) {
        this.imageid = imageid;
    }
    Long imageid ;

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
    
    

    private User user ;

    public Comments getComments(Long commentid) {
        return comments.get(commentid);
    }

    public void setComments(Long commentid , Comments comment ) {
        comments.put(commentid, comment);
    }
    
    public Long addComments(String commenttext,Long userid) {
        Long commentid = new Long(0L);
        Comments comment = new Comments(this);
        comment.setCommentText(commenttext);
         SamajUtils utils = new SamajUtils();
         System.out.println("p1.Post.addComments()"+id);
        commentid = utils.postComments(commenttext,id,user.getName(),userid);
        comments.put(commentid, comment);
        return commentid;
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
