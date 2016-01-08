<%@page import="p1.Post"%>
<%@page import="p1.UserPosts"%>
<%

    UserPosts userposts =  (UserPosts)session.getAttribute("userposts");
   Long postid = userposts.addPosts(session.getId(),request.getParameter("data"));
   System.out.print("Generated postid is "+postid);
   request.setAttribute("postid", postid);
request.setAttribute("post", request.getParameter("data"));
%>


    <jsp:include page="post.jsp">
     
        <jsp:param name="post" value="${requestScope.post}"/>
 
        <jsp:param name="id" value="${requestScope.postid}"/>
 
    </jsp:include>
