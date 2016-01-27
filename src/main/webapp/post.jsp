<%@page import="p1.Comments"%>
<%@page import="java.util.LinkedHashMap"%>
<div class="container">
    <div class="col-sm-8">
        <div class="panel panel-primary post panel-shadow">
            <div class="post-heading">
                <div class="pull-left image">
                    <img src="http://localhost:7070/newbhawsarsamaj/img/mohit.png" class="img-circle avatar" alt="user profile image">
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
                
                
                <div class="stats">
                    <a id="like" name ="<%= request.getParameter("id") %>" href="#" class="btn btn-default stat-item"><i class="fa fa-thumbs-up icon"></i>250</a>
                    <a href="#" class="btn btn-default stat-item"><i class="fa fa-share icon"></i>12</a>
                </div>
            </div>
            <div class="post-footer">
                <ul id="<%= request.getParameter("id")%>_commentlist" class="comments-list">
                  
                    <%
                    
                      LinkedHashMap<Long,Comments> map =(LinkedHashMap<Long,Comments>)request.getAttribute("comments");
                      System.out.print(map);
                      for(Long id : map.keySet()){
                      Comments comment =  map.get(id);
                    request.setAttribute("commentid",comment.getId());
                    request.setAttribute("commenttext",comment.getCommentText());
                    
                    %>
                      <jsp:include page="comment.jsp">
                          <jsp:param name="commentid" value="${commentid}"/>                          
                            <jsp:param name="commenttext" value="${commenttext}"/>   
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
                    
