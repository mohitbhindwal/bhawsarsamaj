<%@page import="p1.Post"%>
<%@page import="p1.UserPosts"%>
<%

  UserPosts userposts =  (UserPosts)session.getAttribute("userposts");
  Post post = userposts.getPosts(Long.valueOf(request.getParameter("commentid").toString()));
  System.out.print("sharecooment"+post.getId());
  post.addComments(request.getParameter("commentdata").toString());


%>


<li class="comment">
    <a class="pull-left" href="#">
        <img class="avatar" src="http://bootdey.com/img/Content/user_1.jpg" alt="avatar">
    </a>
    <div class="comment-body">
        <div class="comment-heading">
            <h4 class="user">Gavino Free</h4>
            <h5 class="time">5 minutes ago</h5>
        </div>
        <p><%= request.getParameter("commentdata")%></p>
    </div>
</li>