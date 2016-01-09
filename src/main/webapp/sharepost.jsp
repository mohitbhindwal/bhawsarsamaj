<%@page import="p1.User"%>
<%@page import="p1.Post"%>
<%@page import="p1.UserPosts"%>
<%

    UserPosts userposts =  (UserPosts)session.getAttribute("userposts");
    User user =  (User)session.getAttribute("user");
   Long postid = userposts.addPosts(session.getId(),request.getParameter("data"),user.getId());
   System.out.print("Generated postid is "+postid);
   request.setAttribute("postid", postid);
   request.setAttribute("post", request.getParameter("data"));
   request.setAttribute("postman", user.getName());
%>


    <jsp:include page="post.jsp">
        <jsp:param name="post" value="${requestScope.post}"/>
        <jsp:param name="id" value="${requestScope.postid}"/>
        <jsp:param name="postman" value="${requestScope.postman}"/>
    </jsp:include>
