package org.aksw.gwt.TripleCheckMate.client.requests;

import java.util.List;

import org.aksw.gwt.TripleCheckMate.shared.evaluate.UserRecord;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserRequestAsync {
	public void getUserInfo(String userGoogleID, AsyncCallback<UserRecord> callback);
	public void saveUserInfo(UserRecord user, AsyncCallback<Long> callback);
	
	void createAndGetSession(long userID, long campaignID, AsyncCallback<Long> callback);
	void getUSerStatistics(long userID, AsyncCallback<List<UserRecord>> callback);

}

