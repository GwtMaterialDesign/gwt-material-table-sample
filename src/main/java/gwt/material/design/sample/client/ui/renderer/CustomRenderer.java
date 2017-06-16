package gwt.material.design.sample.client.ui.renderer;

import gwt.material.design.client.data.BaseRenderer;
import gwt.material.design.client.ui.table.TableData;

public class CustomRenderer<T> extends BaseRenderer<T> {

    @Override
    public TableData drawSelectionCell() {
        TableData checkBox = super.drawSelectionCell();
        checkBox.addStyleName("frozen-col");
        return checkBox;
    }

}
