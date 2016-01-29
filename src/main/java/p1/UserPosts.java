/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 *
 * @author m
 */
public class UserPosts {
    
    private User user ;

    public UserPosts() {
    }
    
    
    public LinkedHashMap<Long,Post> posts = new LinkedHashMap<Long,Post>();
    public LinkedHashMap<Long,Post> friendposts = new LinkedHashMap<Long,Post>();
 //  public ArrayList<Post> posts = new ArrayList<Post>();
    

    public User getUser() {
        return user;
    }

    public  Post getPosts(Long postid) {
        Post temp =  posts.get(postid);
        if(temp!=null)
        return temp;
        return friendposts.get(postid);
    }

    public void setPosts(Long postid,Post post ) {
        posts.put(postid, post);
    }
    
    public Long addPosts(String sessionid ,String posttext,Long userid,Long imageid) {
        Long postid = null;
        Post post = new Post(user);
        postid =   post.addPost(sessionid, posttext,userid,imageid);
        post.setId(postid);
        setPosts(postid,post);
        return postid;
        
    }
    
    public void loadPost(int lastNumberOfPost) {
        SamajUtils utils = new SamajUtils();
        posts.putAll(utils.loadPostOfUser(user, lastNumberOfPost));
        for (Map.Entry<Long, User> entry : user.getFriends().entrySet()) {
            friendposts.putAll(utils.loadPostOfUser(entry.getValue(), lastNumberOfPost));
        }
    }
    
    

    public void setUser(User user) {
        this.user = user;
    }

    public UserPosts(User user ) {
        this.user=user ;
    }
    
    
    
}
