<%@page import="p1.Login"%>
<%@page import="p1.User"%>
<%@page import="introb.DataObject"%>
<%@page import="introb.SessioniUtils"%>
<%@page import="introb.DataSet"%>
<%@page import="p1.SamajUtils"%>
<%@page import="java.util.LinkedHashMap"%>


<%

if(request.getParameter("sharetomyfriends")!=null){

    Integer  sharebyid = Integer.valueOf(request.getParameter("sharebyuserid"));
    String sharebyusername = request.getParameter("sharebyusername");
    int postid = Integer.valueOf(request.getParameter("postid"));
    
    User user =  (User)session.getAttribute("user");
    LinkedHashMap<Long,User> friendmap = user.getFriends();
    for(Long friendid : friendmap.keySet()){
    
     SamajUtils.sharepost(user.getId(),user.getName(), friendmap.get(friendid).getId(),friendmap.get(friendid).getName(), postid);
    
    
    
    }
    
    if(true)
    return ;


}


%>






<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Share</h4>
      </div>
         
  
        <div class="modal-body">
            <ul class="list-group">
            
     
<%
if(request.getParameter("frompost").equals("false")){

   long postid = Long.parseLong(request.getParameter("postid").trim().toString());
   LinkedHashMap<Long,User> map =  ((User)session.getAttribute("user")).getFriends();
   
   if(map.size()>0)
   for(Long key : map.keySet()){
        DataSet ds = SessioniUtils.query("select avtar from users where id ="+key);
        DataObject dob=ds.get(0);
            String title = "\"img/default_user.png\",";
            if(dob.get("avtar")!=null){
              String outpath = SamajUtils.displayImage(Long.parseLong(dob.get("avtar").toString()),Login.imagefolder);
                title = request.getContextPath()+"/images/"+outpath;
                System.out.print("------->"+title);
            }
   %>
   <li class="list-group-item" style="padding: 0px;margin: 0px;">
            <a class="bts-dynamic-item" onclick="getbody(this,<%=key%>);" 
               href="javascript:void(0);"><span><img style="width:50px;height:50px" 
                src="<%=title%>">
                </span><span><i><b><%=map.get(key).getName()%></b></i></span></a>
                
            </li>
                
   <%
   }
}%> 
        </ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <!--button type="button" id="sharepostblue" class="btn btn-primary" data-dismiss="modal" onclick="shareposttomyfriends(${sessionScope.user.id},'${sessionScope.user.name}',<%=request.getParameter("postid")%>);" >Share</button-->
        <button type="button" id="sharepostblue" class="btn btn-primary"   >Share</button>
      </div>    
    </div>
  </div>
</div>
      
         <script>
           $(function () {
    $("#sharepostblue").on('click', function() {
           $('#myModal').on('hidden.bs.modal', function (e) {
                   shareposttomyfriends(${sessionScope.user.id},'${sessionScope.user.name}',<%=request.getParameter("postid")%>);
                  });
       $('#myModal').modal('hide');
          // $('#mohitmodal').html('');
 
});

    

      </script>