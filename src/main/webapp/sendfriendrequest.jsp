<%@page import="p1.User"%>
<%@page import="p1.SamajUtils"%>
<jsp:include page="print.jsp"/>


 

<%

if(request.getParameter("sender")!=null){
SamajUtils.addPendingRequest(request.getParameter("sender").toString(),request.getParameter("sendername").toString(), request.getParameter("receiver").toString());
%>
<button type="button" class="btn btn-primary btn-lg" style="border-radius: 24px;">Friend Request Sent</button>
<%
}

%>