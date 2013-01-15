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
package org.aksw.gwt.TripleCheckMate.shared.evaluate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EvaluateResource  implements Comparable<EvaluateResource> , Serializable{
	/**
	 * Auto-generated for serialization
	 */
	private static final long serialVersionUID = 8604373274886096465L;
	
	public String about = "";
	public boolean valid = true;
	public String comments = "";
	public List<EvaluateItem> items = new ArrayList<EvaluateItem>();
	public String classType = "";
	public boolean isSkipped = true;
	
	public EvaluateResource(){}
	public EvaluateResource(String about, boolean valid, String comments, List<EvaluateItem> items, String classType){
		this.about = about;
		this.valid = valid;
		this.comments = comments;
		this.items.addAll(items);
		this.classType = classType;
	}
	public EvaluateResource(EvaluateResource r){
		this.about = r.about;
		this.valid = r.valid;
		this.comments = r.comments;
		this.items.clear();
		this.items.addAll(r.items);
		this.classType = r.classType;
		this.isSkipped = r.isSkipped;
	}
	
	public void updateContents(String about, boolean valid, String comments, List<EvaluateItem> items, String classType){
		this.about = about;
		this.valid = valid;
		this.comments = comments;
		this.items.clear();
		this.items.addAll(items);
		this.classType = classType;
	}
	public void updateContents(boolean valid, String comments, List<EvaluateItem> items){
		this.valid = valid;
		this.comments = comments;
		this.items.clear();
		this.items.addAll(items);
	}
	@Override
	public int compareTo(EvaluateResource arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
