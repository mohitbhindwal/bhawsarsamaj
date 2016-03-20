<%@page import="p1.UserPosts"%>
<%@page import="p1.User"%>
<%@page import="p1.SamajUtils"%>
<%System.out.println("Get Body----------");%>
<jsp:include page="print.jsp"/>
<%
Long userid = Long.parseLong(request.getParameter("userid"));
User user = new User(userid);
request.setAttribute("user", user); 
request.setAttribute("userid", userid);
request.setAttribute("username", user.getName());
request.setAttribute("sendername", ((User)session.getAttribute("user")).getName() ); 


UserPosts userposts = new UserPosts(user);
userposts.loadPost(15,false);
request.setAttribute("userposts", userposts);
String isfriends =  SamajUtils.isFriends(((User)session.getAttribute("user")).getId(), userid);
request.setAttribute("isfriends",isfriends);

if(((User)session.getAttribute("user")).getId()==userid){
request.setAttribute("editmode","true"); 
request.setAttribute("isfriends","accepted");
}else
request.setAttribute("editmode","false"); 


%>
 <jsp:include page="body.jsp"/>