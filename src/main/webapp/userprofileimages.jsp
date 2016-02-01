<%@page import="introb.SessioniUtils"%>
<jsp:include page="print.jsp"/>
<%
    String divname = request.getParameter("divname");
    String colname = "";
    if(divname.equals("myavtardiv"))
    colname = "avtar";
    else if(divname.equals("profilepicdiv1"))
        colname="pic1";
    else if(divname.equals("profilepicdiv2"))
        colname="pic2";
    else if(divname.equals("profilepicdiv3"))
        colname="pic3";
    
    int imageid = Integer.parseInt(request.getParameter("imageid").toString());
    
    String sql = "Update users set "+colname+"="+imageid+" where id="+request.getParameter("userid").toString();
    
    System.out.print("***********"+sql);
SessioniUtils.executeUpdate(sql);







%>