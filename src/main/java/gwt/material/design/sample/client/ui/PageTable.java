package gwt.material.design.sample.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import gwt.material.design.jquery.client.api.JQueryElement;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.pager.*;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;
import gwt.material.design.sample.client.ui.factory.PersonRowFactory;
import gwt.material.design.sample.client.ui.menu.MaterialPopupMenu;
import gwt.material.design.sample.client.ui.renderer.CustomRenderer;
import gwt.material.design.sample.shared.model.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static gwt.material.design.jquery.client.api.JQuery.$;

public class PageTable extends Composite {

    interface PageGridUiBinder extends UiBinder<HTMLPanel, PageTable> {
    }

    private static PageGridUiBinder ourUiBinder = GWT.create(PageGridUiBinder.class);

    @UiField
    MaterialDataTable<Person> table;

    @UiField
    MaterialPopupMenu popupMenu;

    private MaterialDataPager pager;
    private ListDataSource<Person> dataSource;

    public PageTable() {
        // Generate 20 categories
        int rowIndex = 1;
        List<Person> people = new ArrayList<>();
        for(int i = 1; i <= 100; i++, rowIndex++){
            people.add(new Person(i, "http://joashpereira.com/templates/material_one_pager/img/avatar1.png", "Field " + rowIndex, "Field " + i, "No " + i,"Category 1" + ""));
        }

        initWidget(ourUiBinder.createAndBindUi(this));

        dataSource = new ListDataSource<>();
        dataSource.add(0, people);
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        pager = new MaterialDataPager<>(table, dataSource);
        //pager.setLimitOptions(5, 10, 15, 20);
        table.add(pager);

        // We will manually add this category otherwise categories
        // can be loaded on the fly with HasDataCategory, or a custom
        // RowComponentFactory as demonstrated below
        //table.addCategory(new CustomCategoryComponent("Custom Category"));

        // We will define our own person row factory to generate the
        // category name. This can be used to generate your own
        // RowComponent's too, if custom functionality is required.
        table.setRowFactory(new PersonRowFactory());

        // It is possible to create your own custom renderer per table
        // When you use the BaseRenderer you can override certain draw
        // methods to create elements the way you would like.
        table.setRenderer(new CustomRenderer<>());

        // Now we will add our tables columns.
        // There are a number of methods that can provide custom column configurations.

        // Add an image profile on each category rows
        table.addColumn(new WidgetColumn<Person, MaterialImage>() {
            @Override
            public MaterialImage getValue(Person object) {
                MaterialImage profile = new MaterialImage();
                profile.setUrl(object.getPicture());
                profile.setWidth("40px");
                profile.setHeight("40px");
                profile.setPadding(4);
                profile.setMarginTop(8);
                profile.setBackgroundColor(Color.GREY_LIGHTEN_2);
                profile.setCircle(true);
                return profile;
            }
        });


        table.addColumn(new TextColumn<Person>() {
            @Override
            public Comparator<? super RowComponent<Person>> sortComparator() {
                return (o1, o2) -> o1.getData().getFirstName().compareToIgnoreCase(o2.getData().getFirstName());
            }
            @Override
            public String getValue(Person object) {
                return object.getFirstName();
            }
        }, "First Name");

        table.addColumn(new TextColumn<Person>() {
            @Override
            public Comparator<? super RowComponent<Person>> sortComparator() {
                return (o1, o2) -> o1.getData().getLastName().compareToIgnoreCase(o2.getData().getLastName());
            }
            @Override
            public String getValue(Person object) {
                return object.getLastName();
            }
        }, "Last Name");

        table.addColumn(new TextColumn<Person>() {
            @Override
            public boolean numeric() {
                return true;
            }
            @Override
            public HideOn hideOn() {
                return HideOn.HIDE_ON_MED_DOWN;
            }
            @Override
            public Comparator<? super RowComponent<Person>> sortComparator() {
                return (o1, o2) -> o1.getData().getPhone().compareToIgnoreCase(o2.getData().getPhone());
            }
            @Override
            public String getValue(Person object) {
                return object.getPhone();
            }
        }, "Phone");

        for(int i = 0; i < 8; i++) {
            final int index = i;
            table.addColumn(new TextColumn<Person>() {
                @Override
                public Comparator<? super RowComponent<Person>> sortComparator() {
                    return (o1, o2) -> o1.getData().getPhone().compareToIgnoreCase(o2.getData().getPhone());
                }
                @Override
                public String getValue(Person object) {
                    return object.getPhone() + " " + index;
                }
            }, "Column " + index);
        }

        // Example of a widget column!
        // You can add any handler to the column cells widget.
        table.addColumn(new WidgetColumn<Person, MaterialBadge>() {
            @Override
            public TextAlign textAlign() {
                return TextAlign.CENTER;
            }
            @Override
            public MaterialBadge getValue(Person object) {
                MaterialBadge badge = new MaterialBadge();
                badge.setText("badge " + object.getId());
                badge.setBackgroundColor(Color.BLUE);
                badge.setLayoutPosition(Position.RELATIVE);
                return badge;
            }
        });

        // Added access to ToolPanel to add icon widget
        Panel panel = table.getScaffolding().getToolPanel();
        MaterialIcon polyIcon = new MaterialIcon(IconType.POLYMER);
        polyIcon.setWaves(WavesType.LIGHT);
        polyIcon.setCircle(true);
        panel.add(polyIcon);
    }

    @UiHandler("btnGotoFirstPage")
    void onGotoFirstPage(ClickEvent e) {
        pager.firstPage();
    }

    @UiHandler("btnGotoLastPage")
    void onGotoLastPage(ClickEvent e) {
        pager.lastPage();
    }
}