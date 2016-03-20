<%@page import="p1.SamajUtils"%>
<%@page import="java.util.HashMap"%>
<%@page import="p1.User"%>
<%
    
    //
    if(request.getParameter("requeststatus")!=null && request.getParameter("friendrequestid")!=null)
    {
    boolean isSuccess =  SamajUtils.changeFriendRequestStatus(request.getParameter("requeststatus"), Integer.valueOf(request.getParameter("friendrequestid")));
    out.print(isSuccess);
    return ;
    }    
    
    
    
    User user =  (User)session.getAttribute("user");
    HashMap map =  user.getFriendRequestMap();
    
%>   




<li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu message-dropdown">
                        <%if(map.size()>0) for(Object key : map.keySet()){ %>
                        <li class="message-preview">
                            <p>Friend Request Received From <a href="javascript:void(0);" onclick="getbody(this,<%=key.toString().split("_")[1].split("#")[0]%>);"><%=map.get(key)%></a></p>
                            
                                  <div class="media">
                                    <span class="pull-left">
                                        <img class="avatar" style="width:50px;height:50px" src="<%=key.toString().split("#")[1]%>" alt="No image">
                                    </span>
                                    <div class="media-body" id="acceptreject_<%=key.toString().split("_")[0]%>" >
                                        
                                        <button id="validateBtn" id="fraccepted_<%=key.toString().split("_")[0]%>" onclick="changeFriendRequestStatus(this,'accepted',<%=key.toString().split("_")[0]%>)"   class="btn btn-primary"><i class="icon-hand-right"></i>Accept</button>&nbsp;
                                        <button id="validateBtn" id="frrejected_<%=key.toString().split("_")[0]%>" onclick="changeFriendRequestStatus(this,'rejected',<%=key.toString().split("_")[0]%>)"   class="btn btn-primary"><i class="icon-hand-right"></i>Reject</button>
                                    </div>
                                </div>
                           
                        </li>
                        <%} else {%> <li class="message-preview"><h3>No Notification</h3></li>   <%}%>
                    </ul>
                </li>