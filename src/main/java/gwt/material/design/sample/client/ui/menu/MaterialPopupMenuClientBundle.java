package gwt.material.design.sample.client.ui.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * Client Bundle for Icon Morph component
 * @author kevzlou7979
 */
interface MaterialPopupMenuClientBundle extends ClientBundle {

    MaterialPopupMenuClientBundle INSTANCE = GWT.create(MaterialPopupMenuClientBundle.class);

    @Source("resources/css/popup-menu.css")
    TextResource menuCss();
}
