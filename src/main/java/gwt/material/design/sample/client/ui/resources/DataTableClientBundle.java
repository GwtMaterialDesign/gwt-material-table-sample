package gwt.material.design.sample.client.ui.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * Client Bundle for Icon Morph component
 * @author kevzlou7979
 */
public interface DataTableClientBundle extends ClientBundle {

    DataTableClientBundle INSTANCE = GWT.create(DataTableClientBundle.class);

    @Source("css/data-table.css")
    TextResource dataTable();
}
