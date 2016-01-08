/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

/**
 *
 * @author m
 */
public class UserPosts {
    
    private User user ;

    public UserPosts() {
    }
    
    
    public HashMap<Long,Post> posts = new HashMap<Long,Post>();
    
 //  public ArrayList<Post> posts = new ArrayList<Post>();
    

    public User getUser() {
        return user;
    }

    public  Post getPosts(Long postid) {
        return posts.get(postid);
    }

    public void setPosts(Long postid,Post post ) {
        posts.put(postid, post);
    }
    
    public Long addPosts(String sessionid ,String posttext) {
        Long postid = null;
        Post post = new Post(user);
        postid =   post.addPost(sessionid, posttext);
        post.setId(postid);
        setPosts(postid,post);
        return postid;
        
    }
    
    public void loadPost(int lastNumberOfPost ){
    SamajUtils utils = new SamajUtils();
    posts.putAll(utils.loadPostOfUser(user, lastNumberOfPost));
    }
    
    

    public void setUser(User user) {
        this.user = user;
    }

    public UserPosts(User user ) {
        this.user=user ;
    }
    
    
    
}
