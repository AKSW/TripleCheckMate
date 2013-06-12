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
package org.aksw.TripleCheckMate.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class test extends Composite {

    public test() {


        TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(1.5, Unit.EM);

        ScrollPanel scrollPanel = new ScrollPanel();
        tabLayoutPanel.add(scrollPanel, "New Widget", false);

        DecoratedTabPanel decoratedTabPanel = new DecoratedTabPanel();
        tabLayoutPanel.add(decoratedTabPanel, "New Widget", false);
        initWidget(tabLayoutPanel);
    }

}
