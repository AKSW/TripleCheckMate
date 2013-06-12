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

import java.util.List;

import org.aksw.TripleCheckMate.shared.evaluate.ClassItem;
import org.aksw.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.TripleCheckMate.shared.evaluate.EvaluateResource;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EvaluationRequestAsync {
	  
	public void SaveEvaluation(long sessionID, EvaluateResource item, AsyncCallback<String> callback);
	
	void getExistingEvaluations(long user, String classType, AsyncCallback<List<String>> callback);
	
	public void getClassChildren(long classID, AsyncCallback<List<ClassItem>> callback);
	void saveClassCount(long id, long count, AsyncCallback<Integer> callback);
	void saveClassFromOWL(String owlURL, AsyncCallback<Integer> callback);

	void getErrorChildren(long errorID, AsyncCallback<List<ErrorItem>> callback);
}

