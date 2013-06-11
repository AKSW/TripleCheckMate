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
package org.aksw.gwt.TripleCheckMate.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.SessionContext;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TripleCheckMate implements EntryPoint {

	/**
	 * This is the entry point method.
	 */

	public void onModuleLoad() {

		setHeaderTop();
		setHeaderBottom();
		setVisualPlacement();
	}

	private void setVisualPlacement(){
		RootPanel rootPanel = RootPanel.get();

		VerticalPanel page = new VerticalPanel();
		page.setHeight("100%");
		page.setWidth("100%");
		rootPanel.add(page);

		VerticalPanel evalTabPanel = new VerticalPanel();

		SessionContext.tabPanel.add(SessionContext.pageStart, "DBpedia Evaluation Campaign");
		SessionContext.tabPanel.add(SessionContext.pageEval,"Evaluate");
		SessionContext.tabPanel.addStyleName("main-tab");
		SessionContext.tabPanel.selectTab(0);
		SessionContext.tabPanel.setAnimationEnabled(true);
		SessionContext.tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
				if (SessionContext.user.userID == -1) {
					event.cancel();
				}
			}
		});
		
		page.add(SessionContext.tabPanel);

		VerticalPanel bodyPanel = new VerticalPanel();
		bodyPanel.setWidth("100%");
		bodyPanel.setSpacing(4);
		evalTabPanel.add(bodyPanel);

		VerticalPanel graphPanel = new VerticalPanel();
	    HTML label = new HTML("Graphs");
	    graphPanel.add(label);
	}

	private void setHeaderBottom(){

	}

	private void setHeaderTop(){

	}
}
