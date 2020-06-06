package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
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
		System.out.println("-- Running search.java --");
		
		//saving received parameters to variables
				String squery = request.getParameter("squery");
				System.out.println("Receiving parameters...");
				System.out.println("Query: "+squery);
				
				//connecting to solr instance
				System.out.println("\nConnecting to solr");
				String urlString = "http://35.186.165.64/solr/wsu";
				HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
				solr.setParser(new XMLResponseParser());
				System.out.println("Done connecting");
				
				//excecuting solr query
				System.out.println("\nExecuting solr query..");
				
				SolrQuery query = new SolrQuery();
				query.set("q", squery);
				query.set("df", "Negative_Review");

				System.out.println("\nGetting results from solr");
				QueryResponse res;
				try {
					res = solr.query(query);
					float qtime = (float) (res.getElapsedTime()/1000.0);
					SolrDocumentList docList = res.getResults();
					
					for (int i = 0; i < docList.size(); ++i) {
						SolrDocument doc = docList.get(i);
						System.out.println(doc.getFieldValue("Hotel_Name"));
						System.out.println("\t"+doc.getFieldValue("Negative_Review"));
					}

					System.out.println("squery : "+squery);
					System.out.println("queryTime : "+qtime);
					
					System.out.println("\nSending values back to search.jsp");
					request.setAttribute("userQuery", squery);
					request.setAttribute("docList", docList);
					request.setAttribute("qtime", qtime);
					
					System.out.println("Redirecting to Find.jsp");
					request.getRequestDispatcher("Find.jsp").forward(request, response);
					
					
				} catch (SolrServerException e) {
					e.printStackTrace();
				}
	}

}
