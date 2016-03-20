/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import introb.SessioniUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author m
 */
public class Login extends HttpServlet {

    public static String imagefolder = null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        imagefolder   = config.getServletContext().getInitParameter("file-upload");
    }

    
    
    
    
      protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         RequestDispatcher dlr=request.getRequestDispatcher("login.jsp"); 
             System.out.println("p1.Login.processRequest()"+request.getParameter("rememberme"));
                 if(request.getParameter("Username")!=null&&request.getParameter("Password")!=null)
                 {
                    User user = User.validate(request.getParameter("Username"),request.getParameter("Password"));
                     if(user!=null){                  // login
                         rememberme(request,response);
                         HttpSession session=request.getSession();  
                         user.setSessionId(session.getId());
                         user.loadFriendsOFUser();
                         user.loadFriendsRequestOFUser();
                         session.setAttribute("user",user);
                         UserPosts userposts = new UserPosts(user);
                         userposts.loadPost(5,true);
                         session.setAttribute("userposts", userposts);
                         request.setAttribute("editmode", "true");
                         request.setAttribute("isfriends", "accepted");
                          RequestDispatcher dr=request.getRequestDispatcher("index.jsp"); 
                         dr.forward(request, response);
                     }
                     else{
                     System.out.println("p1.Login.processRequest()redirect");
                     request.setAttribute("error","Invalid UserName or Password");
                     dlr.forward(request, response);
                    }
                 }
                 else{
                 System.out.println("p1.Login.processRequest()redirect3");
                 request.setAttribute("error","Invalid UserName or Password");
                 dlr.forward(request, response);
                 }
     
    }
      
      public void rememberme(HttpServletRequest request, HttpServletResponse response){
      
          boolean rememberme = false;
          if(request.getParameter("rememberme")!=null)
          rememberme = true;
          
              
          String userid = request.getParameter("Username");
          String password = request.getParameter("Password");
                  
                  System.out.println(" Login.java rememberme = "+request.getParameter("rememberme"));
          Cookie[] cookies = request.getCookies(); 
          boolean foundCookie = false;
          if(cookies!=null)
          for (int i = 0; i < cookies.length; i++) {
              Cookie c = cookies[i];
              System.out.println("--> name = "+c.getName()+" value = "+c.getValue());
              if (c.getName().equals("bhawsarid")) {
                  String userId = c.getValue();
                  foundCookie = true;
              }
          }
          
          if(rememberme){
          Cookie cuserid = new Cookie("bhawsarid", userid);
          Cookie cpwdid = new Cookie("bhawsarpwd", password);
          Cookie cRemember = new Cookie("bhawsarrem","checked");
          cuserid.setMaxAge(24*60*60);
          cpwdid.setMaxAge(24*60*60);
          cRemember.setMaxAge(24*60*60);
          response.addCookie(cuserid);
          response.addCookie(cpwdid);
          response.addCookie(cRemember);
          }
          
          if(!rememberme&&foundCookie){
          Cookie cuserid = new Cookie("bhawsarid", null);
          Cookie cpwdid = new Cookie("bhawsarpwd", null);
          Cookie cRemember = new Cookie("bhawsarrem",null);
          cuserid.setMaxAge(0);
          cpwdid.setMaxAge(0);
          cRemember.setMaxAge(0);
          response.addCookie(cuserid);
          response.addCookie(cpwdid);
          response.addCookie(cRemember);
          
          
          }
      
      }

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
