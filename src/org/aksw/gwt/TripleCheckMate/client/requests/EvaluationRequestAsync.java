package org.aksw.gwt.TripleCheckMate.client.requests;

import java.util.List;

import org.aksw.gwt.TripleCheckMate.shared.evaluate.ClassItem;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.EvaluateResource;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EvaluationRequestAsync {
	  
	public void SaveEvaluation(long sessionID, EvaluateResource item, AsyncCallback<String> callback);
	
	void getExistingEvaluations(long user, String classType, AsyncCallback<List<String>> callback);
	
	public void getClassChildren(long classID, AsyncCallback<List<ClassItem>> callback);
	void saveClassCount(long id, long count, AsyncCallback<Integer> callback);
	void saveClassFromOWL(String owlURL, AsyncCallback<Integer> callback);

	void getErrorChildren(long errorID, AsyncCallback<List<ErrorItem>> callback);
}

