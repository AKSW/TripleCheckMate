package org.aksw.gwt.TripleCheckMate.server.requests;

import java.util.List;

import org.aksw.gwt.TripleCheckMate.client.requests.UserRequest;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.gwt.TripleCheckMate.shared.storage.StorageFactory;
import org.aksw.gwt.TripleCheckMate.shared.storage.StorageService;
import org.aksw.gwt.TripleCheckMate.shared.storage.exception.StorageServiceException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserRequestImpl  extends RemoteServiceServlet implements UserRequest{

	/**
	 * Auto-generated for serialization
	 */
	private static final long serialVersionUID = 8458992141997562528L;
	private String getAbsolutePath() {
		return getServletContext().getRealPath("/");
	}

	@Override
	public UserRecord getUserInfo(String userGoogleID) throws StorageServiceException {

		String absolutePath = getServletContext().getRealPath("/");
		StorageService service = StorageFactory.create(absolutePath);
		return service.getUserRecord(userGoogleID);
	}

	@Override
	public long saveUserInfo(UserRecord user) throws StorageServiceException {

		String absolutePath = getServletContext().getRealPath("/");
		StorageService service = StorageFactory.create(absolutePath);
		service.saveUserRecord(user);
		UserRecord u = service.getUserRecord(user.googleID);
		return u.userID;
	}
	
	
	@Override
	public long createAndGetSession(long userID, long campaignID)
			throws StorageServiceException {
		StorageService service = StorageFactory.create(getAbsolutePath());
		return service.createAndGetSession(userID, campaignID);
	}

	@Override
	public List<UserRecord> getUSerStatistics(long userID)
			throws StorageServiceException {
		StorageService service = StorageFactory.create(getAbsolutePath());
		return service.getUSerStatistics(userID);
	}

}
