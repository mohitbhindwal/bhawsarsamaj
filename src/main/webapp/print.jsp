<%@ page import="java.io.*,java.util.*" %>

<% 
    System.out.println("-----------------param received----------------");
    Enumeration headerNames = request.getParameterNames(); 
while(headerNames.hasMoreElements()) { 
    String paramName = (String)headerNames.nextElement(); 
    String paramValue = request.getParameter(paramName); 
    System.out.println(paramName+"["+paramValue+"]");
} 
System.out.println("-----------------------------------------------");
%>
