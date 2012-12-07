package org.aksw.gwt.TripleCheckMate.shared.sparql;

import java.io.Serializable;

public class ResultItem implements Serializable{
	/**
	 * Autogenerated for serialization
	 */
	private static final long serialVersionUID = -2067749804344339633L;
	public String value = "";
	public String type = "";
	public String lang = "";
	public String datatype = "";
	public ResultItem(){}
	public ResultItem(String v, String t, String l, String d){
		value = v;
		type = t;
		lang = l;
		datatype = d;
	}
	
	public String toString(){
		if (type.equalsIgnoreCase("literal"))
			return "\"" + value + "\" (@lang = "+ lang + ")";
		if (type.equalsIgnoreCase("typed-literal"))
			return "\"" + value + "\" (@type = "+ datatype + ")";
		return value;
	}
	public String toHTMLString(){
		if (type.equalsIgnoreCase("uri"))
			return "<a href=\""+dereferenceURI(value) + "\" title=\""+value + "\" target=\"_blank\">"+ abbreviateURI(value) + "</a>";
		if (type.equalsIgnoreCase("literal"))
			return "\"" + value + "\" (@lang = "+ lang + ")";
		if (type.equalsIgnoreCase("typed-literal"))
			return "\"" + value + "\" (@type = "+ datatype + ")";
		return value;
	}
	private String abbreviateURI(String uri){
		return PrefixService.getAbbeviatedForm(uri);
	}
	private String dereferenceURI(String uri){
		return uri.replace("http://dbpedia.org/", "http://dbpedia.aksw.org:8877/");
	}
}