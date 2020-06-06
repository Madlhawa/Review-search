package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class readSettings
 */
@WebServlet("/readSettings")
public class readSettings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public readSettings() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String host = null, core = null;
		
		File configFile = new File("config.properties");
		 
		try {
		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		 
		    host = props.getProperty("host");
		    core = props.getProperty("core");
		 
		    System.out.print("Host name is: " + host);
		    System.out.print("\nCore name is: " + core);
		    
		    reader.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
		
		request.setAttribute("host", host);
		request.setAttribute("core", core);
		
		System.out.println("Redirecting to configure.jsp");
		request.getRequestDispatcher("configure.jsp").forward(request, response);
	}

}
