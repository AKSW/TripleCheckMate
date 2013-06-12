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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.aksw.TripleCheckMate.shared.evaluate.ClassItem;
import org.aksw.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.TripleCheckMate.shared.evaluate.EvaluateItem;
import org.aksw.TripleCheckMate.shared.evaluate.EvaluateResource;
import org.aksw.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.TripleCheckMate.shared.storage.StorageService;
import org.aksw.TripleCheckMate.shared.storage.exception.StorageServiceException;

public class JDBCStorageService extends StorageService {

	private String JDBC_DRIVER = "org.h2.Driver";
	private String DB_URL = "jdbc:h2:evaluation_db";
	private String USER = "sa";
	private String PASS = "";

	public JDBCStorageService(String driver, String dburl, String username,
			String password) {
		JDBC_DRIVER = driver;
		DB_URL = dburl;
		USER = username;
		PASS = password;

	}

	private void executeUpdateQuery(String query)
			throws StorageServiceException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			// Save resource
			st = conn.prepareStatement(query);
			st.executeUpdate();
			conn.commit();

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
	}

	private long getNumberFromQuery(String query, String fieldName,
			boolean isLong) throws StorageServiceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			// Save resource
			st = conn.prepareStatement(query);
			rs = st.executeQuery();
			if (rs.next()) {
				if (rs.isLast()) {
					if (isLong == true)
						return rs.getLong(fieldName);
					else
						return rs.getInt(fieldName);
				}
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		return 0;
	}

	private void updateUserStatistics(long userID)
			throws StorageServiceException {
		String qCountRes = " SELECT COUNT( r.rid ) AS total "
				+ " FROM evaluated_resource r INNER JOIN evaluation_session s ON (r.sid = s.sid) "
				+ " WHERE s.uid = " + userID;
		long cntRes = getNumberFromQuery(qCountRes, "total", true);

		String qCountTrpl = "SELECT COUNT( d.eid ) AS total "
				+ " FROM evaluated_resource_details d "
				+ " INNER JOIN evaluated_resource r  ON (d.rid = r.rid) "
				+ " INNER JOIN evaluation_session s ON (r.sid = s.sid) "
				+ " WHERE s.uid = " + userID;
		long cntTrpl = getNumberFromQuery(qCountTrpl, "total", true);

		String qCountDistinct = "SELECT count(DISTINCT d.error_id)  AS total "
				+ " FROM evaluated_resource_details d "
				+ " INNER JOIN evaluated_resource r  ON (d.rid = r.rid) "
				+ " INNER JOIN evaluation_session s ON (r.sid = s.sid) "
				+ " WHERE s.uid = " + userID;
		long cntDistinct = getNumberFromQuery(qCountDistinct, "total", true);

		String userUpdateQuery = "UPDATE users SET  " + " statR=" + cntRes
				+ ", " + " statT=" + cntTrpl + ", " + " statD=" + cntDistinct
				+ " " + " WHERE uid = " + userID;
		executeUpdateQuery(userUpdateQuery);
	}

	public int saveEvaluation(long sessionID, EvaluateResource item)
			throws StorageServiceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			// Save resource
			st = conn
					.prepareStatement("INSERT INTO evaluated_resource ( sid, resource, comments, class, correct) "
							+ " VALUES(?,?,?,?,?)");
			st.setLong(1, sessionID);
			st.setString(2, item.about);
			st.setString(3, item.comments);
			st.setString(4, item.classType);
			st.setInt(5, item.valid ? 1 : 0);
			st.executeUpdate();

			// Get latest resource id
			st = conn
					.prepareStatement("SELECT rid FROM evaluated_resource where sid = ? AND resource LIKE ? ");
			st.setLong(1, sessionID);
			st.setString(2, item.about);
			rs = st.executeQuery();
			long rid = 0;
			if (rs.next()) {
				if (rs.isLast()) {
					rid = rs.getLong("rid");
				}
			}

			st = conn
					.prepareStatement("INSERT INTO evaluated_resource_details (rid, predicate, object, error_id, comment) "
							+ " VALUES(?,?,?,?,?)");

			for (EvaluateItem i : item.items) {
				st.setLong(1, rid);
				st.setString(2, i.P.toString());
				st.setString(3, i.O.toString());
				st.setLong(4, i.errorType);
				st.setString(5, i.comments);
				st.executeUpdate();
			}

			conn.commit();

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		// Update user statistics
		String q = "select uid from evaluation_session where sid = "
				+ sessionID;
		long uid = getNumberFromQuery(q, "uid", true);
		updateUserStatistics(uid);
		return 1;
	}

	public long createAndGetSession(long userID, long campaignID)
			throws StorageServiceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Timestamp time = new Timestamp(Calendar.getInstance().getTime()
				.getTime());
		long sessionID = 0;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			// Save resource
			st = conn
					.prepareStatement("INSERT INTO evaluation_session ( cid, uid, timestamp) "
							+ " VALUES(?,?,?)");
			st.setLong(1, campaignID);
			st.setLong(2, userID);
			st.setTimestamp(3, time);
			st.executeUpdate();

			// Get latest resource id
			st = conn
					.prepareStatement("SELECT sid FROM evaluation_session where cid = ? AND uid = ? ORDER BY timestamp DESC LIMIT 1 ");
			st.setLong(1, campaignID);
			st.setLong(2, userID);
			//st.setTimestamp(3, time);
			rs = st.executeQuery();

			if (rs.next()) {
				if (rs.isLast()) {
					sessionID = rs.getLong("sid");
				}
			}

			conn.commit();

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		return sessionID;
	}

	public UserRecord getUserRecord(String googleID)
			throws StorageServiceException {

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		UserRecord user = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			st = conn
					.prepareStatement("SELECT * FROM users WHERE googleid like ?");

			st.setString(1, googleID);
			rs = st.executeQuery();

			if (rs.next()) {
				if (rs.isLast()) {
					long id = rs.getLong("uid");
					String name = rs.getString("name");
					String pic = rs.getString("picture");
					String prof = rs.getString("profile");
					int statR = rs.getInt("statR");
					int statT = rs.getInt("statT");
					int statD = rs.getInt("statD");
					user = new UserRecord(id, googleID, name, pic, prof);
					user.updateStatistics(statR, statT, statD);
				}
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		return user;
	}

	public int saveUserRecord(UserRecord user) throws StorageServiceException {

		Connection conn = null;
		PreparedStatement st = null;
		Boolean insertOrUpdate = getUserRecord(user.googleID) == null;
		int retVal = 0;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			if (insertOrUpdate) {
				st = conn
						.prepareStatement("INSERT INTO users ( googleid, name, picture, profile) VALUES(?,?,?,?)");
				st.setString(1, user.googleID);
				st.setString(2, user.name);
				st.setString(3, user.picture);
				st.setString(4, user.profile);
			} else {
				st = conn
						.prepareStatement("UPDATE users set name = ? , picture = ?, profile = ? where googleid = ? ");
				st.setString(1, user.name);
				st.setString(2, user.picture);
				st.setString(3, user.profile);
				st.setString(4, user.googleID);
			}

			st.executeUpdate();
			conn.commit();

			retVal = 1;

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		return retVal;
	}

	public int saveClasses(List<ClassItem> items)
			throws StorageServiceException {
		Connection conn = null;
		PreparedStatement st = null;
		int retVal = 0, i = 0;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			st = conn
					.prepareStatement("INSERT INTO classes ( cid, cname, curi, cparent, count_cache, is_leaf) "
							+ " VALUES(?,?,?,?,?,?)");

			for (ClassItem item : items) {
				st.setLong(1, item.ID);
				st.setString(2, item.name);
				st.setString(3, item.uri);
				st.setLong(4, item.parentID);
				st.setLong(5, item.count);
				st.setInt(6, item.isLeaf ? 1 : 0);

				st.executeUpdate();
			}
			conn.commit();
			retVal = i;
		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		return retVal;
	}

	public List<ClassItem> getClassChildren(long classID)
			throws StorageServiceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<ClassItem> items = new ArrayList<ClassItem>();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			st = conn
					.prepareStatement("SELECT * FROM classes WHERE cparent = ? ORDER BY cname");

			st.setLong(1, classID);
			rs = st.executeQuery();

			while (rs.next()) {
				long id = rs.getLong("cid");
				String name = rs.getString("cname");
				String uri = rs.getString("curi");
				long cparent = rs.getLong("cparent");
				long count = rs.getLong("count_cache");
				int isLeaf = rs.getInt("is_leaf");

				items.add(new ClassItem(id, name, uri, cparent, count,
						isLeaf == 1));
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		return items;

	}

	public int updateClassCount(long id, long count)
			throws StorageServiceException {
		String query = "UPDATE classes SET count_cache = " + count
				+ " WHERE cid = " + id;
		executeUpdateQuery(query);
		return 1;
	}

	public List<String> getExistingEvaluations(long userID, String classType)
			throws StorageServiceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<String> items = new ArrayList<String>();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			st = conn
					.prepareStatement(" SELECT r.resource AS 'resource', RAND( ) AS 'rnd' "
							+ " FROM evaluated_resource r "
							+ " INNER JOIN evaluation_session s ON ( r.sid = s.sid ) "
							+ " WHERE s.uid <> ? AND r.class LIKE ? "
							+ " GROUP BY r.resource "
							+ " HAVING COUNT( r.resource ) <2 "
							+ " ORDER BY rnd " + " LIMIT 5 ");
			st.setLong(1, userID);
			st.setString(2, classType);
			rs = st.executeQuery();

			while (rs.next()) {
				items.add(rs.getString("resource"));
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		return items;
	}

	public List<ErrorItem> getErrorChildren(long errorID)
			throws StorageServiceException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		List<ErrorItem> items = new LinkedList<ErrorItem>();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String where = (errorID == -1) ? "" : "WHERE error_parent = " + errorID ;
			st = conn.prepareStatement("SELECT * FROM errors " + where
					+ " ORDER BY error_parent, error_title");

			// st.setLong(1, errorID);
			rs = st.executeQuery();

			while (rs.next()) {
				long id = rs.getLong("error_id");
				String title = rs.getString("error_title");
				String exampleURI = rs.getString("example_uri");
				String exampleN3 = rs.getString("example_n3");
				String exampleDesc = rs.getString("description");
				long parent = rs.getLong("error_parent");
				int isLeaf = rs.getInt("is_leaf");

				items.add(new ErrorItem(id, title, exampleURI, exampleN3,
						exampleDesc, parent, isLeaf == 1));
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		return items;

	}

	public List<UserRecord> getUSerStatistics(long uid)
			throws StorageServiceException {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<UserRecord> items = new ArrayList<UserRecord>();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String uidWhere = uid == -1 ? "" : " AND u.uid = " + uid + " ";
			st = conn.prepareStatement(" SELECT * FROM users u "
					+ " WHERE statR > 0 " + uidWhere
					+ " ORDER BY u.statR DESC, u.statT DESC, u.statD DESC ");

			rs = st.executeQuery();

			while (rs.next()) {
				long id = rs.getLong("uid");
				String googleID = rs.getString("googleID");
				String name = rs.getString("name");
				String pic = rs.getString("picture");
				String prof = rs.getString("profile");
				int statR = rs.getInt("statR");
				int statT = rs.getInt("statT");
				int statD = rs.getInt("statD");
				UserRecord user = new UserRecord(id, googleID, name, pic, prof);
				user.updateStatistics(statR, statT, statD);
				items.add(user);
			}

		} catch (SQLException se) {
			// Handle errors for JDBC
			if (conn != null) {
				try {
					if (conn.getAutoCommit() == false)
						conn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
					throw new StorageServiceException(se2.getMessage());
				}
			}
			se.printStackTrace();
			throw new StorageServiceException(se.getMessage());
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new StorageServiceException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		return items;
	}

}
