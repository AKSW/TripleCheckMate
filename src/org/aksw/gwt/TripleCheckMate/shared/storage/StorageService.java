package org.aksw.gwt.TripleCheckMate.shared.storage;

import java.util.List;

import org.aksw.gwt.TripleCheckMate.shared.evaluate.ClassItem;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.EvaluateResource;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.gwt.TripleCheckMate.shared.storage.exception.StorageServiceException;

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
