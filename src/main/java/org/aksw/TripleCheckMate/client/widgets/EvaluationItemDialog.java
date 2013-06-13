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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.aksw.TripleCheckMate.shared.evaluate.ErrorItem;
import org.aksw.TripleCheckMate.shared.evaluate.EvaluateItem;
import org.aksw.TripleCheckMate.shared.evaluate.SessionContext;

import java.util.List;

public class EvaluationItemDialog extends DialogBox {
    interface EvaluationItemDialogUiBinder extends
            UiBinder<Widget, EvaluationItemDialog> {
    }

    private static EvaluationItemDialogUiBinder uiBinder = GWT
            .create(EvaluationItemDialogUiBinder.class);

    @UiField
    HTML htmlTripleS;
    @UiField
    HTML htmlTripleP;
    @UiField
    HTML htmlTripleO;

    @UiField
    Tree treeError;
    @UiField
    HTML htmlExampleURI;
    @UiField
    HTML htmlExampleN3;
    @UiField
    HTML htmlExampleDesc;

    @UiField
    TextArea txtComments;

    @UiField
    Button btnSave;
    @UiField
    Button btnCancel;

    private EvaluateItem item = null;
    private EvaluationTable parentDlg = null;
    private boolean oldStatus = false;

    public EvaluationItemDialog() {
        super();
        setWidget(uiBinder.createAndBindUi(this));
        this.setModal(true);
        //this.setAutoHideEnabled(true);
        //this.setGlassEnabled(true);
        this.center();
        this.hide();

        setupTree();

        btnSave.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                TreeItem ti = treeError.getSelectedItem();
                if (ti == null) {
                    Window.alert("Please select an error!");
                    return;
                }
                ErrorItem ei = (ErrorItem) ti.getUserObject();
                item.errorType = ei.ID;
                item.errorTittle = ei.title;
                item.comments = txtComments.getText();
                EvaluationItemDialog.this.hide();
                parentDlg.updateData();
            }
        });

        btnCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                onCancelPressed();
            }
        });
    }

    public void setParent(EvaluationTable parent) {
        this.parentDlg = parent;
    }

    public void setEvaluateItem(EvaluateItem item, boolean statePreChanged) {
        this.item = item;
        if (statePreChanged == true)
            this.oldStatus = !item.isWrong;
        else
            this.oldStatus = item.isWrong;

        htmlTripleS.setHTML("<strong>Subject</strong>: "
                + item.S.toHTMLString());
        htmlTripleP.setHTML("<strong>Predicate</strong>: "
                + item.P.toHTMLString());
        htmlTripleO
                .setHTML("<strong>Object</strong>: " + item.O.toHTMLString());
        openSubTree(item.errorType);
        txtComments.setText(item.comments);

        this.center();
    }

    public EvaluateItem getEvaluateItem() {
        return item;
    }

    private void setupTree() {

        // Get all error items from DB async callback
        AsyncCallback<List<ErrorItem>> errorCallback = new AsyncCallback<List<ErrorItem>>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                // SessionContext.hidePopup();
            }

            public void onSuccess(List<ErrorItem> items) {
                arrangeErrorTreeItems(items, null);
            }
        };

        // Get all error items from DB call
        SessionContext.evaluationReqSrv.getErrorChildren(-1, errorCallback);

        treeError.addSelectionHandler(new SelectionHandler<TreeItem>() {

            public void onSelection(SelectionEvent<TreeItem> event) {
                // TODO Auto-generated method stub
                final TreeItem treeItem = event.getSelectedItem();
                itemSelected(((ErrorItem) treeItem.getUserObject()));


            }
        });
    }

    private void itemSelected(ErrorItem errorItem) {
        ErrorItem tmpEI = errorItem;
        if (errorItem == null)
            tmpEI = new ErrorItem();

        if (tmpEI.exampleURI.equals(""))
            htmlExampleURI.setHTML("");
        else
            htmlExampleURI.setHTML("<strong>Example URI</strong>:</br>"
                    + tmpEI.exampleURI);

        if (tmpEI.exampleN3.equals(""))
            htmlExampleN3.setHTML("");
        else
            htmlExampleN3.setHTML("<strong>Example N3</strong>:</br>"
                    + tmpEI.exampleN3);

        if (tmpEI.exampleDesc.equals(""))
            htmlExampleDesc.setHTML("");
        else
            htmlExampleDesc
                    .setHTML("<strong>Description </strong>:</br>"
                            + tmpEI.exampleDesc);
    }

    private void arrangeErrorTreeItems(List<ErrorItem> items, TreeItem parentTI) {
        // Get root elements first (ID = 0)
        long parentEID = 0;
        if (parentTI != null)
            parentEID = ((ErrorItem) parentTI.getUserObject()).ID;

        for (ErrorItem i : items) {
            if (i.parentID == parentEID) {
                TreeItem t = null;
                if (parentTI == null)
                    t = treeError.addTextItem(i.title);
                else
                    t = parentTI.addTextItem(i.title);
                t.setUserObject(i);
                if (i.isLeaf == false)
                    arrangeErrorTreeItems(items, t);
            }
        }
        if (parentTI == null)
            items.clear();
    }

    private void openSubTree(long errorID) {
        // treeCloseAll(null);
        treeError.setSelectedItem(null, true);
        itemSelected(null);
        treeOpenAll(null);
        // errorTree.getItem(0).setState(true);
        if (errorID == 0) {
            // Do nothing for now
        } else {
            TreeItem ti = getTreeItemWithID(null, errorID);

            if (ti != null) {
                treeError.setSelectedItem(ti);
                ti.setState(true, true);
                TreeItem p = ti.getParentItem();
                while (p != null) {
                    p.setState(true, true);
                    p = p.getParentItem();
                }
            }
        }

    }

    private void treeCloseAll(TreeItem ti) {
        if (ti == null) {
            for (int i = 0; i < treeError.getItemCount(); i++)
                treeCloseAll(treeError.getItem(i));
        } else {
            for (int i = 0; i < ti.getChildCount(); i++)
                treeCloseAll(ti.getChild(i));
            ti.setState(false);
        }
    }

    private void treeOpenAll(TreeItem ti) {
        if (ti == null) {
            for (int i = 0; i < treeError.getItemCount(); i++)
                treeOpenAll(treeError.getItem(i));
        } else {
            for (int i = 0; i < ti.getChildCount(); i++)
                treeOpenAll(ti.getChild(i));
            ti.setState(true);
        }
    }

    private TreeItem getTreeItemWithID(TreeItem ti, long ID) {
        if (ti == null) {
            for (int i = 0; i < treeError.getItemCount(); i++) {
                ErrorItem er = (ErrorItem) treeError.getItem(i).getUserObject();
                if (er.ID == ID)
                    return treeError.getItem(i);
                TreeItem n = getTreeItemWithID(treeError.getItem(i), ID);
                if (n != null)
                    return n;
            }
        } else {
            for (int i = 0; i < ti.getChildCount(); i++) {
                ErrorItem er = (ErrorItem) ti.getChild(i).getUserObject();
                if (er != null && er.ID == ID)
                    return ti.getChild(i);
                TreeItem n = getTreeItemWithID(ti.getChild(i), ID);
                if (n != null)
                    return n;
            }
        }
        return null;
    }

    private void onCancelPressed() {
        EvaluationItemDialog.this.hide();
        item.isWrong = this.oldStatus;
        parentDlg.updateData();
    }

    protected void onPreviewNativeEvent(Event.NativePreviewEvent event) {
        super.onPreviewNativeEvent(event);
        switch (event.getTypeInt()) {
            case Event.ONKEYDOWN:
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
                    onCancelPressed();
                }
                break;
        }
    }
}
