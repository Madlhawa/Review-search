package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class readProperties {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File configFile = new File("config.properties");
		 
		try {
		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		 
		    String host = props.getProperty("host");
		    String core = props.getProperty("core");
		 
		    System.out.print("Host name is: " + host);
		    System.out.print("\nCore name is: " + core);
		    
		    reader.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}

	}

}
