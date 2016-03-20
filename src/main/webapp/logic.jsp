<%@page import="introb.IntrobSession"%>
<%@page import="introb.SessioniUtils"%>
<%@page import="p1.SamajUtils"%>
<%

 String unfriendby = request.getParameter("unfriendby");
 String unfriendto = request.getParameter("unfriendto");
 if(unfriendby!=null){
 
 //SessioniUtils.executeUpdate(" update friendrequest set status = 'unfriend' where  requestsenttoid = " +request.getParameter("unfriendby")  
 
 //+ " and requestsentbyid = " +request.getParameter("unfriendto") );
 String sql = "delete from friendrequest where status = 'accepted' and ((requestsenttoid = "+unfriendto+" and requestsentbyid = "+unfriendby+ ") or (requestsenttoid = "+unfriendby+" and requestsentbyid = "+unfriendto + "))";
 System.out.print("Executing sql = "+sql);
 SessioniUtils.insertupdatedelete(sql);
 
 
 
 }


%>