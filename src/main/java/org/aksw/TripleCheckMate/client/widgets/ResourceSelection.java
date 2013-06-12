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
package org.aksw.TripleCheckMate.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.aksw.TripleCheckMate.shared.evaluate.ClassItem;
import org.aksw.TripleCheckMate.shared.evaluate.EvaluateItem;
import org.aksw.TripleCheckMate.shared.evaluate.SessionContext;
import org.aksw.TripleCheckMate.shared.sparql.JsonSparqlResult;
import org.aksw.TripleCheckMate.shared.sparql.ResultItem;
import org.aksw.TripleCheckMate.shared.storage.exception.StorageServiceException;

import java.util.ArrayList;
import java.util.List;

public class ResourceSelection extends Composite {

    interface ResourceSelectionUiBinder extends UiBinder<Widget, ResourceSelection> {
    }

    private static ResourceSelectionUiBinder uiBinder = GWT.create(ResourceSelectionUiBinder.class);

    @UiField
    HTML htmlUserInfo;
    @UiField
    Button btnFetch;
    @UiField
    Button btnFinish;

    @UiField
    RadioButton rdAny;
    @UiField
    RadioButton rdClass;
    @UiField
    RadioButton rdManual;

    @UiField
    Tree treeClass;
    @UiField
    SparqlSuggestBox txtManual;

    @UiField
    DisclosurePanel pnlAdvanced;
    @UiField
    VerticalPanel pnlSelAny;
    @UiField
    VerticalPanel pnlSelClass;
    @UiField
    VerticalPanel pnlSelManual;

    public ResourceSelection() {
        super();
        initWidget(uiBinder.createAndBindUi(this));

        expandOptions(true);

        pnlSelClass.setVisible(false);
        pnlSelManual.setVisible(false);

        setupRadioButtons();
        setupClassTree();
        setupFetchButton();

        txtManual.addSelectionHandler(new SelectionHandler<SparqlSuggestOracle.Suggestion>() {

            public void onSelection(SelectionEvent<SparqlSuggestOracle.Suggestion> event) {
                // TODO Auto-generated method stub
                expandOptions(false);
                fetchAndLoadRandom();
            }
        });

        btnFinish.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (Window.confirm("Are you sure you want to finish?")) {
                    Window.alert("Thank you for your time, see your position in the hall of Fame!");
                    SessionContext.finishEvaluation();
                }
            }
        });
    }

    private void setupRadioButtons() {
        ClickHandler handler = new ClickHandler() {

            public void onClick(ClickEvent event) {
                // TODO Auto-generated method stub
                pnlSelAny.setVisible(false);
                pnlSelClass.setVisible(false);
                pnlSelManual.setVisible(false);
                if (event.getSource() == rdAny) {
                    pnlSelAny.setVisible(true);
                } else if (event.getSource() == rdClass) {
                    pnlSelClass.setVisible(true);
                } else if (event.getSource() == rdManual) {
                    pnlSelManual.setVisible(true);
                }
            }
        };
        rdAny.addClickHandler(handler);
        rdClass.addClickHandler(handler);
        rdManual.addClickHandler(handler);

        rdAny.setValue(true);
        rdClass.setValue(false);
        rdManual.setValue(false);
    }

    private void setupClassTree() {
        ClassItem item = new ClassItem("owl:Thing",
                "http://www.w3.org/2002/07/owl#Thing",
                "http://www.w3.org/2002/07/owl#Thing");
        item.count = 2161560;
        TreeItem ti = new TreeItem();
        ti.setText(item.name);
        // Temporarily add an item so we can expand this node
        ti.addTextItem("");
        ti.setUserObject(item);
        treeClass.addItem(ti);

        // Add a handler that automatically generates some children
        treeClass.addOpenHandler(new OpenHandler<TreeItem>() {

            public void onOpen(OpenEvent<TreeItem> event) {
                final TreeItem treeItem = event.getTarget();
                if (treeItem.getChildCount() == 1
                        && treeItem.getChild(0).getText().equals("")) {
                    // Close the item immediately
                    treeItem.setState(false, false);

                    SessionContext.showPopup();
                    // Set up the callback object.
                    AsyncCallback<List<ClassItem>> classCallback = new AsyncCallback<List<ClassItem>>() {
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                            SessionContext.hidePopup();
                        }

                        public void onSuccess(List<ClassItem> items) {
                            treeItem.getChild(0).remove();
                            for (ClassItem i : items) {
                                TreeItem child = treeItem.addTextItem(i.name);
                                child.setUserObject(i);
                                if (i.isLeaf == false)
                                    child.addTextItem("");
                            }
                            treeItem.setState(true, false);
                            SessionContext.hidePopup();
                        }
                    };
                    long classID = ((ClassItem) treeItem.getUserObject()).ID;
                    SessionContext.evaluationReqSrv.getClassChildren(classID,
                            classCallback);

                }
            }
        });
    }

    private void setupFetchButton() {
        btnFetch.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                expandOptions(false);
                fetchAndLoadRandom();
            }
        });
    }

    public void fetchAndLoadRandom() {

        SessionContext.showPopup();

        if (rdAny.getValue() == true) {
            String query = SessionContext.endpoint
                    .getQueryforRandomResource();
            fetchRandomResource(query, "Any");
        }

        if (rdClass.getValue() == true) {

            if (treeClass.getSelectedItem() == null) {
                Window.alert("No Class selected");

                SessionContext.hidePopup();
                return;
            }

            final ClassItem selectedClass = (ClassItem) treeClass.getSelectedItem().getUserObject();
            if (selectedClass.count != 0) {
                String query = SessionContext.endpoint
                        .getQueryforRandomClassResource(selectedClass.uri, selectedClass.count);
                fetchRandomResource(query, selectedClass.uri);
            } else {
                // Get the class count first
                try {
                    String query = SessionContext.endpoint.getQueryforClassCount(selectedClass.uri);
                    String queryURL = SessionContext.endpoint.generateQueryURL(query);

                    RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, queryURL);
                    rb.setCallback(new RequestCallback() {

                        public void onResponseReceived(Request request,
                                                       Response response) {
                            try {
                                JsonSparqlResult result = new JsonSparqlResult(response
                                        .getText());

                                String classCount = result.getFirstResult();
                                selectedClass.count = Long.parseLong(classCount);
                                String query = SessionContext.endpoint
                                        .getQueryforRandomClassResource(selectedClass.uri, selectedClass.count);
                                fetchRandomResource(query, selectedClass.uri);
                                saveClassCount(selectedClass.ID, selectedClass.count);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Window.alert("Error communicating with SPARQL Endpoint!");
                                SessionContext.hidePopup();
                            }
                        }

                        public void onError(Request request, Throwable exception) {
                            Window.alert("ERROR");
                            SessionContext.hidePopup();
                        }
                    });
                    rb.send();
                } catch (RequestException e) {
                    Window.alert("Error occurred" + e.getMessage());

                    SessionContext.hidePopup();
                }

            }
        }

        if (rdManual.getValue() == true) {
            String resource = txtManual.getText();
            fetchResource(resource, "Manual");
            txtManual.setText("");
        }

    }

    private void fetchRandomResource(String sparqlQueryURL, final String classType) {
        try {
            String queryURL = SessionContext.endpoint
                    .generateQueryURL(sparqlQueryURL);
            RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, queryURL);
            rb.setCallback(new RequestCallback() {

                public void onResponseReceived(Request request,
                                               Response response) {
                    try {
                        JsonSparqlResult result = new JsonSparqlResult(response
                                .getText());

                        String resource = result.getFirstResult();
                        fetchResource(resource, classType);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Window.alert("Error communicating with SPARQL Endpoint!");
                        SessionContext.hidePopup();
                    }
                }

                public void onError(Request request, Throwable exception) {
                    Window.alert("ERROR");
                    SessionContext.hidePopup();
                }
            });
            rb.send();
        } catch (RequestException e) {
            Window.alert("Error occurred" + e.getMessage());
            SessionContext.hidePopup();
        }
    }

    private void fetchResource(final String resource, final String classType) {

        // If not manual mode get existing by 50%
        if (rdManual.getValue() || Random.nextBoolean()) {
            fetchResourceSPARQL(resource, classType);
        } else {
            AsyncCallback<List<String>> getExistingEvaluationCallback = new AsyncCallback<List<String>>() {
                public void onFailure(Throwable caught) {
                    // Error getting the user from DB
                    String details = caught.getMessage();
                    if (caught instanceof StorageServiceException) {
                        details = ((StorageServiceException) caught).getErrorMessage();
                    }
                    Window.alert(details);
                    SessionContext.hidePopup();
                }

                public void onSuccess(List<String> result) {
                    String newResource = resource;
                    // TODO check if same was skipped
                    for (String s : result) {
                        if (!SessionContext.existsInSessionHistory(s)) {
                            newResource = s;
                            break;
                        }
                    }
                    fetchResourceSPARQL(newResource, classType);
                }
            };
            // Call getUserInfo (async)
            SessionContext.evaluationReqSrv.getExistingEvaluations(SessionContext.user.userID, classType, getExistingEvaluationCallback);
        }

    }

    private void fetchResourceSPARQL(final String resource, final String classType) {

        try {


            String sparqlQuery = SessionContext.endpoint
                    .getQueryforResourceTriples(resource);
            String queryURL = SessionContext.endpoint
                    .generateQueryURL(sparqlQuery);

            SessionContext.showPopup();
            RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, queryURL);
            rb.setCallback(new RequestCallback() {

                public void onResponseReceived(Request request,
                                               Response response) {
                    try {
                        JsonSparqlResult result = new JsonSparqlResult(response
                                .getText());

                        List<EvaluateItem> dataList = new ArrayList<EvaluateItem>();
                        for (int i = 0; i < result.data.size(); i++) {
                            dataList.add(new EvaluateItem(i, new ResultItem(
                                    resource, "uri", "", ""), result.data.get(i)
                                    .get(0), result.data.get(i).get(1)));
                        }
                        SessionContext.setNewEvaluation(resource, dataList, classType);
                        SessionContext.hidePopup();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Window.alert("Error communicating with SPARQL Endpoint!");
                        SessionContext.hidePopup();
                    }
                }

                public void onError(Request request, Throwable exception) {
                    Window.alert("ERROR");
                    SessionContext.hidePopup();
                }
            });
            rb.send();
        } catch (RequestException e) {
            Window.alert("Error occurred" + e.getMessage());
            SessionContext.hidePopup();
        }
    }

    private void saveClassCount(long id, long count) {
        AsyncCallback<Integer> saveClassCountCallback = new AsyncCallback<Integer>() {
            public void onFailure(Throwable caught) {
                // Error getting the user from DB
                String details = caught.getMessage();
                if (caught instanceof StorageServiceException) {
                    details = ((StorageServiceException) caught).getErrorMessage();
                }
                Window.alert(details);
            }

            public void onSuccess(Integer result) {

            }
        };
        // Call getUserInfo (async)
        SessionContext.evaluationReqSrv.saveClassCount(id, count, saveClassCountCallback);
    }

    public void expandOptions(boolean expand) {
        pnlAdvanced.setOpen(expand);
        htmlUserInfo.setHTML(SessionContext.getUserProgressTxt());
    }
}
