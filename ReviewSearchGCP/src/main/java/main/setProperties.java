package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class setProperties {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File configFile = new File("config.properties");
		 
		try {
		    Properties props = new Properties();
		    
		    props.setProperty("host", "35.245.226.199");
		    props.setProperty("core", "wsu");
		    
		    FileWriter writer = new FileWriter(configFile);
		    props.store(writer, "host settings");
		    writer.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
		
	}

}
