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
import org.apache.solr.client.solrj.response.FacetField;
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
				System.out.println("-- Running search.java Post --");
				
				//get server parameters from the server
				/*
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
				}*/
				
				//saving received parameters to variables
						String squery = request.getParameter("squery");
						String hotel = request.getParameter("hotel").substring(0, request.getParameter("hotel").lastIndexOf('(')-1);
						//String hotel = "K K Hotel George";
						System.out.println("Receiving parameters...");
						System.out.println("Query: "+squery);
						System.out.println("hotel: "+hotel);
						
						//connecting to solr instance
						System.out.println("\nConnecting to solr");
						String urlString = "http://35.245.48.179/solr/wsu";
						HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
						solr.setParser(new XMLResponseParser());
						System.out.println("Done connecting");
						
						//excecuting solr query
						System.out.println("\nExecuting solr query..");
						
						SolrQuery query = new SolrQuery();
						query.set("q", squery);
						//query.set("fq", "Hotel_Name:Britannia International Hotel Canary Wharf");
						//query.addFilterQuery("Hotel_Name:Britannia International Hotel Canary Wharf");
						query.set("df", "Review");
						query.setFacet(true);
						query.addFacetField("Hotel_Name_str");
						
						//Edimax
						query.set("defType", "edismax"); 
						query.set("mm", 2);
						
						query.setRows(5000);

						System.out.println("\nGetting results from solr");
						QueryResponse res;
						
						int is_bad = 0, is_good = 0, rows = 0;
						String is_positive = "images/negative-vote.png";
						try {
							res = solr.query(query);
							float qtime = (float) (res.getElapsedTime()/1000.0);
							SolrDocumentList docList = res.getResults();
							FacetField hotelField = (FacetField) res.getFacetField("Hotel_Name_str");
							
							for (int i = 0; i < docList.size(); ++i) {
								SolrDocument doc = docList.get(i);
								if(String.valueOf(doc.getFieldValue("Hotel_Name")).contains(hotel)) {
									System.out.println(doc.getFieldValue("Hotel_Name"));
									System.out.println("\t"+doc.getFieldValue("Review"));
									System.out.println("\t"+doc.getFieldValue("is_bad_review"));
									if(String.valueOf(doc.getFieldValue("is_bad_review")).contains("1"))
										is_bad++;
									else 
										is_good++;
									
									rows++;
									System.out.println("\tis_bad:"+is_bad+" is_good:"+is_good+" rows:"+rows);
								}
							}

							System.out.println("squery : "+squery);
							System.out.println("queryTime : "+qtime);
							System.out.println("is_bad : "+is_bad);
							System.out.println("is_good : "+is_good);
							System.out.println("rows : "+rows);
							System.out.println("hotel: "+hotel);
							System.out.println("hotelField : "+hotelField.getValues());
							
							if(is_good>is_bad)
								is_positive = "images/positive-vote.png";
								
							
							System.out.println("\nSending values back to find.jsp");
							request.setAttribute("squery", squery);
							request.setAttribute("docList", docList);
							request.setAttribute("qtime", qtime);
							request.setAttribute("is_bad", is_bad);
							request.setAttribute("is_good", is_good);
							request.setAttribute("rows", rows);
							request.setAttribute("hotel", hotel);
							request.setAttribute("is_positive", is_positive);
							
							System.out.println("Redirecting to Find.jsp");
							request.getRequestDispatcher("Find.jsp").forward(request, response);
							
						} catch (SolrServerException e) {
							e.printStackTrace();
						}
	}
}