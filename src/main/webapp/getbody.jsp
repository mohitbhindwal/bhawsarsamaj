<%@page import="p1.UserPosts"%>
<%@page import="p1.User"%>
<%@page import="p1.SamajUtils"%>
<%
Long userid = Long.parseLong(request.getParameter("userid"));
User user = new User(userid);
request.setAttribute("user", user); 
request.setAttribute("userid", userid); 
request.setAttribute("sendername", ((User)session.getAttribute("user")).getName() ); 
if(((User)session.getAttribute("user")).getId()==userid){
request.setAttribute("editmode","true"); 
}else
request.setAttribute("editmode","false"); 

UserPosts userposts = new UserPosts(user);
userposts.loadPost(15,false);
request.setAttribute("userposts", userposts);
boolean isfriends =  SamajUtils.isFriends(((User)session.getAttribute("user")).getId(), userid);
request.setAttribute("isfriends",String.valueOf(isfriends));



%>
 <jsp:include page="body.jsp"/>