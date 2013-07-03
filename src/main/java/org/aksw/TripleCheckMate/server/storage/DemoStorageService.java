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
package org.aksw.TripleCheckMate.server.storage;

import org.aksw.TripleCheckMate.shared.evaluate.ClassItem;
import org.aksw.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.TripleCheckMate.shared.evaluate.EvaluateResource;
import org.aksw.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.TripleCheckMate.shared.exceptions.StorageServiceException;
import org.aksw.TripleCheckMate.shared.sparql.Endpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoStorageService extends StorageService {

    //TODO for now we use a db connection in test to get classes and errors
    private String JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:evaluation_db";
    private String USER = "sa";
    private String PASS = "";

    public DemoStorageService(String driver, String dburl, String username,
                              String password) {
        JDBC_DRIVER = driver;
        DB_URL = dburl;
        USER = username;
        PASS = password;

    }

    public List<Endpoint> getCampaigns() throws StorageServiceException {
        Endpoint e = new Endpoint(
                0,
                "http://live.dbpedia.org/sparql",
                "http://dbpedia.org",
                "live.dbpedia.org/sparql");
        return Arrays.asList(e);
    }

    public int saveEvaluation(long sessionID, EvaluateResource item) throws StorageServiceException {
        throw new StorageServiceException("This is a session is for demonstration purposes only and evaluations are not saved. Press skip to evaluate another resource.");
    }

    public long createAndGetSession(long userID, long campaignID) throws StorageServiceException {
        return 1;
    }

    public List<String> getExistingEvaluations(long userID, String classType) throws StorageServiceException {
        ArrayList<String> ar = new ArrayList<String>();
        ar.add("http://dbpedia.org/resource/Berlin");
        ar.add("http://dbpedia.org/resource/Leipzig");
        ar.add("http://dbpedia.org/resource/Thessaloniki");
        return ar;
    }

    public UserRecord getUserRecord(String userID) throws StorageServiceException {
        return new UserRecord(
                0,
                "0",
                "Demo User",
                "https://ssl.gstatic.com/s2/profiles/images/silhouette96.png",
                "http://example.com");
    }

    public int saveUserRecord(UserRecord user) throws StorageServiceException {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int saveClasses(List<ClassItem> items) throws StorageServiceException {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ClassItem> getClassChildren(long classID) throws StorageServiceException {
        StorageService ss = new JDBCStorageService(JDBC_DRIVER, DB_URL, USER, PASS);
        return ss.getClassChildren(classID);
    }

    public int updateClassCount(long id, long count) throws StorageServiceException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ErrorItem> getErrorChildren(long errorID) throws StorageServiceException {
        StorageService ss = new JDBCStorageService(JDBC_DRIVER, DB_URL, USER, PASS);
        return ss.getErrorChildren(errorID);
    }

    public List<UserRecord> getUSerStatistics(long uid) throws StorageServiceException {
        return Arrays.asList( new UserRecord(
                                    0,
                                    "0",
                                    "Demo User",
                                    "https://ssl.gstatic.com/s2/profiles/images/silhouette96.png",
                                    "http://example.com"));
    }
}
