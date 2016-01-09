<%@page import="p1.Post"%>
<%@page import="p1.UserPosts"%>
<jsp:include page="print.jsp"></jsp:include>
<%

    
  UserPosts userposts =  (UserPosts)session.getAttribute("userposts");
  Post post = userposts.getPosts(Long.valueOf(request.getParameter("postid").toString()));
  System.out.print("postid of comment"+post.getId());
  post.addComments(request.getParameter("commentdata").toString(),userposts.getUser().getId());
  

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
