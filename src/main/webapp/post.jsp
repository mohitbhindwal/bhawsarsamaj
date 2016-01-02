<!--link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"-->



<link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.min.css">
<script>
      $('#<%= request.getParameter("id")%>').click(function(evt){
      evt.preventDefault();
      alert(evt.target.id);
      postcomment(evt);
   });
   
   
      function postcomment(evt){
             var dataString = 'data='+$('#<%= request.getParameter("id")%>_comment').val() ;
         if(evt!==null)
             evt.preventDefault();
         $.ajax({
                type: 'POST',
                url: 'sharecomment.jsp',
                dataType: 'html',
                data:dataString,
                success: function(data) {
                //   alert(data);
                    
                      $('#<%= request.getParameter("id")%>_commentlist').append(data);
                   //    setTimeout("postdata(null)",1000000);
                },
                error : function(request,error){
                   alert("Request: "+JSON.stringify(request));
    }
            });
   }
   
   
</script>
   
<div class="container">
    <div class="col-sm-8">
        <div class="panel panel-primary post panel-shadow">
            <div class="post-heading">
                <div class="pull-left image">
                    <img src="http://localhost:7070/newbhawsarsamaj/img/mohit.png" class="img-circle avatar" alt="user profile image">
                </div>
                <div class="pull-left meta">
                    <div class="title h5">
                        <a href="#"><b><%= request.getParameter("id") %></b></a>
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
                <div class="stats">
                    <a href="#" class="btn btn-default stat-item"><i class="fa fa-thumbs-up icon"></i>250</a>
                    <a href="#" class="btn btn-default stat-item"><i class="fa fa-share icon"></i>12</a>
                </div>
            </div>
            <div class="post-footer">
                <ul id="<%= request.getParameter("id")%>_commentlist" class="comments-list">
                    <li class="comment">
                        <a class="pull-left" href="#">
                            <img class="avatar" src="http://bootdey.com/img/Content/user_1.jpg" alt="avatar">
                        </a>
                        <div class="comment-body">
                            <div class="comment-heading">
                                <h4 class="user">Gavino Free</h4>
                                <h5 class="time">5 minutes ago</h5>
                            </div>
                            <p>Sure, oooooooooooooooohhhhhhhhhhhhhhhh</p>
                        </div>
                    </li>
                </ul>
                
               <div class="input-group"> 
                    <!--input class="form-control" placeholder="Add a comment" type="text"-->
                    <textarea class="form-control" id="<%= request.getParameter("id")%>_comment" placeholder="What are you doing right now?" ></textarea>
                    <span class="input-group-addon">
                        <a href="post.jsp"  ><i id="<%= request.getParameter("id") %>" class="fa fa-edit"></i></a>  
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>
                    


 