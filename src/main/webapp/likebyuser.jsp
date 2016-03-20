<%@page import="p1.Login"%>
<%@page import="introb.DataObject"%>
<%@page import="introb.SessioniUtils"%>
<%@page import="introb.DataSet"%>
<%@page import="p1.SamajUtils"%>
<%@page import="java.util.LinkedHashMap"%>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Like By</h4>
      </div>
        <div class="modal-body">
            
            
     
<%
 

   long postid = Long.parseLong(request.getParameter("postid").trim().toString());
   boolean frompost = Boolean.valueOf(request.getParameter("frompost").trim().toString());
   LinkedHashMap<Long,String> map =  SamajUtils.getaddAllLikeByOFPost(postid,frompost);
 System.out.print("######"+map);
   if(map.size()==0)out.print("<h1><B><I>No Like<I><B></h1>");
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

            <a class="list-group-item bts-dynamic-item" onclick="getbody(this,<%=key%>);" 
               href="javascript:void(0);"><span><img style="width:50px;height:50px" 
                src="<%=title%>">
                </span><span><i><b><%=map.get(key).toString()%><b><i></i></b></b></i></span></a>
            
            
   <%
   }
%> 
 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <!--button type="button" class="btn btn-primary">Save changes</button-->
      </div>
    </div>
  </div>
</div>