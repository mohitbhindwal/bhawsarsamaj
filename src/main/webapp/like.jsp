 
<%@page import="java.util.LinkedHashMap"%>
<%@page import="p1.SamajUtils"%>
<jsp:useBean id = "userposts" class = "p1.UserPosts" scope = "request"></jsp:useBean>
<jsp:useBean id = "user" class = "p1.User" scope = "session"></jsp:useBean>


<% 
    
    long postid = Long.parseLong(request.getParameter("postid"));
    if(request.getParameter("frompost").equals("true")){
    //LinkedHashMap<Long,String>  likeby =  ( LinkedHashMap<Long,String> )request.getAttribute("likeby");
    SamajUtils.addLike(user.getId(), user.getName(), postid, true);
    LinkedHashMap<Long,String>  likeby = SamajUtils.getaddAllLikeByOFPost(postid,true);
    System.out.print("displaylikes false"+likeby.size());
    out.print(likeby.size());
    }else{
      System.out.print("displaylikes--- true");
      SamajUtils.addLike(user.getId(), user.getName(), postid, false);
      LinkedHashMap<Long,String>  likeby = SamajUtils.getaddAllLikeByOFPost(postid,false);
      out.print(likeby.size());
   }

%>

