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

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.aksw.TripleCheckMate.shared.evaluate.SessionContext;
import org.aksw.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.TripleCheckMate.shared.storage.exception.StorageServiceException;

public class StartPage extends Composite {

    interface StartPageUiBinder extends UiBinder<Widget, StartPage> {
    }

    private static StartPageUiBinder uiBinder = GWT.create(StartPageUiBinder.class);

    @UiField
    Button btnAuth;
    @UiField
    Button btnStart;
    @UiField
    Button btnRefreshStats;
    @UiField
    UserStatisticsTable wdgUserStatTable;

    // Authenticate variables
    private final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    // available from the APIs console
    private final String CLIENT_ID = "280328408987.apps.googleusercontent.com";
    private final String SCOPE = "https://www.googleapis.com/auth/userinfo.profile";

    public StartPage() {

        super();
        initWidget(uiBinder.createAndBindUi(this));

        StartEvaluation();
        // Start button click Handler

        btnStart.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                btnStart.setEnabled(false);
                SessionContext.showPopup();
                // get campaign id
                // TODO get it from DB
                SessionContext.evaluationCampaign = 1;

                // create new session
                AsyncCallback<Long> sessionGetCallback = new AsyncCallback<Long>() {
                    public void onFailure(Throwable caught) {
                        // Error getting the user from DB
                        String details = caught.getMessage();
                        if (caught instanceof StorageServiceException) {
                            details = ((StorageServiceException) caught)
                                    .getErrorMessage();
                        }
                        Window.alert(details);
                        SessionContext.hidePopup();
                    }

                    public void onSuccess(Long session) {
                        // User retrieved, if null does not exists (=> store)
                        // else use stored info

                        SessionContext.userSession = session;
                        SessionContext.gotoEvaluationPage();

                        btnStart.setEnabled(false);
                        SessionContext.hidePopup();
                    }
                };
                // Call getUserInfo (async)
                SessionContext.userReqSrv.createAndGetSession(
                        SessionContext.user.userID,
                        SessionContext.evaluationCampaign, sessionGetCallback);
                // Go to next page

            }
        });

        // Authenticate with Google button Handler
        btnAuth.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {

                SessionContext.showPopup();

                AuthRequest req = new AuthRequest(AUTH_URL, CLIENT_ID)
                        .withScopes(SCOPE); // Can specify multiple scopes here
                Auth.get().login(req, new Callback<String, Throwable>() {

                    public void onSuccess(String token) {
                        // You now have the OAuth2 token needed to sign
                        // authenticated requests.
                        btnAuth.setEnabled(false);
                        handleUserInfo(token);
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Authentication failed...");
                        SessionContext.hidePopup();
                    }
                });
            }
        });

        btnRefreshStats.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                wdgUserStatTable.updateData();
            }
        });
    }

    public void StartEvaluation() {
        btnStart.setEnabled(false);
        btnAuth.setEnabled(true);
        wdgUserStatTable.updateData();
    }

    private final void handleUserInfo(String authToken) {
        try {
            String profileURL = "https://www.googleapis.com/oauth2/v1/userinfo";
            RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,
                    profileURL);
            rb.setHeader("Content-type", "application/atom+xml");
            rb.setHeader("Host", "spreadsheets.google.com");
            rb.setHeader("Authorization", "Bearer " + authToken);
            rb.setCallback(new RequestCallback() {

                public void onResponseReceived(Request request,
                                               Response response) {
                    if (response.getStatusCode() == 200) {
                        JSONValue value = JSONParser.parseLenient(response
                                .getText());
                        JSONObject obj = value.isObject();

                        if (obj.get("id") != null
                                && obj.get("id").isString() != null)
                            SessionContext.user.googleID = obj.get("id")
                                    .isString().stringValue();
                        if (obj.get("name") != null
                                && obj.get("name").isString() != null)
                            SessionContext.user.name = obj.get("name")
                                    .isString().stringValue();
                        if (obj.get("picture") != null
                                && obj.get("picture").isString() != null)
                            SessionContext.user.picture = obj.get("picture")
                                    .isString().stringValue();
                        if (obj.get("link") != null
                                && obj.get("link").isString() != null)
                            SessionContext.user.profile = obj.get("link")
                                    .isString().stringValue();
                        if (SessionContext.user.picture.equals(""))
                            SessionContext.user.picture = "https://ssl.gstatic.com/s2/profiles/images/silhouette96.png";

                        // uses SessionContext.user
                        searchForUserinStorageService();

                    } else {
                        Window.alert("Cannot retrieve user info!\n Return HTTP Code: "
                                + response.getStatusCode()
                                + " / "
                                + response.getText());
                        SessionContext.hidePopup();
                    }
                }

                public void onError(Request request, Throwable exception) {
                    Window.alert("Cannot retrieve user info!\nERROR : "
                            + exception.getMessage());
                    SessionContext.hidePopup();
                }
            });
            rb.send();
        } catch (RequestException e) {
            Window.alert("Cannot retrieve user info!\nERROR : "
                    + e.getMessage());
            SessionContext.hidePopup();
        }

    }

    private void searchForUserinStorageService() {
        // Set up the callback object get getting user details from DB.
        AsyncCallback<UserRecord> userGetCallback = new AsyncCallback<UserRecord>() {
            public void onFailure(Throwable caught) {
                // Error getting the user from DB
                String details = caught.getMessage();
                if (caught instanceof StorageServiceException) {
                    details = ((StorageServiceException) caught)
                            .getErrorMessage();
                }
                Window.alert(details);
                SessionContext.hidePopup();
            }

            public void onSuccess(UserRecord user) {
                // User retrieved, if null does not exists (=> store) else use
                // stored info
                if (user == null) {
                    saveUsertoStorageService(user);
                } else {
                    SessionContext.user.update(user);
                }
                btnStart.setEnabled(true);
                SessionContext.hidePopup();
            }
        };
        // Call getUserInfo (async)
        SessionContext.userReqSrv.getUserInfo(SessionContext.user.googleID,
                userGetCallback);

    }

    private void saveUsertoStorageService(UserRecord user) {
        AsyncCallback<Long> saveUserCallback = new AsyncCallback<Long>() {
            public void onFailure(Throwable caught) {
                String details = caught.getMessage();
                if (caught instanceof StorageServiceException) {
                    details = ((StorageServiceException) caught)
                            .getErrorMessage();
                }
                Window.alert(details);
            }

            public void onSuccess(Long userID) {
                // Get the user ID of the current user
                SessionContext.user.userID = userID;
            }

        };
        // Call saveUserInfo (async)
        SessionContext.userReqSrv.saveUserInfo(SessionContext.user,
                saveUserCallback);
    }
}
