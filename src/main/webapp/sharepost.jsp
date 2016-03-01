<%@page import="p1.User"%>
<%@page import="p1.Post"%>
<%@page import="p1.UserPosts"%>
<%

    UserPosts userposts =  (UserPosts)session.getAttribute("userposts");
    User user =  (User)session.getAttribute("user");
    System.out.print(request.getParameter("imageid"));
    String imgid = request.getParameter("imageid");
    
    Long imageid = null;
    if(imgid!=null&&imgid.length()>0)
    imageid = Long.valueOf(request.getParameter("imageid"));
   Post post = userposts.addPosts(session.getId(),request.getParameter("data"),user.getId(),imageid);
   System.out.print("Generated postid is "+post.getId());
   request.setAttribute("postid", post.getId());
   request.setAttribute("post", request.getParameter("data"));
   request.setAttribute("postman", user.getName());
   request.setAttribute("creationdate", post.getCreationDate());
    request.setAttribute("avtarsrc", user.getAvtarsrc());
     if(imgid!=null&&imgid.length()>0)
   request.setAttribute("imageid", imageid);
%>


    <jsp:include page="post.jsp">
        <jsp:param name="post" value="${requestScope.post}"/>
        <jsp:param name="id" value="${requestScope.postid}"/>
        <jsp:param name="postman" value="${requestScope.postman}"/>
        <jsp:param name="imageid" value="${requestScope.imageid}"/>
    </jsp:include>
