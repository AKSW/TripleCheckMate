package org.aksw.gwt.TripleCheckMate.client.requests;

import java.util.List;

import org.aksw.gwt.TripleCheckMate.shared.evaluate.ClassItem;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.EvaluateResource;
import org.aksw.gwt.TripleCheckMate.shared.storage.exception.StorageServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("evaluate")
public interface EvaluationRequest extends RemoteService {
	public String SaveEvaluation(long sessionID, EvaluateResource item) throws StorageServiceException;
	
	public List<String> getExistingEvaluations(long user, String classType) throws StorageServiceException;
	
	// Class selection related
	public List<ClassItem> getClassChildren(long classID) throws StorageServiceException;
	public int saveClassCount(long id, long count) throws StorageServiceException;
	public int saveClassFromOWL(String owlURL) throws StorageServiceException;
	
	public List<ErrorItem> getErrorChildren(long errorID) throws StorageServiceException;
}
