package org.aksw.gwt.TripleCheckMate.shared.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.aksw.gwt.TripleCheckMate.server.storage.JDBCStorageService;


public class StorageFactory {
	/**
	 * Factory creation method for storage service creation
	 * @param absolutePath the absolute path of the web root folder
	 * @return the appropriate StorageService
	 */
	public static StorageService create(String basePath) {

		String path =  basePath + "/";
		String driver = "org.h2.Driver";
		String database = "jdbc:h2:";
		String databasePath = "db/evaluation";
		String dbuser = "sa";
		String dbpass = "";

		try {
			
			Properties prop = new Properties();
			prop.load(new FileInputStream(path	+ "db/storage.properties"));

			driver = prop.getProperty("driver");
			database = prop.getProperty("database");
			databasePath = prop.getProperty("databasePath");
			if (!databasePath.startsWith("/", 0))
				databasePath = path + databasePath;
			dbuser = prop.getProperty("dbuser");
			dbpass = prop.getProperty("dbpass");

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return new JDBCStorageService(driver, database+databasePath, dbuser, dbpass);
	}

}
