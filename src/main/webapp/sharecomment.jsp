<%@page import="p1.Comments"%>
<%@page import="p1.SamajUtils"%>
<%@page import="p1.User"%>
<%@page import="p1.Post"%>
<%@page import="p1.UserPosts"%>
<jsp:include page="print.jsp"></jsp:include>
<%
  UserPosts userposts =  (UserPosts)session.getAttribute("userposts");
  User user = (User)session.getAttribute("user");
  Post post = userposts.getPosts(Long.valueOf(request.getParameter("postid").toString()));
  System.out.print("postid of comment"+post.getId());
  Comments comment =  post.addComments(request.getParameter("commentdata").toString(),user.getId(),user.getName());
  request.setAttribute("commentid", comment.getId());
  request.setAttribute("commenttext", request.getParameter("commentdata").toString());
  request.setAttribute("commentoravtaridsrc",SamajUtils.getImagesrcfromID(user.getAvtaroid()));
  request.setAttribute("username", user.getName());
  request.setAttribute("creationdate", comment.getCreationDate());
%>

<jsp:include page="comment.jsp">
        <jsp:param name="commentid" value="${requestScope.commentid}"/>
        <jsp:param name="commenttext" value="${requestScope.commenttext}"/>
</jsp:include>



   
                   
                
                     