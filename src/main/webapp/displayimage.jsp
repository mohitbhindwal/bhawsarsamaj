<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="p1.SamajUtils"%>
<%
System.out.print("Display Image");
Integer imageid = Integer.valueOf(request.getParameter("imageid"));
String outpath = SamajUtils.displayImage(imageid,"D:/ramout/");
%>
<!--img alt="Image" src="/images/<%=outpath%>"-->
<!--img src="${pageContext.request.contextPath}/images/foo.png"-->
<img class='img-responsive col-xs-12' style=" width:100%; height:auto;margin-left: 0px" src="${pageContext.request.contextPath}/images/<%=outpath%>">