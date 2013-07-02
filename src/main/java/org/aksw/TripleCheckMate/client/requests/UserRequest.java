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
package org.aksw.TripleCheckMate.client.requests;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.aksw.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.TripleCheckMate.shared.exceptions.StorageServiceException;

import java.util.List;

@RemoteServiceRelativePath("UserRequest")
public interface UserRequest extends RemoteService {
    public UserRecord getUserInfo(String userGoogleID) throws StorageServiceException;

    public long saveUserInfo(UserRecord user) throws StorageServiceException;

    public long createAndGetSession(long userID, long campaignID) throws StorageServiceException;

    public List<UserRecord> getUSerStatistics(long userID) throws StorageServiceException;

}
