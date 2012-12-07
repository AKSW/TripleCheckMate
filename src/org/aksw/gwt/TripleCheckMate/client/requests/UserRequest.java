package org.aksw.gwt.TripleCheckMate.client.requests;

import java.util.List;

import org.aksw.gwt.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.gwt.TripleCheckMate.shared.storage.exception.StorageServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userinfo")
public interface UserRequest extends RemoteService {
	public UserRecord getUserInfo(String userGoogleID) throws StorageServiceException;
	public long saveUserInfo(UserRecord user) throws StorageServiceException;
	
	public long createAndGetSession(long userID, long campaignID) throws StorageServiceException;
	
	public List<UserRecord> getUSerStatistics(long userID) throws StorageServiceException;

}