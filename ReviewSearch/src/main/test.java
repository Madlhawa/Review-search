package main;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String squery = "wifi";
		
		System.out.println("\nConnecting to solr");
		String urlString = "http://35.186.165.64/solr/wsu";
		HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
		solr.setParser(new XMLResponseParser());
		System.out.println("Done connecting");
		
		SolrQuery query = new SolrQuery();
		query.set("q", squery);
		query.set("df", "Negative_Review");
		
		System.out.println("\nGetting results from solr");
		QueryResponse res;
		
		try {
			res = solr.query(query);
			SolrDocumentList docList = res.getResults();
			
			for (int i = 0; i < docList.size(); ++i) {
				SolrDocument doc = docList.get(i);
				System.out.println(doc.getFieldValue("Negative_Review"));
				System.out.println(doc.getFieldValue("Hotel_Name"));
				System.out.println(" ");
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
