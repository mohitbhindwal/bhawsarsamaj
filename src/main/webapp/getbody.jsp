<%@page import="p1.UserPosts"%>
<%@page import="p1.User"%>
<%@page import="p1.SamajUtils"%>
<%
Long userid = Long.parseLong(request.getParameter("userid"));
User user = new User(userid);
request.setAttribute("user", user); 
if(((User)session.getAttribute("user")).getId()==userid){
request.setAttribute("editmode","true"); 
}else
request.setAttribute("editmode","false"); 

UserPosts userposts = new UserPosts(user);
userposts.loadPost(15,false);
request.setAttribute("userposts", userposts);
%>
 <jsp:include page="body.jsp"/>