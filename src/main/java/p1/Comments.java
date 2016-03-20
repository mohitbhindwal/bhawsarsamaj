/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.text.SimpleDateFormat;

/**
 *
 * @author m
 */
public class Comments {

    private Post post;
    private String commentText;
    private Long id;
    private Long postid;
    private String username;
    private Long userid;
    private Long commentoravtarID;
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    public String creationDate;

    public Long getCommentoravtarID() {
        return commentoravtarID;
    }

    public void setCommentoravtarID(Long commentoravtarID) {
        this.commentoravtarID = commentoravtarID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostid() {
        return postid;
    }

    public void setPostid(Long postid) {
        this.postid = postid;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Comments(Post post) {
        this.post = post;
    }

    public Comments() {

    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
