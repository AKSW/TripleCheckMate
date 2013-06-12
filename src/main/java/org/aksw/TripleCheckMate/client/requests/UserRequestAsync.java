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

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.aksw.TripleCheckMate.shared.evaluate.UserRecord;

import java.util.List;

public interface UserRequestAsync {
    public void getUserInfo(String userGoogleID, AsyncCallback<UserRecord> callback);

    public void saveUserInfo(UserRecord user, AsyncCallback<Long> callback);

    void createAndGetSession(long userID, long campaignID, AsyncCallback<Long> callback);

    void getUSerStatistics(long userID, AsyncCallback<List<UserRecord>> callback);

}

