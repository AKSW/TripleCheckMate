package org.aksw.gwt.TripleCheckMate.client.widgets;

import org.aksw.gwt.TripleCheckMate.shared.evaluate.SessionContext;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserSessionStatistics extends Composite{
	
	private VerticalPanel bodyPanel = new VerticalPanel();
	
	private final HTML txtUser = new HTML();
	private final HTML txtSession = new HTML();
	private final HTML txtAlltime = new HTML();
	
	public UserSessionStatistics(){
		super();
		// All composites must call initWidget() in their constructors.
		initWidget(bodyPanel);
		
		bodyPanel.add(txtUser);
		bodyPanel.add(txtSession);
		bodyPanel.add(txtAlltime);
	}
	
	public void updateSession(int rcount, int scount, int ecount){
		txtUser.setText( SessionContext.user.toHTMLString() );
		txtSession.setText("Session: " + rcount + " resources (" + scount + " skipped), " + ecount + " errors.");
		txtAlltime.setText("Session: " + rcount + " resources (" + scount + " skipped), " + ecount + " errors.");
	}

}
