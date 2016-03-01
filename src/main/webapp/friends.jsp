<%-- 
    Document   : friends
    Created on : 24 Feb, 2016, 11:53:49 PM
    Author     : m
--%>

<%@page import="java.util.LinkedHashMap"%>
<%@page import="p1.User"%>
<%@page import="introb.DataObject"%>
<%@page import="introb.DataSet"%>
<%@page import="introb.SessioniUtils"%>
<%@page import="p1.SamajUtils"%>
 
     
<%
  LinkedHashMap<Long, User> friends =  ((User)session.getAttribute("user")).getFriends();
  if(friends.size()==0)out.print("<h1><I>No Friends</I></h1>");
 
 
 
  if(friends!=null&&friends.size()>0){
      out.print("</p><table class=\"table\" style=\"padding: 0px;border: 0px\"><tbody>");
  for( Long keys : friends.keySet()){
       User user = friends.get(keys);
      String imgsrc = SamajUtils.getImagesrcfromID(Integer.parseInt(user.getAvtaroid().toString()));
 %>
  <tr style="padding: 0px;border: 0px;"  ><td style="padding: 0px;border: 0px;margin: 0px">
  <div class="col-lg-3 col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-3">
                            <span><img  class="img-rounded" style="width:100px;height:100px" src = "<%=imgsrc%>"/></span>
                        </div>
                        <div class="col-xs-9 text-right">
                            <!--div class="huge">26</div-->
                            <div><p><a  style="color: white" onclick="getbody(this,<%= user.getId()%>);" href="javascript:void(0);" ><%= user.getName()%></a></p></div>
                        </div>
                    </div>
                </div>
                <a onclick="getbody(this,<%= user.getId()%>);" href="javascript:void(0);" >
                    <div class="panel-footer">
                        <button id="block" class="btn btn-primary">Block</button>
                    </div>
                </a>
            </div>
        </div>
 </td></tr>
   
  
  <%
      //if(j==3){out.print("</div>");j=-1;}
  }out.print("</tbody></table>");
}%>

  