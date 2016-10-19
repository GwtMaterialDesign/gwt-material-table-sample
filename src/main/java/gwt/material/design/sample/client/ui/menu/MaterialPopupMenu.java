package gwt.material.design.sample.client.ui.menu;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import gwt.material.design.jquery.client.api.JQueryElement;
import gwt.material.design.client.ui.html.UnorderedList;

import static gwt.material.design.jquery.client.api.JQuery.$;

/**
 * Popup Menu> Menu
 *
 * @author Mark Kevin
 * @author Ben Dol
 */
public class MaterialPopupMenu extends UnorderedList implements HasSelectionHandlers<Element>{
    
    private String id;
    private Object selected;

    public MaterialPopupMenu() {
        id = DOM.createUniqueId();
        setInitialClasses("popup-menu","menu-bar", "z-depth-3");
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        StyleInjector.inject(MaterialPopupMenuClientBundle.INSTANCE.menuCss().getText());

        $(this).attr("tabindex", "0");
        $(this).on("blur", e -> {
            close();
            return true;
        });

        $("*").on("scroll." + id, e -> {
            close();
            return true;
        });

        initializeSelectionEvent();

        close();
    }

    private void initializeSelectionEvent() {
        // Initialization of Selection event
        $(".popup-menu li").off("click");
        $(".popup-menu li").on("click", e -> {
            e.stopPropagation();
            SelectionEvent.fire(MaterialPopupMenu.this, $(e.getCurrentTarget()).asElement());
            $(this).hide();
            return true;
        });

        // Check if the dropdown is not visible anymore into it's container either left / bottom side
        $(".popup-menu li").off("mouseover");
        $(".popup-menu li").on("mouseover", (e, param1) -> {
            JQueryElement item = $(e.getCurrentTarget()).find("a");
            if(item.attr("data-activates") != null) {
                JQueryElement dp = $("#" + item.attr("data-activates"));

                double dpWidth = dp.width();
                double dpLeft = dp.offset().left;
                double conWidth = body().width();

                double dpHeight = 200;
                double dpTop = dp.offset().top;
                double conHeight = body().height();

                if(dpWidth + dpLeft > conWidth) {
                    dp.addClass("edge-left");
                }

                if(dpHeight + dpTop > conHeight) {
                    dp.addClass("edge-bottom");
                }
            }
            return true;
        });

        $(".popup-menu li").off("mouseleave");
        $(".popup-menu li").on("mouseleave", (e, param) -> {
            JQueryElement item = $(e.getCurrentTarget()).find("a");
            if(item.attr("data-activates") != null) {
                JQueryElement dp = $("#" + item.attr("data-activates"));
                dp.removeClass("edge-left");
                dp.removeClass("edge-bottom");
            }
            return true;
        });
    }

    /**
     * Set the popup position of the context menu
     * @param x window x position
     * @param y window y position
     */
    public void setPopupPosition(int x, int y) {
        // Will check if the popup is out of container
        setLeft(x);
        setTop(y);
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<Element> selectionHandler) {
        return addHandler(selectionHandler, SelectionEvent.getType());
    }

    @Override
    protected void onDetach() {
        super.onDetach();

        $(this).off("." + id);
        $("*").off("." + id);
    }

    public void open() {
        setVisible(true);
        Scheduler.get().scheduleDeferred(() -> setFocus(true));

        // Check if dropdown is out of the container (Left)
        if($(this).width() +  $(this).offset().left > body().width()) {
            setLeft(body().width() - $(this).width());
        }
    }

    public void close() {
        setVisible(false);
    }

    public Object getSelected() {
        return selected;
    }

    public void setSelected(Object selected) {
        this.selected = selected;
    }
}
