package org.aksw.gwt.TripleCheckMate.client.widgets;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class LoadingPopup extends PopupPanel{
	public LoadingPopup() {
		this.setAutoHideEnabled(false);
		this.setModal(true);
		this.setGlassEnabled(true);
		this.setTitle("Loading");
		this.add(new Label("Loading..."));
	}

}
