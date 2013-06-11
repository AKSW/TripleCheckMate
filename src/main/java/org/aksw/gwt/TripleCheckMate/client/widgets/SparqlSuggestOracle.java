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
package org.aksw.gwt.TripleCheckMate.client.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.aksw.gwt.TripleCheckMate.shared.evaluate.SessionContext;
import org.aksw.gwt.TripleCheckMate.shared.sparql.JsonSparqlResult;
import org.aksw.gwt.TripleCheckMate.shared.sparql.ResultItem;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SuggestOracle;

public class SparqlSuggestOracle extends SuggestOracle {

	@Override
	public void requestSuggestions(final Request request, final Callback callback) {
		// TODO Auto-generated method stub
		String addressQuery = request.getQuery();
        // look up for suggestions, only if at least 2 letters have been typed
        if (addressQuery.length() > 2) {    
        	try {
    			String query = SessionContext.endpoint.getQueryforAutocomplete(addressQuery);
    			String queryURL = SessionContext.endpoint.generateQueryURL(query);

    			RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, queryURL);
    			rb.setCallback(new com.google.gwt.http.client.RequestCallback() {
    				@Override
    				public void onResponseReceived(com.google.gwt.http.client.Request req, com.google.gwt.http.client.Response res) {
    					JsonSparqlResult result = new JsonSparqlResult(res.getText());
    					Collection<Suggestion> suggestions = new ArrayList<Suggestion>();
    					for (List<ResultItem> i : result.data){
    						 if (i.size() == 1){
    							 suggestions.add(new SparqlSuggestItem(i.get(0).value));
    						 }
    					}

                        callback.onSuggestionsReady(request, new Response(suggestions));
    				}

    				@Override
    				public void onError(com.google.gwt.http.client.Request request, Throwable exception) {

    				}
    			});
    			rb.send();
    		} catch (RequestException e) {
    			Window.alert("Error occurred" + e.getMessage());
    		}

        } else {
            Response response = new Response(
                    Collections.<Suggestion> emptyList());
            callback.onSuggestionsReady(request, response);
        }
	}
	
	public class SparqlSuggestItem implements SuggestOracle.Suggestion, Serializable {

	    private static final long serialVersionUID = 1L;

	    public String uri;

	    public SparqlSuggestItem(String uri) {
	        this.uri = uri;
	    }

	    @Override
	    public String getDisplayString() {
	        return this.uri;
	    }

	    @Override
	    public String getReplacementString() {
	        return this.uri;
	    }
	}

}
