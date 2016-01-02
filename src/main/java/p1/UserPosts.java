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
public class UserPosts {
    
    private User user ;

    public UserPosts() {
    }
    
    
    
    
    public ArrayList<Post> posts = new ArrayList<Post>();
    

    public User getUser() {
        return user;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
    
    public void addPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
    
    public void loadPost(int lastNumberOfPost ){
    SamajUtils utils = new SamajUtils();
    posts.addAll(utils.loadPostOfUser(user, lastNumberOfPost));
    }
    
    

    public void setUser(User user) {
        this.user = user;
    }

    public UserPosts(User user ) {
        this.user=user ;
    }
    
    
    
}
