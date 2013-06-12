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
package org.aksw.TripleCheckMate.server.requests;

import java.util.List;

import org.aksw.TripleCheckMate.client.requests.UserRequest;
import org.aksw.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.TripleCheckMate.shared.storage.StorageFactory;
import org.aksw.TripleCheckMate.shared.storage.StorageService;
import org.aksw.TripleCheckMate.shared.storage.exception.StorageServiceException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserRequestImpl  extends RemoteServiceServlet implements UserRequest{

	/**
	 * Auto-generated for serialization
	 */
	private static final long serialVersionUID = 8458992141997562528L;
	private String getAbsolutePath() {
		return getServletContext().getRealPath("/");
	}

	public UserRecord getUserInfo(String userGoogleID) throws StorageServiceException {

		String absolutePath = getServletContext().getRealPath("/");
		StorageService service = StorageFactory.create(absolutePath);
		return service.getUserRecord(userGoogleID);
	}

	public long saveUserInfo(UserRecord user) throws StorageServiceException {

		String absolutePath = getServletContext().getRealPath("/");
		StorageService service = StorageFactory.create(absolutePath);
		service.saveUserRecord(user);
		UserRecord u = service.getUserRecord(user.googleID);
		return u.userID;
	}
	
	public long createAndGetSession(long userID, long campaignID)
			throws StorageServiceException {
		StorageService service = StorageFactory.create(getAbsolutePath());
		return service.createAndGetSession(userID, campaignID);
	}

	public List<UserRecord> getUSerStatistics(long userID)
			throws StorageServiceException {
		StorageService service = StorageFactory.create(getAbsolutePath());
		return service.getUSerStatistics(userID);
	}

}
