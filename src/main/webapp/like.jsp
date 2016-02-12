 
<%@page import="java.util.LinkedHashMap"%>
<%@page import="p1.SamajUtils"%>
<jsp:useBean id = "userposts" class = "p1.UserPosts" scope = "request"></jsp:useBean>
<jsp:useBean id = "user" class = "p1.User" scope = "session"></jsp:useBean>


<% 
    
    long postid = Long.parseLong(request.getParameter("postid"));
    if(request.getParameter("displaylikes")!=null){
    LinkedHashMap<Long,String>  likeby =  ( LinkedHashMap<Long,String> )request.getAttribute("likeby");
    System.out.print("displaylikes true");
%>
    
    <div class="stats">
   <a id="like" onclick="likeme(this,<%= request.getParameter("id")%>)" name ="<%= request.getParameter("id")%>" href="javascript:void(0);" class="btn btn-primary stat-item"><i class="fa fa-thumbs-up icon"></i><%=likeby.size()%></a>
   <a href="#" class="btn btn-default stat-item"><i class="fa fa-share icon"></i>12</a>
   
<div class="btn-group"> 
<button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>&nbsp;5&nbsp;</button> 
<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span></button><ul class="dropdown-menu"><li><a href="#">Action</a></li><li><a href="#">Another action</a></li></ul></div>
    
    </div>
   
   <%
    }else{
    int currentlikes = Integer.parseInt(request.getParameter("currentvalue").trim());
    System.out.print("displaylikes false");
    SamajUtils.addLike(user.getId(), user.getName(), postid);
    out.print(currentlikes+1);
    }%>

