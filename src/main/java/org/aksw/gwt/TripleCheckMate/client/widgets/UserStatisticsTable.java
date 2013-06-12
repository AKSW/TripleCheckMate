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

import java.util.List;
import java.util.Comparator;


import org.aksw.gwt.TripleCheckMate.shared.evaluate.SessionContext;
import org.aksw.gwt.TripleCheckMate.shared.evaluate.UserRecord;
import org.aksw.gwt.TripleCheckMate.shared.storage.exception.StorageServiceException;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class UserStatisticsTable extends Composite{
	
	private ListDataProvider<UserRecord> dataProvider = new ListDataProvider<UserRecord>();
	private final ListHandler<UserRecord> sortHandler = new ListHandler<UserRecord>(dataProvider.getList());
	
	private final CellTable<UserRecord> tblUserStats = new CellTable<UserRecord>(UserRecord.KEY_PROVIDER);
	
	 
	public UserStatisticsTable() {
		super();
		createTable();
		setLayout();
        // All composites must call initWidget() in their constructors.
		//initWidget(panel);
		updateData();
	}
	
	public void updateData(){
		
		// create new session
		AsyncCallback<List<UserRecord>> statisticsCallback = new AsyncCallback<List<UserRecord>>() {
			public void onFailure(Throwable caught) {
				// Error getting the user from DB
				String details = caught.getMessage();
				if (caught instanceof StorageServiceException) {
					details = ((StorageServiceException) caught)
							.getErrorMessage();
				}
				Window.alert(details);
			}

			public void onSuccess(List<UserRecord> statistics) {
				// User retrieved, if null does not exists (=> store)
				dataProvider.getList().clear();
				dataProvider.getList().addAll(statistics);
				tblUserStats.redraw();
			}
		};
		// Call getUserInfo (async)
		SessionContext.userReqSrv.getUSerStatistics(-1, statisticsCallback);
		
	}
	
	public void clearTable(){
		
		dataProvider.getList().clear();
	}
	
	private void setLayout() {
		VerticalPanel bodyPanel = new VerticalPanel();
		bodyPanel.setWidth("100%");
		
		bodyPanel.add(tblUserStats);
		
		initWidget(bodyPanel);
	}
	
	private void createTable (){
		// Link to data provider
		dataProvider.addDataDisplay(tblUserStats);

		// Table properties
		tblUserStats.setWidth("100%");
		tblUserStats.setPageSize(50);

		// Table columns
		Column<UserRecord, SafeHtml> colUserName = new Column<UserRecord, SafeHtml>(new SafeHtmlCell()) {

			public SafeHtml getValue(UserRecord item) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant(item.toHTMLString());
				return sb.toSafeHtml();
			}
		};

		tblUserStats.addColumn(colUserName, "User");
		colUserName.setSortable(true);
		sortHandler.setComparator(colUserName, new Comparator<UserRecord>() {
			public int compare(UserRecord o1, UserRecord o2) {
				return o1.name.compareTo(o2.name);
			}
		});

		Column<UserRecord, String> colRes = new Column<UserRecord, String>(new TextCell()) {

			public String getValue(UserRecord object) {
				// TODO Auto-generated method stub
				return "" + object.recordCount;
			}
		} ;
		tblUserStats.addColumn(colRes, "Resources");
		colRes.setSortable(true);
		sortHandler.setComparator(colRes, new Comparator<UserRecord>() {
			public int compare(UserRecord o1, UserRecord o2) {
				Integer a1 = new Integer(o1.recordCount);
				Integer a2 = new Integer(o2.recordCount);
				return a1.compareTo(a2);
			}
		});

		Column<UserRecord, String> colTriples = new Column<UserRecord, String>(new TextCell()) {

			public String getValue(UserRecord object) {
				// TODO Auto-generated method stub
				return "" + object.errorCount;
			}
		} ;
		tblUserStats.addColumn(colTriples, "Wrong Triples");
		colTriples.setSortable(true);
		sortHandler.setComparator(colTriples, new Comparator<UserRecord>() {
			public int compare(UserRecord o1, UserRecord o2) {
				Integer a1 = new Integer(o1.errorCount);
				Integer a2 = new Integer(o2.errorCount);
				return a1.compareTo(a2);
			}
		});

		Column<UserRecord, String> colErrorTypes = new Column<UserRecord, String>(new TextCell()) {

			public String getValue(UserRecord object) {
				// TODO Auto-generated method stub
				return "" + object.distinctErrorCount;
			}
		} ;
		tblUserStats.addColumn(colErrorTypes, "Error Types");
		colErrorTypes.setSortable(true);
		sortHandler.setComparator(colErrorTypes, new Comparator<UserRecord>() {
			public int compare(UserRecord o1, UserRecord o2) {
				Integer a1 = new Integer(o1.distinctErrorCount);
				Integer a2 = new Integer(o2.distinctErrorCount);
				return a1.compareTo(a2);
			}
		});
		
		tblUserStats.addColumnSortHandler(sortHandler);
		tblUserStats.getColumnSortList().push(colTriples);
		tblUserStats.getColumnSortList().push(colRes);
	}
}
