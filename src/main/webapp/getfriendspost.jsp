<%@page import="java.util.Set"%>
<!-- get friends post-->
<%@page import="java.util.ArrayList"%>
<%@page import="p1.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id = "userposts" class = "p1.UserPosts" scope = "session"></jsp:useBean>
<%
 
 Set<Long>  keyset = userposts.friendposts.keySet();
 if(keyset==null||keyset.size()==0)out.print("<h1><I>No Post For Now Please Add Friends</I></h1>");
 for(Long postid : keyset){
 Post post =  userposts.friendposts.get(postid);
 request.setAttribute("postid", post.getId());
 request.setAttribute("post", post.getPost());
 request.setAttribute("postman", post.getUsername());
 request.setAttribute("postmanid", post.getUserid());
 request.setAttribute("imageid", post.getImageid());
 request.setAttribute("comments", post.addAllDBComments());
 request.setAttribute("avtarsrc", post.getAvtarsrc());
 request.setAttribute("creationdate", post.getCreationDate());
 request.setAttribute("url", post.getUrl());

%>
 
<jsp:include page="post.jsp">
    <jsp:param name="post" value="${requestScope.post}"/>
    <jsp:param name="id" value="${requestScope.postid}"/>
    <jsp:param name="postman" value="${requestScope.postman}"/>
    <jsp:param name="imageid" value="${requestScope.imageid}"/>
</jsp:include>

 
 <%}%>

     
  
    
 


 
 <!-- get friends post end-->