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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author m
 */
public class Login extends HttpServlet {

      protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         RequestDispatcher dlr=request.getRequestDispatcher("login.jsp"); 
             System.out.println("p1.Login.processRequest()");
                 if(request.getParameter("Username")!=null&&request.getParameter("Password")!=null)
                 {
                    User user = User.validate(request.getParameter("Username"),request.getParameter("Password"));
                     if(user!=null){                  // login
                         HttpSession session=request.getSession();  
                         user.setSessionId(session.getId());
                         user.loadFriendsOFUser();
                         session.setAttribute("user",user);
                         UserPosts userposts = new UserPosts(user);
                         userposts.loadPost(2,true);
                         session.setAttribute("userposts", userposts);
                         request.setAttribute("editmode", "true");
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
