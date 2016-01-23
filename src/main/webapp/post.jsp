<!--Post start-->

   
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
               <div class="pull-right">
                                <div class="btn-group">
                                    <button aria-expanded="false" type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                        Actions
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li><a href="#">Action</a>
                                        </li>
                                        <li><a href="#">Another action</a>
                                        </li>
                                        <li><a href="#">Something else here</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li><a href="#">Separated link</a>
                                        </li>
                                    </ul>
                                </div>
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
                  
                    
                    
                </ul>
                
               <div class="input-group"> 
                    <!--input class="form-control" placeholder="Add a comment" type="text"-->
                    <textarea class="form-control" id="<%= request.getParameter("id")%>_comment" placeholder="What are you doing right now?" ></textarea>
                    <span class="input-group-addon">
                        <a   href="#" > <i class="fa fa-edit" id="<%= request.getParameter("id") %>"></i></a>  
                    </span>
                </div>
            </div>  
        </div>
    </div>
</div>
                    

<script>
    
      $('#<%= request.getParameter("id")%>').click(function(evt){
      evt.preventDefault();
      alert(evt.target.id);
      postcomment<%= request.getParameter("id")%>(evt);
   });
   
   
   
   
   function postcomment<%= request.getParameter("id")%>(evt){
         alert('postcomment');
         var dataString = 'commentdata='+$('#<%= request.getParameter("id")%>_comment').val()+'&postid=<%= request.getParameter("id")%>' ;
         if(evt!==null)
             evt.preventDefault();
         $.ajax({
                type: 'POST',
                url: 'sharecomment.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
                alert(data);
                $('#<%= request.getParameter("id")%>_commentlist').append(data);
                   //    setTimeout("postdata(null)",1000000);
                },
                error : function(request,error){
                   alert("Request: "+JSON.stringify(request));
    }
            });
   }
   
 
   
</script>


 
     <script>
                                
                $('#like').click(function(event) {
                       if(event!==null)
                    event.preventDefault();   
                    alert( $(this).attr("name"));
                    alert( $(this).text());
                    var value1 = $(this).attr("value")
                     var dataString = 'value='+ $(this).text()+'&postid=<%= request.getParameter("id")%>' ;
       
                $.ajax({
                type: 'POST',
                url: 'like.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
                alert(data);
                    $('#like').text(data.trim());
                },
                error : function(request,error){
                   alert("Request: "+JSON.stringify(request));
    }
            });
                });
           

        

        </script>
    
    
    
 
 