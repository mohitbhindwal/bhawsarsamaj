<%@page import="p1.Post"%>
<%@page import="p1.UserPosts"%>
<jsp:include page="print.jsp"></jsp:include>
<%
  UserPosts userposts =  (UserPosts)session.getAttribute("userposts");
  Post post = userposts.getPosts(Long.valueOf(request.getParameter("postid").toString()));
  System.out.print("postid of comment"+post.getId());
  Long commentid =  post.addComments(request.getParameter("commentdata").toString(),userposts.getUser().getId());
  request.setAttribute("commentid", request.getParameter("postid").toString());
  request.setAttribute("commenttext", request.getParameter("commentdata").toString());
%>


<jsp:include page="comment.jsp">
        <jsp:param name="commentid" value="${requestScope.commentid}"/>
        <jsp:param name="commenttext" value="${requestScope.commenttext}"/>
</jsp:include>



