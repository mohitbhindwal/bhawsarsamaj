/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import sun.applet.Main;

/**
 *
 * @author m
 */
public class Likes {
    
    private Long id ;
    private Integer postid ;
    private Integer userid ;
    private String username ;
    private String ispost ;
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIspost() {
        return ispost;
    }

    public void setIspost(String ispost) {
        this.ispost = ispost;
    
    }
    
    
    public static void main(String[] args) {
        for(int i =0 ; i < 10;i++){
        System.out.println(i%4);
        }
    }
    
}
