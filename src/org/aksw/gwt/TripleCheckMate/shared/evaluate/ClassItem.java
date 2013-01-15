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

public class ClassItem implements Comparable<ClassItem> , Serializable{

	/**
	 * Auto-generated for serialization
	 */
	private static final long serialVersionUID = -2115169797941126082L;
	
	public long ID = 0;
	public long parentID = 0;
	public String name = "";
	public String uri = "";
	public String parent = "";
	public long count = 0;
	public int level = 0;
	public boolean isLeaf = false;
	
	// Tree structure
	private List<ClassItem> children = new ArrayList<ClassItem>();
	
	public ClassItem(){}
	public ClassItem(String name, String uri, String parent){
		this.name = name;
		this.uri = uri;
		this.parent = parent;
	}
	public ClassItem(long ID, String name, String uri, long pID, long count, boolean isLeaf){
		this.ID= ID;
		this.name = name;
		this.uri = uri;
		this.parentID = pID;
		this.count = count;
		this.isLeaf = isLeaf;
	}
	
	public void clearChildren(){
		for (ClassItem item: children){
			item.clearChildren();
		}
		children.clear();
	}

	@Override
	public int compareTo(ClassItem arg0) {
		return uri.compareTo(arg0.uri);
	}

}
