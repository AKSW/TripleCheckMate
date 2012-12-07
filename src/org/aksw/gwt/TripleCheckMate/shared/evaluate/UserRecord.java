package org.aksw.gwt.TripleCheckMate.shared.evaluate;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class UserRecord implements Comparable<UserRecord> ,Serializable{
	/**
	 * Auto-generated for serialization
	 */
	private static final long serialVersionUID = 8871828625070002603L;
	
	public long userID = -1;
	public String googleID = "";
	public String name = "";
	public String picture = "";
	public String profile = "";
	
	public int recordCount = 0 ;
	public int errorCount = 0;
	public int distinctErrorCount = 0;
	
	
	public UserRecord(){
	}
	public UserRecord(long userID, String googleID, String name, String picture, String profile){
		this.userID = userID;
		this.googleID = googleID;
		this.name = name;
		this.picture = picture;
		this.profile = profile;
	}
	public UserRecord(UserRecord u){
		update(u);
	}
	
	public void update(UserRecord u){
		this.userID = u.userID;
		this.googleID = u.googleID;
		this.name = u.name;
		this.picture = u.picture;
		this.profile = u.profile;
		this.recordCount = u.recordCount ;
		this.errorCount = u.errorCount;
		this.distinctErrorCount = u.distinctErrorCount;
	}
	
	public void updateStatistics(int rc, int ec, int dc){
		this.recordCount = rc;
		this.errorCount = ec;
		this.distinctErrorCount = dc;
	}
	
	public static final ProvidesKey<UserRecord> KEY_PROVIDER = new ProvidesKey<UserRecord>() {
	      public Object getKey(UserRecord item) {
	        return item == null ? null : item.userID;
	      }
	    };

	public String toHTMLString(){
		String img = (picture.equals("")) ? "" : "<img width=\"20\" height=\"20\" src=\"" + picture + "\" class =\"user-display-picture\"/>";
		return "<div class=\"user-display\">" + img + name + "</div>";
	}
	@Override
	public int compareTo(UserRecord arg0) {
		// TODO Auto-generated method stub
		return name.compareTo(arg0.name);
	}

}
