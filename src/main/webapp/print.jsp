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
    System.out.println("-----------------Attributes received----------------");
    Enumeration atrributes = request.getAttributeNames(); 
while(atrributes.hasMoreElements()) { 
    String anames = (String)atrributes.nextElement(); 
    
    String vnames = request.getAttribute(anames).toString(); 
    System.out.println(anames+"["+vnames+"]");
} 
System.out.println("-----------------------------------------------");
%>
