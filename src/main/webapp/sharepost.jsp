<%@page import="p1.User"%>
<%@page import="p1.Post"%>
<%@page import="p1.UserPosts"%>
<%

    UserPosts userposts =  (UserPosts)session.getAttribute("userposts");
    User user =  (User)session.getAttribute("user");
    System.out.print(request.getParameter("imageid"));
    String imgid = request.getParameter("imageid");
    String url = request.getParameter("url");
    Long imageid = null;
    if(imgid!=null&&imgid.length()>0)
    imageid = Long.valueOf(request.getParameter("imageid"));
    if(user==null)
    System.out.print("user is null");
    
    if(userposts==null)
    System.out.print("userposts is null");
    
   Post post = userposts.addPosts(session.getId(),request.getParameter("data"),user.getId(),imageid,  url);
   System.out.print("Generated postid is "+post.getId());
   System.out.print("========> "+request.getParameter("data"));
   request.setAttribute("postid", post.getId());
   request.setAttribute("post", request.getParameter("data"));
   request.setAttribute("postman", user.getName());
   request.setAttribute("creationdate", post.getCreationDate());
   request.setAttribute("url", url);
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
