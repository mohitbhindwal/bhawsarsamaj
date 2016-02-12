
<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="p1.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 
<!--link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"-->

<!--
User user = (User)session.getAttribute("user");
user.postText(user.getName(), user.getSessionId(),request.getParameter("mdata"));
%-->

<jsp:useBean id = "userposts" class = "p1.UserPosts" scope = "session"></jsp:useBean>
<jsp:useBean id = "user" class = "p1.User" scope = "session"></jsp:useBean>

<%

Set<Long> keyset = null;
        
if(!request.getAttribute("editmode").equals("true"))  
   userposts =(p1.UserPosts)request.getAttribute("userposts") ;
 
 
 keyset = userposts.posts.keySet();
 for(Long postid :  keyset){
 Post post =  userposts.getPosts(postid);
 request.setAttribute("postid", post.getId());
 request.setAttribute("post", post.getPost());
 request.setAttribute("postman", post.getUser().getName());
 request.setAttribute("imageid", post.getImageid());
 request.setAttribute("comments", post.addAllDBComments());
 request.setAttribute("likeby", post.addAllLikeByOFPost());
 request.setAttribute("avtarsrc", post.getAvtarsrc());
%>
 
  <jsp:include page="post.jsp">
     
         <jsp:param name="post" value="${requestScope.post}"/>
        <jsp:param name="id" value="${requestScope.postid}"/>
        <jsp:param name="postman" value="${requestScope.postman}"/>
        <jsp:param name="imageid" value="${requestScope.imageid}"/>
 
    </jsp:include>
 
 
 <%}%>

     
  
    

<!--%

     UserPosts  userposts =  (UserPosts)session.getAttribute("userposts");
      ArrayList<Post> list =  userposts.getPosts();
        for(Post post : list){
        RequestDispatcher dr=request.getRequestDispatcher("post.jsp"); 
        dr.include(request, response); 
        }
%-->
 



<!--link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.min.css">
<div class="container">
    <div class="col-sm-8">
        <div class="panel panel-primary post panel-shadow">
            <div class="post-heading">
                <div class="pull-left image">
                    <img src="http://localhost:7070/newbhawsarsamaj/img/mohit.png" class="img-circle avatar" alt="user profile image">
                </div>
                <div class="pull-left meta">
                    <div class="title h5">
                        <a href="#"><b>${user.name}</b></a>
                    </div>
                    <h6 class="text-muted time">1 minute ago</h6>
                </div>
               <div class="pull-right">
                                <div class="btn-group">
                                    <button aria-expanded="false" type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                        Actions
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li><a href="#">Action</a>
                                        </li>
                                        <li><a href="#">Another action</a>
                                        </li>
                                        <li><a href="#">Something else here</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li><a href="#">Separated link</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
            </div> 
            <div class="post-description"> 
                <p>${user.name}</p>
                <div class="stats">
                    <a href="#" class="btn btn-default stat-item"><i class="fa fa-thumbs-up icon"></i>250</a>
                    <a href="#" class="btn btn-default stat-item"><i class="fa fa-share icon"></i>12</a>
                </div>
            </div>
            <div class="post-footer">
                   <div class="input-group"> 
                    <!--input class="form-control" placeholder="Add a comment" type="text">
                    <textarea class="form-control" placeholder="What are you doing right now?" ></textarea>
                    <span class="input-group-addon">
                        <a href="#"><i class="fa fa-edit"></i></a>  
                    </span>
                </div>
            </div>
        </div-->



 