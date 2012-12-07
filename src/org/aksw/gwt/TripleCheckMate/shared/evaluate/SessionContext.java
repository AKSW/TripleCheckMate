package org.aksw.gwt.TripleCheckMate.shared.evaluate;

import java.util.ArrayList;
import java.util.List;

import org.aksw.gwt.TripleCheckMate.client.requests.EvaluationRequest;
import org.aksw.gwt.TripleCheckMate.client.requests.EvaluationRequestAsync;
import org.aksw.gwt.TripleCheckMate.client.requests.UserRequest;
import org.aksw.gwt.TripleCheckMate.client.requests.UserRequestAsync;
import org.aksw.gwt.TripleCheckMate.client.widgets.EvaluationPage;
import org.aksw.gwt.TripleCheckMate.client.widgets.LoadingPopup;
import org.aksw.gwt.TripleCheckMate.client.widgets.StartPage;
import org.aksw.gwt.TripleCheckMate.shared.sparql.Endpoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DecoratedTabPanel;

public class SessionContext {
	private SessionContext() {
	}

	// User related
	public static UserRecord user = new UserRecord();
	public static long userSession = 0;

	// Evaluation
	public static int evaluationCampaign = 0;
	public static EvaluateResource latestEvaluateResource = new EvaluateResource();
	public static List<EvaluateResource> evaluations = new ArrayList<EvaluateResource>();
	// TODO get it with RPC
	public static Endpoint endpoint = new Endpoint(
			"http://dbpedia.aksw.org:8877/sparql",
			"http://dbpedia.org",
			"dbpedia.aksw.org:8877/sparql");

	// Async RPC Requests
	public static EvaluationRequestAsync evaluationReqSrv = GWT
			.create(EvaluationRequest.class);
	public static UserRequestAsync userReqSrv = GWT.create(UserRequest.class);

	// GUI related
	public static DecoratedTabPanel tabPanel = new DecoratedTabPanel();
	public static EvaluationPage pageEval = new EvaluationPage();
	public static StartPage pageStart = new StartPage();
	public static LoadingPopup popup = new LoadingPopup();

	public static void showPopup() {
		popup.center();
		popup.show();
	}

	public static void hidePopup() {
		popup.hide();
	}

	public static void setNewEvaluation(String aboutURI,
			List<EvaluateItem> data, String classType) {
		if (!latestEvaluateResource.about.equals("")) {
			// Add evaluation history
			evaluations.add(new EvaluateResource(latestEvaluateResource));
		}
		latestEvaluateResource.updateContents(aboutURI, false, "",
				new ArrayList<EvaluateItem>(), classType);
		pageEval.setNewEvaluation(aboutURI, data);
	}

	public static boolean existsInSessionHistory(String resource) {
		if (latestEvaluateResource.about.equals(resource))
			return true;
		for (EvaluateResource r : evaluations)
			if (r.about.equals(resource))
				return true;
		return false;
	}

	public static void submitEvaluation(EvaluateResource e) {

	}

	public static void gotoEvaluationPage() {
		SessionContext.tabPanel.selectTab(1);
		pageEval.openSelectDialog();
	}
	
	public static String getUserProgressTxt() {
		int submitted = 0;
		int skipped = 0;
		for (EvaluateResource i : evaluations) {
			if (i.isSkipped == true)
				skipped++;
			else
				submitted++;
		}
		if (!latestEvaluateResource.about.equals(""))
			if (latestEvaluateResource.isSkipped == true)
				skipped++;
			else
				submitted++;

		return "<div class=\"user-progress\">" + user.toHTMLString()
				+ "<div class=\"user-progress-item\">Submitted " + submitted
				+ ", Skipped " + skipped + "</div></div>";
	}
	
	public static void finishEvaluation(){
		pageEval.finishEvaluation();
		pageStart.StartEvaluation();
		SessionContext.tabPanel.selectTab(0);
		
	}

}
