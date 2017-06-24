package gwt.material.design.sample.client;

/*
 * #%L
 * GwtMaterialDesign
 * %%
 * Copyright (C) 2015 GwtMaterial
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import gwt.material.design.sample.client.ui.InfiniteTable;
import gwt.material.design.sample.client.ui.PageTable;
import gwt.material.design.sample.client.ui.StandardTable;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.sample.client.ui.resources.DataTableClientBundle;

public class MaterialTableSample implements EntryPoint {

    @Override
    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(e -> GWT.log(e.getMessage(), e));
        StyleInjector.inject(DataTableClientBundle.INSTANCE.dataTable().getText());
        Scheduler.get().scheduleDeferred(() -> {
            final Composite[] table = new Composite[1];

            MaterialAnchorButton stdBtn = new MaterialAnchorButton("Standard");
            RootPanel.get().add(stdBtn);
            stdBtn.addClickHandler(e -> {
                if(table[1] != null) {
                    table[1].removeFromParent();
                }
                table[1] = new StandardTable();
                RootPanel.get().add(table[1]);
            });

            MaterialAnchorButton infBtn = new MaterialAnchorButton("Infinite");
            RootPanel.get().add(infBtn);
            infBtn.addClickHandler(e -> {
                if(table[1] != null) {
                    table[1].removeFromParent();
                }
                table[1] = new InfiniteTable();
                RootPanel.get().add(table[1]);
            });

            MaterialAnchorButton pageBtn = new MaterialAnchorButton("With Pager");
            RootPanel.get().add(pageBtn);
            pageBtn.addClickHandler(e -> {
                if(table[1] != null) {
                    table[1].removeFromParent();
                }
                table[1] = new PageTable();
                RootPanel.get().add(table[1]);
            });
        });
    }
}
