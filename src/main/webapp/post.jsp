<%@page import="java.util.Locale"%>
<%@page import="p1.User"%><%@page import="java.util.Map"%><%@page import="p1.SamajUtils"%><%@page import="p1.Comments"%><%@page import="java.util.LinkedHashMap"%>

<div class="row">
   <div class="col-sm-8">
        <div class="panel panel-primary post panel-shadow">
            <div class="post-heading">
                <div class="pull-left image">
                    <img src="${avtarsrc}" class="img-circle avatar" alt="user profile image">
                </div>
                
                
                <div class="pull-left meta">
                 <div class="title h5">
                     <a onclick="getbody(this,${postmanid});" href="javascript:void(0);">
                                <b><%= request.getParameter("postman")%></b></a> 
                                 <h5 class="time">${creationdate}</h5> 
                    </div>
                </div>
                    
     <div class="btn-group pull-right">
                                <button aria-expanded="false" type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-chevron-down"></i>
                                </button>
                                <ul class="dropdown-menu slidedown">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-remove fa-fw"></i> Block
                                        </a>
                                    </li>
                                    <!--li>
                                        <a href="#">
                                            <i class="fa fa-check-circle fa-fw"></i> Available
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-times fa-fw"></i> Busy
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-clock-o fa-fw"></i> Away
                                        </a>
                                    </li>
                                    <li class="divider"></li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-sign-out fa-fw"></i> Sign Out
                                        </a>
                                    </li-->
                                </ul>
                            </div>
                    
                 
            </div> 
            <div class="post-description"> 
                
                <p lang="hi"><%= new String(request.getParameter("post").getBytes("UTF-8"),"UTF-8")%> </p>
                 

                <%if(request.getParameter("imageid")!=null&&request.getParameter("imageid").length()>0){%>
                <div class="row">
                    <div class="col-xs-6 col-md-6" style="margin: 0px;padding: 0px">
                     <jsp:include page="displayimage.jsp">
                    <jsp:param name="imageid" value="${requestScope.imageid}" />
                    </jsp:include>
                    </div>
                </div> 
               <%}%>
               
               
                <%if(request.getAttribute("url")!=null&&request.getAttribute("url").toString().length()>0){%>
                 
                   <div  class="embed-responsive embed-responsive-16by9">
                        <iframe id="myVideo" class="embed-responsive-item"  src="<%=request.getAttribute("url")%>" allowfullscreen>
                        </iframe>
 
                    
                </div> 
                
                <%}%>
                 <!-- Like button start -->
                 <% 
                     boolean selflike = false; 
                      int likecount = 0 ;
                     if(request.getAttribute("likeby")!=null){
                      likecount = ((Map)request.getAttribute("likeby")).size();
                     Long userid = ((User)session.getAttribute("user")).getId();
                     selflike =  ((LinkedHashMap)request.getAttribute("likeby")).keySet().contains(userid);
                    }
                    String color = selflike == true? "#337ab7":"#bfbfbf";
                    
                 %>
                 
                 <div class="stats" style="margin-left: 0px"  >


                     <div class="btn-group">
                         <button type="button"  isclicked="<%=selflike%>" 
                                 onclick="likeme(this,<%= request.getParameter("id")%>,true)" 
                                 class="btn btn-default btn-link" 
                                 style="color: <%=color%>;padding-left: 0px;padding-right: 0px">
                             <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true" id="<%=request.getParameter("id")%>_likevalue">&nbsp;<%=likecount%></span></button> 
                         <button type="button" style="color:#bfbfbf" class="btn btn-default btn-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                             <span class="caret" style="color:#bfbfbf"></span>
                         </button><ul class="dropdown-menu"><li>
                                 <a onclick="likebyuser(this,<%= request.getParameter("id")%>,true)"  
                                    data-toggle="modal" data-target="#myModal" >Like By</a></li>
                         </ul></div>

           
           
             <button type="button"  onclick="sharebyuser(this,<%= request.getParameter("id")%>,false)"
          class="btn btn-default btn-link" style="color: <%=color%>;padding-left: 0px;padding-right: 0px">share</button>
   <!--a onclick="sharebyuser(this,<%= request.getParameter("id")%>,false)"
      class="btn btn-default stat-item"><i class="fa fa-share icon"></i>
       <span id="<%=request.getParameter("id")%>_sharevalue">0</span></a-->


    </div>
                 <!-- Like button end -->
   
            </div>
            <div class="post-footer">
                <ul id="<%= request.getParameter("id")%>_commentlist" class="comments-list">
                  
                    <%
                    
                      LinkedHashMap<Long,Comments> map =(LinkedHashMap<Long,Comments>)request.getAttribute("comments");
                      System.out.print(map);
                      if(map!=null&&map.size()>0)
                      for(Long id : map.keySet()){
                      Comments comment =  map.get(id);
                    request.setAttribute("commentid",comment.getId());
                    request.setAttribute("commenttext",comment.getCommentText());
                    request.setAttribute("username",comment.getUsername());
                    request.setAttribute("userid",comment.getUserid());
                    request.setAttribute("creationdate",comment.getCreationDate());
                    request.setAttribute("commentoravtaridsrc",SamajUtils.getImagesrcfromID(comment.getCommentoravtarID()));
                    %>
                    <jsp:include page="comment.jsp">
                        <jsp:param name="commentid" value="${commentid}"/>                          
                        <jsp:param name="commenttext" value="${commenttext}"/>
                        <jsp:param name="username" value="${username}"/>   
                        <jsp:param name="userid" value="${userid}"/>
                        <jsp:param name="userid" value="${userid}"/>
                    </jsp:include>
                    <%}%>
                </ul>
                
               <div class="input-group"> 
                    <!--input class="form-control" placeholder="Add a comment" type="text"-->
                    <textarea class="form-control" id="<%= request.getParameter("id")%>_comment"></textarea>
                    <span class="input-group-addon">
                        <a id="<%= request.getParameter("id") %>"  href="javascript:void(0);" onclick="anchorClicked(this,<%= request.getParameter("id") %>)"> <i class="fa fa-edit" ></i></a>  
                    </span>
                </div>
            </div>  
        </div>
    </div>
</div>