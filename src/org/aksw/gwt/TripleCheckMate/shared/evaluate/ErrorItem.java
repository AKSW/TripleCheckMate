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

	@Override 
	public int compareTo(ErrorItem arg0) {
		return title.compareTo(arg0.title);
	}
}
