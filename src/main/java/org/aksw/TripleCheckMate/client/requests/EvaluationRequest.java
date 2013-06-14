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
import org.aksw.TripleCheckMate.shared.evaluate.ClassItem;
import org.aksw.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.TripleCheckMate.shared.evaluate.EvaluateResource;
import org.aksw.TripleCheckMate.shared.sparql.Endpoint;
import org.aksw.TripleCheckMate.shared.storage.exception.StorageServiceException;

import java.util.List;

@RemoteServiceRelativePath("EvaluationRequest")
public interface EvaluationRequest extends RemoteService {

    public List<Endpoint> getCampaigns() throws StorageServiceException;

    public String SaveEvaluation(long sessionID, EvaluateResource item) throws StorageServiceException;

    public List<String> getExistingEvaluations(long user, String classType) throws StorageServiceException;

    // Class selection related
    public List<ClassItem> getClassChildren(long classID) throws StorageServiceException;

    public int saveClassCount(long id, long count) throws StorageServiceException;

    public int saveClassFromOWL(String owlURL) throws StorageServiceException;

    public List<ErrorItem> getErrorChildren(long errorID) throws StorageServiceException;
}
