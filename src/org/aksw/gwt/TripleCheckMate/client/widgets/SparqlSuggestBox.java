package org.aksw.gwt.TripleCheckMate.client.widgets;

import com.google.gwt.user.client.ui.SuggestBox;

public class SparqlSuggestBox extends SuggestBox {
	public SparqlSuggestBox(){
		super(new SparqlSuggestOracle());
	}

}
