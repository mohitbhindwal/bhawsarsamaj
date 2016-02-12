<%@page import="p1.SamajUtils"%>
<%@page import="p1.Comments"%>
<%@page import="java.util.LinkedHashMap"%>
<div class="row">
   <div class="col-sm-8">
        <div class="panel panel-primary post panel-shadow">
            <div class="post-heading">
                <div class="pull-left image">
                    <img src="${avtarsrc}" class="img-circle avatar" alt="user profile image">
                </div>
                <div class="pull-left meta">
                    <div class="title h5">
                        <a href="#"><b><%= request.getParameter("postman")%><%= request.getParameter("id")%></b></a>
                    </div>
                    <h6 class="text-muted time">1 minute ago</h6>
                </div>
                     <div class="btn-group pull-right">
                                <button aria-expanded="false" type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-chevron-down"></i>
                                </button>
                                <ul class="dropdown-menu slidedown">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-refresh fa-fw"></i> Refresh
                                        </a>
                                    </li>
                                    <li>
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
                                    </li>
                                </ul>
                            </div>
                    
                    
            </div> 
            <div class="post-description"> 
                <p><%= request.getParameter("post") %></p>
                

                <%if(request.getParameter("imageid")!=null&&request.getParameter("imageid").length()>0){%>
                <div class="row">
                    <div class="col-xs-6 col-md-6" style="margin: 0px;padding: 0px">
                     <jsp:include page="displayimage.jsp">
                    <jsp:param name="imageid" value="${requestScope.imageid}" />
                    </jsp:include>
                    </div>
                </div> 
               <%}%>
                
                <jsp:include page="like.jsp" >
                        <jsp:param name="userid" value="${user.id}"/>
                        <jsp:param name="displaylikes" value="true"/>
                        <jsp:param name="postid" value="${param.id}"/>
                </jsp:include>
                    
                    
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
                    request.setAttribute("commentoravtaridsrc",SamajUtils.getImagesrcfromID(comment.getCommentoravtarID()));
                    %>
                    <jsp:include page="comment.jsp">
                        <jsp:param name="commentid" value="${commentid}"/>                          
                        <jsp:param name="commenttext" value="${commenttext}"/>
                        <jsp:param name="username" value="${username}"/>   
                        <jsp:param name="userid" value="${userid}"/>
                    </jsp:include>
                    <%}%>
                    
                    
                    
                    
                    
                    
                </ul>
                
               <div class="input-group"> 
                    <!--input class="form-control" placeholder="Add a comment" type="text"-->
                    <textarea class="form-control" id="<%= request.getParameter("id")%>_comment" placeholder="What are you doing right now?" ></textarea>
                    <span class="input-group-addon">
                        <a id="<%= request.getParameter("id") %>"  href="javascript:void(0);" onclick="anchorClicked(this,<%= request.getParameter("id") %>)"> <i class="fa fa-edit" ></i></a>  
                    </span>
                </div>
            </div>  
        </div>
    </div>
</div>
                    
