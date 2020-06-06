package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class setSettings
 */
@WebServlet("/setSettings")
public class setSettings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setSettings() {
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
		String host = request.getParameter("host");
		String core = request.getParameter("core");
		
		System.out.println("Parameter recieved;");
		System.out.println("Host = "+host+"\nCore = " + core);
		
		File configFile = new File("config.properties");
		 
		try {
		    Properties props = new Properties();
		    
		    props.setProperty("host", host);
		    props.setProperty("core", core);
		    
		    FileWriter writer = new FileWriter(configFile);
		    props.store(writer, "host settings");
		    writer.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
		
		request.setAttribute("host", host);
		request.setAttribute("core", core);
		
		System.out.println("--------------------------------- \nSending Parameters;");
		System.out.println("Host = "+host+"\nCore = " + core);
		
		System.out.println("Redirecting to configure.jsp");
		request.getRequestDispatcher("configure.jsp").forward(request, response);
	}

}
