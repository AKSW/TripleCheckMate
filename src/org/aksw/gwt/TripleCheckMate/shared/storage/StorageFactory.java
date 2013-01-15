/*******************************************************************************
 * Copyright 2013 Agile Knowledge Engineering and Semantic Web (AKSW) Group
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
