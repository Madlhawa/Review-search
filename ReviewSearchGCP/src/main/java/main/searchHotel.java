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

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * Servlet implementation class searchHotel
 */
@WebServlet("/searchHotel")
public class searchHotel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchHotel() {
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
				String urlString = "http://35.245.48.179/solr/wsu";
				HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
				solr.setParser(new XMLResponseParser());
				
				SolrQuery query = new SolrQuery();
				query.set("q", "*");
				query.set("df", "Review");
				query.setFacet(true);
				query.addFacetField("Hotel_Name_str");
				query.setRows(500);
				QueryResponse res;
				try {
					res = solr.query(query);
					FacetField hotelField = (FacetField) res.getFacetField("Hotel_Name_str");
					System.out.println("hotelField : "+hotelField.getValues());
					request.setAttribute("hotelField", hotelField);
				} catch (SolrServerException e) {
					e.printStackTrace();
				}
	}
}


