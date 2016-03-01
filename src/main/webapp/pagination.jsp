<!--Navigation start -->

<%@page import="introb.DataObject"%>
<%@page import="introb.DataSet"%>
<%@page import="introb.SessioniUtils"%>
<%@page import="p1.SamajUtils"%>
<center> <div id="loading-image"></div></center>
<%

  String sql = "select * from users limit 9 ";
  DataSet ds =  SessioniUtils.query(sql);
  int i =0 , j = 0 ;
  if(ds!=null&&ds.size()>0){
      out.print("<div class=\"row\">\n");
  for(; i < ds.size();i++,j++){
      DataObject dob = ds.get(i);
      String imgsrc = SamajUtils.getImagesrcfromID(Integer.parseInt(dob.get("avtar").toString()));
  //if(j==0)out.print("<div class=\"row\">\n");%>
  <div class="col-lg-3 col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-3">
                            <span><img  class="img-rounded" style="width:100px;height:100px" src = "<%=imgsrc%>"/></span>
                        </div>
                        <div class="col-xs-9 text-right">
                            <!--div class="huge">26</div-->
                            <div><p><a  style="color: white" onclick="getbody(this,<%= dob.get("id")%>);" href="javascript:void(0);" ><%= dob.getString("name")%></a></p></div>
                        </div>
                    </div>
                </div>
                <a onclick="getbody(this,<%= dob.get("id")%>);" href="javascript:void(0);" >
                    <div class="panel-footer">
                        <span class="pull-left">View Details</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
  
  
  
  <%
      //if(j==3){out.print("</div>");j=-1;}
  }out.print("</div>");
}%>

  
 <!--Navigation end -->
 <p><p/>

 <center>
 <ul class = "pager">
    <li><a href = "#">Previous</a></li>
    <li><a href = "#">Next</a></li>
</ul>
</center>
