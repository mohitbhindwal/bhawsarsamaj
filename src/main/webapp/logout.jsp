 
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
request.getSession().invalidate();
response.sendRedirect(request.getContextPath() + "/login.jsp"); 
%>

<script  type="text/javascript">
    window.history.forward();
    function noBack() { window.history.forward(); }
</script>
</HEAD>
<BODY onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">