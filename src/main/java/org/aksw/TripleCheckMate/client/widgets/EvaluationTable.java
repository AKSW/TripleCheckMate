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
package org.aksw.TripleCheckMate.client.widgets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.aksw.TripleCheckMate.shared.evaluate.EvaluateItem;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class EvaluationTable extends Composite {
	
	interface EvaluationTableUiBinder extends UiBinder<Widget, EvaluationTable> {}
	private static EvaluationTableUiBinder uiBinder = GWT.create(EvaluationTableUiBinder.class);

	
	@UiField CellTable<EvaluateItem> tblEvalTriples = new CellTable<EvaluateItem>(
			EvaluateItem.KEY_PROVIDER);
	@UiField SimplePager pgrTop;
	@UiField SimplePager pgrBottom;
	@UiField ListBox lstPager;
	
	private final EvaluationItemDialog dlgEdit = new EvaluationItemDialog();

	private final ListDataProvider<EvaluateItem> dataProvider = new ListDataProvider<EvaluateItem>();
	private final ListHandler<EvaluateItem> sortHandler = new ListHandler<EvaluateItem>(
			dataProvider.getList());
	private final int[] arrPagerSizes = { 25, 50, 75, 100, 150 };
	
	

	public EvaluationTable() {
		super();
		initWidget(uiBinder.createAndBindUi(this));
		
		dlgEdit.setParent(this);
		
		createTable();
	}

	public void updateData() {
		tblEvalTriples.redraw();
	}

	public void clearTable() {
		pgrTop.setPage(0);
		pgrBottom.setPage(0);
		dataProvider.getList().clear();
	}

	public void setNewEvaluationData(List<EvaluateItem> data) {
		clearTable();
		if (data != null && data.size() > 0)
			dataProvider.getList().addAll(data);
	}

	public Boolean hasMarkedErrors() {
		for (EvaluateItem item : dataProvider.getList()) {
			if (item.isWrong == true) {
				return true;
			}
		}
		return false;
	}

	public Boolean hasEmptyMarkedErrors() {
		for (EvaluateItem item : dataProvider.getList()) {
			if (item.isWrong == true && item.errorType == 0) {
				return true;
			}
		}
		return false;
	}

	public List<EvaluateItem> getMarkedEvaluations() {
		List<EvaluateItem> wrongEvals = new ArrayList<EvaluateItem>();
		for (EvaluateItem item : dataProvider.getList()) {
			if (item.isWrong == true) {
				wrongEvals.add(item);
			}
		}
		return wrongEvals;
	}



	private void createTable() {
		// Link to data provider
		dataProvider.addDataDisplay(tblEvalTriples);

		// Table properties
		tblEvalTriples.setPageSize(arrPagerSizes[0]);

		// Set Pagers (add both bottom and top)
		pgrTop.setDisplay(tblEvalTriples);
		pgrBottom.setDisplay(tblEvalTriples);

		// Table columns
		final SafeHtmlCell cellP = new SafeHtmlCell();
		Column<EvaluateItem, SafeHtml> colPred = new Column<EvaluateItem, SafeHtml>(
				cellP) {

			public SafeHtml getValue(EvaluateItem item) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant(item.P.toHTMLString());
				return sb.toSafeHtml();
			}
		};

		tblEvalTriples.addColumn(colPred, "Predicate");

		final SafeHtmlCell cellO = new SafeHtmlCell();
		Column<EvaluateItem, SafeHtml> colObj = new Column<EvaluateItem, SafeHtml>(
				cellO) {

			public SafeHtml getValue(EvaluateItem item) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant(item.O.toHTMLString());
				return sb.toSafeHtml();
			}
		};
		tblEvalTriples.addColumn(colObj, "Object");

		Column<EvaluateItem, Boolean> colIsValid = new Column<EvaluateItem, Boolean>(
				new CheckboxCell(true, false)) {

			public Boolean getValue(EvaluateItem item) {
				return item.isWrong;
			}
		};

		tblEvalTriples.addColumn(colIsValid, "Is Wrong");

		// Add a field updater to be notified when the user enters a new name.
		colIsValid.setFieldUpdater(new FieldUpdater<EvaluateItem, Boolean>() {

			public void update(int index, EvaluateItem object, Boolean value) {
				dataProvider.getList().get(index).isWrong = value;
				if (value == false) {
					dataProvider.getList().get(index).errorTittle = "";
					tblEvalTriples.redraw();
				} else {
					dlgEdit.setEvaluateItem(dataProvider.getList().get(index));
					dlgEdit.center();
					dlgEdit.show();
				}

			}
		});

		final SafeHtmlCell cellError = new SafeHtmlCell();
		Column<EvaluateItem, SafeHtml> colError = new Column<EvaluateItem, SafeHtml>(
				cellError) {

			public SafeHtml getValue(EvaluateItem item) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant(item.errorTittle);
				return sb.toSafeHtml();
			}
		};
		tblEvalTriples.addColumn(colError, "Error");

		// Setup sorting

		colPred.setSortable(true);
		sortHandler.setComparator(colPred, new Comparator<EvaluateItem>() {
			public int compare(EvaluateItem o1, EvaluateItem o2) {
				return o1.P.toString().compareTo(o2.P.toString());
			}
		});

		colObj.setSortable(true);
		sortHandler.setComparator(colObj, new Comparator<EvaluateItem>() {
			public int compare(EvaluateItem o1, EvaluateItem o2) {
				return o1.P.toString().compareTo(o2.P.toString());
			}
		});

		colIsValid.setSortable(true);
		sortHandler.setComparator(colIsValid, new Comparator<EvaluateItem>() {
			public int compare(EvaluateItem o1, EvaluateItem o2) {
				if (o1.isWrong == o2.isWrong)
					return 0;
				else
					return (o1.isWrong ? 1 : -1);
			}
		});

		colError.setSortable(true);
		sortHandler.setComparator(colError, new Comparator<EvaluateItem>() {
			public int compare(EvaluateItem o1, EvaluateItem o2) {
				return o1.errorTittle.compareTo(o2.errorTittle);
			}
		});

		tblEvalTriples.addColumnSortHandler(sortHandler);
		tblEvalTriples.getColumnSortList().push(colObj);
		tblEvalTriples.getColumnSortList().push(colPred);

		for (int i = 0; i < arrPagerSizes.length; i++) {
			lstPager.addItem("" + arrPagerSizes[i]);
		}
		lstPager.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				tblEvalTriples.setPageSize(arrPagerSizes[lstPager
						.getSelectedIndex()]);
			}
		});
	}

}
