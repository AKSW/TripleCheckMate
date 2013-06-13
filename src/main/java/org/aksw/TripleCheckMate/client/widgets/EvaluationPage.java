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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.aksw.TripleCheckMate.shared.evaluate.EvaluateItem;
import org.aksw.TripleCheckMate.shared.evaluate.SessionContext;
import org.aksw.TripleCheckMate.shared.storage.exception.StorageServiceException;

import java.util.List;

public class EvaluationPage extends Composite {

    interface EvaluationPageUiBinder extends UiBinder<Widget, EvaluationPage> {
    }

    private static EvaluationPageUiBinder uiBinder = GWT
            .create(EvaluationPageUiBinder.class);

    @UiField
    HTML htmlAbout;
    @UiField
    HTML htmlUserProgress;

    @UiField
    VerticalPanel pnlBody;
    @UiField
    RadioButton rdValid;
    @UiField
    RadioButton rdErrors;
    @UiField
    CheckBox ckMissing;

    @UiField
    TextArea txtComments;
    @UiField
    Button btnSave;
    @UiField
    Button btnSkip;

    @UiField
    EvaluationTable tblEval;

    // @UiField ResourceSelection pnlEvalSelect;
    ResourceSelection pnlEvalSelect = new ResourceSelection();
    DialogBox dlgEvalSelect = new DialogBox(false, true);

    public EvaluationPage() {
        super();
        initWidget(uiBinder.createAndBindUi(this));

        setContentVisible(false);

        // Resource selection Dialog
        dlgEvalSelect.setText("Resource selection options");
        dlgEvalSelect.setTitle("Resource selection options");
        dlgEvalSelect.setGlassEnabled(true);
        dlgEvalSelect.add(pnlEvalSelect);
        dlgEvalSelect.hide();

        ClickHandler handler = new ClickHandler() {

            public void onClick(ClickEvent event) {
                // The user must select any option to enable
                btnSave.setEnabled(true);
                if (rdValid.getValue() == true)
                    ckMissing.setValue(false);
                txtComments.setEnabled(!rdValid.getValue());
                ckMissing.setEnabled(!rdValid.getValue());

            }
        };
        rdValid.addClickHandler(handler);
        rdErrors.addClickHandler(handler);
        ckMissing.setEnabled(false);
        txtComments.setEnabled(false);

        btnSave.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (rdValid.getValue() == true) {
                    if (tblEval.hasMarkedErrors() == true) {
                        Window.alert("You have marked some triples as wrong.\nPlease unmark them.");
                        return;
                    }
                }

                if (ckMissing.getValue() == true) {
                    if (txtComments.getText().equals("")) {
                        Window.alert("Please provide some comments on what type of \ninformation is missing i.e. no rdf:type");
                        return;
                    }
                }

                if (rdErrors.getValue() == true) {
                    if (tblEval.hasEmptyMarkedErrors() == true) {
                        Window.alert("You have marked triples as wrong but without description.");
                    }
                    if (ckMissing.getValue() == false && tblEval.hasMarkedErrors() == false) {
                        Window.alert("You have not marked any wrong triples.");
                        return;
                    }
                }

                if (Window
                        .confirm("You are going to submit the selected errors, procceed? "))
                    submitEvaluation();
            }
        });

        btnSkip.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (Window
                        .confirm("Are sou sure you want to skip this resource? ")) {
                    SessionContext.latestEvaluateResource.isSkipped = true;
                    openSelectDialog();
                }

            }
        });

    }

    private void setAboutText(String aboutURI) {
        htmlAbout.setHTML("<h2>About: <a href=\""
                + aboutURI.replace("http://dbpedia.org/", "http://dbpedia.aksw.org:8877/")
                + "\" target=\"_blank\">"
                + aboutURI
                + "</a> <a href=\""
                + aboutURI.replace("http://dbpedia.org/resource",
                "http://en.wikipedia.org/wiki")
                + "\" target=\"_blank\"><img = src=\"images/wikipedia.png\"/></a></h2>");
    }

    public void openSelectDialog() {
        htmlUserProgress.setHTML(SessionContext.getUserProgressTxt());
        setContentVisible(false);
        pnlEvalSelect.expandOptions(true);
        dlgEvalSelect.center();
        dlgEvalSelect.show();
    }

    public void setNewEvaluation(String aboutURI, List<EvaluateItem> data) {
        dlgEvalSelect.hide();

        setAboutText(aboutURI);
        txtComments.setText("");
        tblEval.setNewEvaluationData(data);

        rdValid.setValue(false);
        ckMissing.setValue(false);
        rdErrors.setValue(false);
        btnSave.setEnabled(false);

        setContentVisible(true);
    }

    public void finishEvaluation() {
        dlgEvalSelect.hide();

        setAboutText("");


        rdValid.setValue(false);
        ckMissing.setValue(false);
        rdErrors.setValue(false);
        btnSave.setEnabled(false);
        setContentVisible(false);


    }

    public void submitEvaluation() {
        SessionContext.latestEvaluateResource.updateContents(
                rdValid.getValue(), "", tblEval.getMarkedEvaluations());

        // Set up the callback object.
        AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                String details = caught.getMessage();
                if (caught instanceof StorageServiceException) {
                    details = ((StorageServiceException) caught)
                            .getErrorMessage();
                }
                Window.alert(details);
                SessionContext.hidePopup();
            }

            public void onSuccess(String result) {
                SessionContext.hidePopup();
                Window.alert("Evaluation was submitted successfully."/* + result */);

                openSelectDialog();
            }
        };

        SessionContext.showPopup();
        SessionContext.latestEvaluateResource.comments = txtComments.getText();
        SessionContext.latestEvaluateResource.isSkipped = false;
        SessionContext.evaluationReqSrv.SaveEvaluation(
                SessionContext.userSession,
                SessionContext.latestEvaluateResource, callback);
    }

    private void setContentVisible(boolean visible) {
        pnlBody.setVisible(visible);
    }

}
