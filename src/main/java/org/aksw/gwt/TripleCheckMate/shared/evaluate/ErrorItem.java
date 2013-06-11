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

public class ErrorItem implements Comparable<ErrorItem> , Serializable{
	/**
	 * Auto-generated for serialization
	 */
	private static final long serialVersionUID = 3282675230275052660L;

	public long ID = 0;
	public long parentID = 0;
	public String title = "";
	public String exampleURI  = "";
	public String exampleN3   = "";
	public String exampleDesc = "";
	public boolean isLeaf = false;
	
	public ErrorItem(){}
	public ErrorItem(long ID, String title, String exampleURI, String exampleN3, String exampleDesc, long parentID, boolean isLeaf){
		this.ID = ID;
		this.title = title;
		this.exampleURI   = exampleURI;
		this.exampleN3    = exampleN3;
		this.exampleDesc  = exampleDesc;
		this.parentID = parentID;
		this.isLeaf = isLeaf;
	}

	public int compareTo(ErrorItem arg0) {
		return title.compareTo(arg0.title);
	}
}
