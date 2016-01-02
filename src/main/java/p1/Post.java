/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.util.ArrayList;

/**
 *
 * @author m
 */
public class Post {
    
    ArrayList<Comments> comments = new ArrayList<Comments>();
    Integer id ;
    String post ;

    public Post() {
    }
    
    

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    
    public Integer addPost(String sessionid , String posttext) {
       Integer postid = null;
        SamajUtils utils = new SamajUtils();
       postid = utils.postText(user.getName(), sessionid, posttext);
       return postid;
    }
    
    

    private User user ;

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
