<%@page import="p1.User"%>
<%@page import="p1.SamajUtils"%>
<jsp:include page="carousel.jsp"/>
<jsp:include page="print.jsp"/>
    
<%
                User muser  =(User)request.getAttribute("user");
                System.out.print(muser.getAvtaroid());
                String avtarid = SamajUtils.getImagesrcfromID(muser.getAvtaroid());
                 System.out.print("is Friend Attribute -->"+request.getAttribute("isfriends"));
%>

<span id="loading">
    <img src="img/loading.gif">
</span>

<span id="fade"></span>
        <!--span id="modal">
            <img id="loader" src="img/loading.gif" />
        </span-->

<div class="col-lg-12" style="margin: 0px;padding: 0px;">
    <div class="card hovercard" style="margin: 0px;padding: 0px;">
     <div class="card-background" style="margin: 0px;padding: 0px;">    
    
         <img class="card-bkimg" alt="" src="<%=avtarid%>">
            <!-- http://lorempixel.com/850/280/people/9/ -->
        </div>
        <div class="useravatar">
            <img alt="" src="<%=avtarid%>">
        </div>
        <br/>
        <div class="card-info">
            <span class="card-title">${user.name}</span>
        </div>
    </div>
    <div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group" aria-label="...">
        <div class="btn-group" role="group">
            <button type="button" id="stars" class="btn btn-primary" href="#tab1" data-toggle="tab"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                <div class="hidden-xs">Home</div>
            </button>
        </div>
        <%if(request.getAttribute("editmode").equals("true")){%>
        <div class="btn-group" role="group">
            <button type="button" id="favorites" class="btn btn-primary" href="#tab2" data-toggle="tab"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                <div class="hidden-xs">Posts</div>
            </button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" id="following" class="btn btn-primary" href="#tab3" data-toggle="tab"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                <div class="hidden-xs">Search</div>
            </button>
        </div>
        <%}%>
    </div>  

            <div class="well">
            <!-- Modal one -->
                        <div id="mohitmodal">
 
                        </div>
                       <!---->
                       
                <div class="tab-content">
                    <!-- Tab1-->
                    <div class="tab-pane fade in active" id="tab1">
                         <!--This is Tab1 start-->
                   
                       
                        
                        
                        
                         <%if(request.getAttribute("editmode").equals("true")){%>
                        <jsp:include page="share.jsp"></jsp:include>
                        <%}%>
                        
                            <div id="wal" class="container-fluid">
                                 <%if(request.getAttribute("isfriends")!=null&&request.getAttribute("isfriends").equals("notfriends")){%>
                                 <div id="sendfriendrequestdiv">
                                 <button type="button" class="btn btn-primary btn-lg" onclick="sendfriendrequest(${sessionScope.user.id},'${sendername}',${userid},'${username}');" style="border-radius: 24px;">Send Friend Request</button>
                                 </div>
                                <p></p>
                                <%}else if(request.getAttribute("isfriends")!=null&&request.getAttribute("isfriends").equals("pending")){%>
                                 <button type="button" class="btn btn-primary btn-lg" style="border-radius: 24px;">Friend Request Pending</button>
                                <%}
                                 else if(request.getAttribute("isfriends")!=null&&request.getAttribute("isfriends").equals("accepted")){%>
                                <%System.out.println("Get Post called");%>    
                                <jsp:include page="getpost.jsp"></jsp:include>
                              <%}%>
                        
                            
                            </div>
                        </div><!-- Tab1 End-->

                            <%if(request.getAttribute("editmode").equals("true")){%>
                        <!-- Tab2-->
                    <div class="tab-pane fade in" id="tab2">
                            <div id="wal2">
 
                            <jsp:include page="getfriendspost.jsp"></jsp:include>
                            </div>
                    </div><!-- Tab2 End-->
                            
                    <!-- Tab3-->
                    <div class="tab-pane fade in" id="tab3">
                    <jsp:include page="tabs.jsp" />
                    </div><!-- Tab3 End-->
                     <%}%>
                    
                </div>

            </div>

    </div>