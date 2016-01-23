/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import javax.servlet.ServletContext;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Servlet implementation class AjaxController2
 */
 
public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private String filePath;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileServlet() {
        super();
       
        // TODO Auto-generated constructor stub
    }
    
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public void init() throws ServletException {
 
        // Define base path somehow. You can define it as init-param of the servlet.
        this.filePath = "";
 
        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\files".
        // In UNIX, it is just straightforward "/files".
    }
	
	protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In do get");
 
        // Get requested file by path info.
        String requestedFile = request.getPathInfo();
            System.out.println("requestedFile="+requestedFile);
        // Check if file is actually supplied to the request URI.
        if (requestedFile == null) {
            // Do your thing if the file is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
 
        // Decode the file name (might contain spaces and on) and prepare file object.
        File file = new File(requestedFile);
            System.out.println("p1.FileServlet.doGet()"+file.getAbsolutePath());
        // Check if file actually exists in filesystem.
        if (!file.exists()) {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
 
        // Get content type by filename.
        String contentType = getServletContext().getMimeType(file.getName());
 
        // If content type is unknown, then set the default value.
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        // To add new content types, add new mime-mapping entry in web.xml.
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
 
        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
 
        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
 
        try {
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
 
            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
	}
 
	protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In do post"+request.getAttribute("filepath"));
		    ServletContext cntx =    getServletContext();
      // Get the absolute path of the image
      String filename = request.getAttribute("filepath").toString();
      // retrieve mimeType dynamically
      String mime = cntx.getMimeType(filename);
      if (mime == null) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return;
      }

      response.setContentType(mime);
      File file = new File(filename);
      response.setContentLength((int)file.length());

      FileInputStream in = new FileInputStream(file);
      OutputStream out = response.getOutputStream();

      // Copy the contents of the file to the output stream
       byte[] buf = new byte[1024];
       int count = 0;
       while ((count = in.read(buf)) >= 0) {
         out.write(buf, 0, count);
      }
       System.out.println("In do asdasdasdadsaddd" );
  //  out.close();
    in.close();

                       
                       
                       
                       
	}
        
        
        
        
        
        
	
 
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }
	
	
}

