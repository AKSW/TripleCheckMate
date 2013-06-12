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
package org.aksw.gwt.TripleCheckMate.shared.sparql;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class JsonSparqlResult{
	
	// TODO create an object for this not string!
	public List<List<ResultItem>> data = new ArrayList<List<ResultItem>>();
	public List<String> head = new ArrayList<String>();
	
	public JsonSparqlResult(String json) throws Exception {
		try {
		// Parse json and get the Object
		JSONValue value = JSONParser.parseLenient(json);
        JSONObject obj = value.isObject();
		
		//First we need to take the description
		//{ "head": { "link": [], "vars": ["p", "o"] },
		JSONObject headObj = obj.get("head").isObject();
		JSONArray headVars = headObj.get("vars").isArray();
		if (headVars != null) {
        	for (int i=0; i<headVars.size(); i++) {
        		String varName = headVars.get(i).isString().stringValue();
        		head.add(varName);
        	}
		}
		
		// "results": { "distinct": false, "ordered": true, "bindings": [ ... ]
        JSONObject resultsObj = obj.get("results").isObject();
        JSONArray bindings = resultsObj.get("bindings").isArray();
        if (bindings != null) {
        	for (int i=0; i<bindings.size(); i++) {
        		
        		JSONObject current = bindings.get(i).isObject();
        		List<ResultItem> item = new ArrayList<ResultItem>();
        		for (int j=0; j < head.size(); j++) {
        			JSONObject rowItem = current.get( head.get(j) ).isObject();
        			String sValue  = "";
        			if (rowItem.get("value") != null)
        				sValue  = rowItem.get("value").isString().stringValue().trim();
        			String sType  = "";
        			if (rowItem.get("type") != null)
        				sType  = rowItem.get("type").isString().stringValue().trim();
        			String sLang  = "";
        			if (rowItem.get("xml:lang") != null)
        				sLang  = rowItem.get("xml:lang").isString().stringValue().trim();
        			String sDatatype  = "";
        			if (rowItem.get("datatype") != null)
        				sDatatype  = rowItem.get("datatype").isString().stringValue().trim();
        			item.add(new ResultItem(sValue, sType, sLang, sDatatype));
        		}
        		data.add(item);                    
             }
        }
		} catch (Exception e){
			e.printStackTrace();
            throw new Exception();
        }
		
	}
	
	public String getFirstResult(){
		if (data.size()>0 && data.get(0).size() > 0)
			return data.get(0).get(0).value;
		return ""; 
			
	}
}
