<%@page import="p1.Comments"%>
<%@page import="p1.SamajUtils"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="p1.User"%>
<%@page import="java.util.Map"%>
<li class="comment" id="commentid_${commentid}">
    <a class="pull-left" href="#">
        <img class="avatar" src="${commentoravtaridsrc}" alt="avatar">
    </a>
    
    <div class="comment-body">
        <div class="comment-heading">
            <h4 class="user"><a href="javascript:void(0);" onclick="getbody(this,${userid});" style="color: black"> ${username}</a> </h4>
            <h5 class="time">${creationdate}</h5>
        </div>
        <p>${commenttext}</p>
        
        
         <!-- Like button start -->
         <%   
             boolean cselflike = false; 
              int clikecount = 0 ;
              if(request.getParameter("commentid")!=null&&request.getParameter("commentid").length()>0){
              long commentid =Long.parseLong(request.getParameter("commentid").toString());
              LinkedHashMap<Long,String> cmap = SamajUtils.getaddAllLikeByOFPost(commentid,false);
              clikecount = cmap.size();
              Long userid = ((User)session.getAttribute("user")).getId();
              cselflike =  cmap.keySet().contains(userid);
              }
                    String color = cselflike == true? "#337ab7":"#bfbfbf";

                 %>
                 <div class="stats">
                     <div class="btn-group"  style="padding: 0px"> 
                         <button type="button" isclicked="<%=cselflike%>"  
                                 style="color: <%=color%>;padding-left: 0px;padding-right: 0px"
                                 onclick="likeme(this,<%= request.getParameter("commentid")%>,false)" 
                                 class="btn btn-default btn-link" style="color: <%=color%>">
                             <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true" id="<%=request.getParameter("commentid")%>_clikevalue">&nbsp;<%=clikecount%></span></button> 
                         <button type="button" style="color:#bfbfbf" class="btn btn-default btn-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                             <span class="caret"></span>
                         </button><ul class="dropdown-menu"><li>
                                 <a onclick="likebyuser(this,<%= request.getParameter("commentid")%>,false)"  data-toggle="modal" data-target="#myModal" >Like By</a></li>
                         </ul></div>

                 </div>
         <!-- Like button end -->
        
        
        
        
    </div>
</li>