<!-- get friends post-->
<%@page import="java.util.ArrayList"%>
<%@page import="p1.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id = "userposts" class = "p1.UserPosts" scope = "session"></jsp:useBean>
<%
    
 for(Long postid : userposts.friendposts.keySet()){
 Post post =  userposts.friendposts.get(postid);
 request.setAttribute("postid", post.getId());
 request.setAttribute("post", post.getPost());
 request.setAttribute("postman", post.getUsername());
 request.setAttribute("imageid", post.getImageid());
 request.setAttribute("comments", post.addAllDBComments());
 request.setAttribute("avtarsrc", post.getAvtarsrc());

%>
 
<jsp:include page="post.jsp">
    <jsp:param name="post" value="${requestScope.post}"/>
    <jsp:param name="id" value="${requestScope.postid}"/>
    <jsp:param name="postman" value="${requestScope.postman}"/>
    <jsp:param name="imageid" value="${requestScope.imageid}"/>
</jsp:include>

 
 <%}%>

     
  
    
 


 
 <!-- get friends post end-->