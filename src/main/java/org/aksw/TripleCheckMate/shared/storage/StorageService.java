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
package org.aksw.TripleCheckMate.shared.storage;

import java.util.List;

import org.aksw.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.TripleCheckMate.shared.evaluate.EvaluateResource;
import org.aksw.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.TripleCheckMate.shared.storage.exception.StorageServiceException;
import org.aksw.TripleCheckMate.shared.evaluate.ClassItem;

public abstract class StorageService {
	
	/**
	 * Saves item to storage service
	 * @param endpoint
	 * @param graph
	 * @param items
	 */
	public abstract int saveEvaluation(long sessionID, EvaluateResource item) throws StorageServiceException;
	public abstract long createAndGetSession(long userID, long campaignID) throws StorageServiceException;
	
	public abstract List<String> getExistingEvaluations(long userID, String classType) throws StorageServiceException;
	
	public abstract UserRecord getUserRecord(String userID) throws StorageServiceException;
	public abstract int saveUserRecord(UserRecord user) throws StorageServiceException;
	
	public abstract int saveClasses(List<ClassItem> items) throws StorageServiceException;
	public abstract List<ClassItem> getClassChildren(long classID) throws StorageServiceException;
	public abstract int updateClassCount(long id, long count) throws StorageServiceException;
	
	public abstract List<ErrorItem> getErrorChildren(long errorID) throws StorageServiceException;
	
	public abstract List<UserRecord> getUSerStatistics(long uid) throws StorageServiceException;

}
