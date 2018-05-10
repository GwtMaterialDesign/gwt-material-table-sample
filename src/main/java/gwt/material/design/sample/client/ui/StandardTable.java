package gwt.material.design.sample.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.data.HasCategories;
import gwt.material.design.client.data.SortDir;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.component.Components;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.TableSubHeader;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;
import gwt.material.design.jquery.client.api.JQueryElement;
import gwt.material.design.sample.client.ui.factory.CustomCategoryFactory;
import gwt.material.design.sample.client.ui.factory.PersonRowFactory;
import gwt.material.design.sample.client.ui.menu.MaterialPopupMenu;
import gwt.material.design.sample.client.ui.renderer.CustomRenderer;
import gwt.material.design.sample.shared.model.Person;

import java.util.*;

import static gwt.material.design.jquery.client.api.JQuery.$;

public class StandardTable extends Composite {

    protected static final List<Person> people = new ArrayList<>();
    static {
        people.add(new Person(1, "Bill", "Jones", "123456", "Male"));
        people.add(new Person(2, "John", "Doe", "123456", "Male"));
        people.add(new Person(3, "Jim", "Smith", "123456", "Male"));
        people.add(new Person(4, "Beth", "Smith", "123456", "Female"));
        people.add(new Person(5, "Laura", "Doe", "123456", "Female"));
        people.add(new Person(6, "Anna", "Jones", "123456", "Female"));
    };

    public static class CustomCategoryComponent extends CategoryComponent {
        public CustomCategoryComponent(HasCategories parent, String name) {
            super(parent, name);
        }

        public CustomCategoryComponent(HasCategories parent, String name, boolean openByDefault) {
            super(parent, name, openByDefault);
        }

        @Override
        protected void render(TableSubHeader subheader, int columnCount) {
            super.render(subheader, columnCount);

            subheader.setOpenIcon(IconType.FOLDER_OPEN);
            subheader.setCloseIcon(IconType.FOLDER);
        }
    }

    interface StandardGridUiBinder extends UiBinder<HTMLPanel, StandardTable> {
    }

    private static StandardGridUiBinder ourUiBinder = GWT.create(StandardGridUiBinder.class);

    @UiField
    MaterialDataTable<Person> table;

    @UiField
    MaterialPopupMenu popupMenu;

    @UiField
    MaterialButton sortBtn;

    public StandardTable() {
        long startTime = new Date().getTime();

        initWidget(ourUiBinder.createAndBindUi(this));

        // We will manually add this category otherwise categories
        // can be loaded on the fly with HasDataCategory, or a custom
        // RowComponentFactory as demonstrated below
        //table.addCategory(new CustomCategoryComponent("Custom Category"));

        // We will define our own person row factory to generate the
        // category name. This can be used to generate your own
        // RowComponent's too, if custom functionality is required.
        table.setRowFactory(new PersonRowFactory());

        // If we want to generate all our categories using CustomCategoryComponent
        // We can define our own CategoryComponentFactory. There we can define our
        // own CategoryComponent implementations.
        table.setCategoryFactory(new CustomCategoryFactory());

        // It is possible to create your own custom renderer per table
        // When you use the BaseRenderer you can override certain draw
        // methods to create elements the way you would like.
        table.setRenderer(new CustomRenderer<>());

        // Now we will add our tables columns.
        // There are a number of methods that can provide custom column configurations.

        // Add an image profile on each category rows
        /*table.addColumn(new WidgetColumn<Person, MaterialImage>() {
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
        });*/

        table.addColumn(new WidgetColumn<Person, MaterialButton>() {
            @Override
            public Comparator<? super RowComponent<Person>> sortComparator() {
                return (o1, o2) -> o1.getData().getFirstName().compareToIgnoreCase(o2.getData().getFirstName());
            }
            @Override
            public MaterialButton getValue(Person object) {
                /*MaterialCheckBox box = new MaterialCheckBox();
                box.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                    @Override
                    public void onValueChange(ValueChangeEvent<Boolean> event) {
                        MaterialToast.fireToast("Clicked");
                    }
                });*/
                return new MaterialButton("ASdasd");
            }
            @Override
            public String width() {
                return "100px";
            }
        }, "First Name");

        table.addColumn(new WidgetColumn<Person, MaterialComboBox<String>>() {
            @Override
            public Comparator<? super RowComponent<Person>> sortComparator() {
                return (o1, o2) -> o1.getData().getFirstName().compareToIgnoreCase(o2.getData().getFirstName());
            }
            @Override
            public MaterialComboBox<String> getValue(Person person) {
                MaterialComboBox<String> mcb = new MaterialComboBox<>();
                List<String> values = new ArrayList<>();
                values.add("North");
                values.add("East");
                values.add("South");
                values.add("West");
                mcb.addItems(values);
                mcb.addClickHandler(event -> {
                    event.getNativeEvent().stopPropagation();
                });
                mcb.addValueChangeHandler(new ValueChangeHandler<List<String>>() {

                    @Override
                    public void onValueChange(ValueChangeEvent<List<String>> event) {
                        MaterialToast.fireToast("Location changed");


                    }
                });
                return mcb;
            }
            @Override
            public String width() {
                return "100px";
            }
        }, "First Name2");

        table.addColumn(new TextColumn<Person>() {
            @Override
            public Comparator<? super RowComponent<Person>> sortComparator() {
                return (o1, o2) -> o1.getData().getFirstName().compareToIgnoreCase(o2.getData().getFirstName());
            }
            @Override
            public String getValue(Person object) {
                return object.getLastName();
            }

            @Override
            public boolean autoSort() {
                return false;
            }
        }, "Last Name");

        for (int i = 0; i < 4; i++) {
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

        table.addColumn(new TextColumn<Person>() {
            @Override
            public boolean numeric() {
                return true;
            }
            @Override
            public Comparator<? super RowComponent<Person>> sortComparator() {
                return (o1, o2) -> o1.getData().getPhone().compareToIgnoreCase(o2.getData().getPhone());
            }
            @Override
            public String getValue(Person object) {
                return object.getPhone();
            }
        }, "Phone2332");

        table.addColumn(new TextColumn<Person>() {
            @Override
            public Comparator<? super RowComponent<Person>> sortComparator() {
                return (o1, o2) -> o1.getData().getPhone().compareToIgnoreCase(o2.getData().getPhone());
            }
            @Override
            public String getValue(Person object) {
                return object.getPhone();
            }
        }, "Ph56656656");

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

        table.setVisibleRange(0, 200);

        // Generate 20 categories
        List<Person> people = new ArrayList<>();
        int rowIndex = 0;
        for(int k = 1; k <= 5; k++){
            // Generate 100 rows
            for(int i = 1; i <= 20; i++, rowIndex++) {
                people.add(new Person(i, "http://joashpereira.com/templates/material_one_pager/img/avatar1.png", "Field " + rowIndex, "Field " + i, "No " + i,"Category " + k));
            }
        }
        table.setRowData(0, people);

        // Added access to ToolPanel to add icon widget
        Panel panel = table.getScaffolding().getToolPanel();
        MaterialIcon polyIcon = new MaterialIcon(IconType.POLYMER);
        polyIcon.setWaves(WavesType.LIGHT);
        polyIcon.setCircle(true);
        panel.add(polyIcon);

        // Set the visible range of the table for pager (later)
        table.setVisibleRange(0, 2001);

        // Here we are adding a row expansion handler.
        // This is invoked when a row is expanded.
        table.addRowExpandingHandler(event -> {
            // Fake Async Task
            // This is demonstrating a fake asynchronous call to load
            // the data inside the expansion element.
            new Timer() {
                @Override
                public void run() {
                    // Clear the content first.
                    JQueryElement element = event.getExpansion().getContent().empty();
                    // Assign the jquery element to a GMD Widget
                    MaterialWidget content = new MaterialWidget(element);

                    // Add new content.
                    MaterialBadge badge = new MaterialBadge("This content", Color.WHITE, Color.BLUE);
                    badge.getElement().getStyle().setPosition(Position.RELATIVE);
                    badge.getElement().getStyle().setRight(0, Unit.PX);
                    badge.setFontSize(12, Unit.PX);
                    content.add(badge);

                    MaterialButton btn = new MaterialButton("was made", IconType.FULLSCREEN, ButtonType.RAISED);
                    btn.setWaves(WavesType.DEFAULT);
                    content.add(btn);

                    MaterialTextBox textBox = new MaterialTextBox();
                    textBox.setText(" from an asynchronous");
                    textBox.setGwtDisplay(Display.INLINE_TABLE);
                    textBox.setWidth("200px");
                    content.add(textBox);

                    MaterialIcon icon = new MaterialIcon(IconType.CALL);
                    icon.getElement().getStyle().setPosition(Position.RELATIVE);
                    icon.getElement().getStyle().setTop(12, Unit.PX);
                    content.add(icon);

                    // Hide the expansion elements overlay section.
                    // The overlay is retrieved using RowExpansion#getOverlay()
                    event.getExpansion().getOverlay().css("display", "none");
                }
            }.schedule(2000);
        });

        // Add a row select handler, called when a user selects a row.
        table.addRowSelectHandler(event -> {
            GWT.log(event.getModel().getId() + ": " + event.isSelected());
        });

        // Add category opened handler, called when a category is opened.
        table.addCategoryOpenedHandler(event -> {
            GWT.log("Category Opened: " + event.getName());
        });

        // Add category closed handler, called when a category is closed.
        table.addCategoryClosedHandler(event -> {
            GWT.log("Category Closed: " + event.getName());
        });

        // Add a row double click handler, called when a row is double clicked.
        table.addRowDoubleClickHandler(event -> {
            // GWT.log("Row Double Clicked: " + model.getId() + ", x:" + mouseEvent.getPageX() + ", y: " + mouseEvent.getPageY());
            Window.alert("Row Double Clicked: " + event.getModel().getId());
        });

        // Configure the tables long press duration configuration.
        // The short press is when a click is held less than this duration.
        table.setLongPressDuration(400);

        // Add a row long press handler, called when a row is long pressed.
        table.addRowLongPressHandler(event -> {
            //GWT.log("Row Long Pressed: " + model.getId() + ", x:" + mouseEvent.getPageX() + ", y: " + mouseEvent.getPageY());
        });

        // Add a row short press handler, called when a row is short pressed.
        table.addRowShortPressHandler(event -> {
            //.log("Row Short Pressed: " + model.getId() + ", x:" + mouseEvent.getPageX() + ", y: " + mouseEvent.getPageY());
        });

        popupMenu.addSelectionHandler(selectionEvent -> {
            JQueryElement span = $(selectionEvent.getSelectedItem()).find("span");
            for(Person per : table.getSelectedRowModels(false)){
                MaterialToast.fireToast($(span).asElement().getInnerHTML() + " : " + per.getFirstName());
            }
        });

        table.addRowContextMenuHandler(event -> {
            // Firing Row Context will automatically select the row where it was right clicked
            table.selectRow($(event.getRow()).asElement(), true);
            popupMenu.setSelected(event.getModel());
            popupMenu.setPopupPosition(event.getMouseEvent().getClientX(), event.getMouseEvent().getClientY());
            popupMenu.open();
        });

        table.addRenderedHandler(e -> {
            long endTime = new Date().getTime();
            GWT.log(endTime - startTime + " ms");
        });

        /*table.addRenderedHandler(event -> {
            Window.alert("Rendered!");
        });

        table.addComponentsRenderedHandler(event -> {
            Window.alert("Components Rendered!");
        });*/
    }

    @UiHandler("sortBtn")
    void onSortBtnClick(ClickEvent event) {
        Components<RowComponent<Person>> beforeRows = new Components<>(table.getRows(), RowComponent::new);
        table.sort(1, SortDir.DESC);
        Components<RowComponent<Person>> descRows = new Components<>(table.getRows(), RowComponent::new);
        table.sort(1, SortDir.ASC);
        Components<RowComponent<Person>> ascRows = new Components<>(table.getRows(), RowComponent::new);
        GWT.log("beforeRows:");
        printRows(beforeRows);
        GWT.log("descRows:");
        printRows(descRows);
        GWT.log("ascRows:");
        printRows(ascRows);
    }

    public static <T> void printRows(Components<RowComponent<T>> rowComponents) {
        String msg = "[";
        for(RowComponent<T> row : rowComponents) {
            msg += row.getData() + ", ";
        }
        GWT.log(msg + "]");
    }

    @UiHandler("cbCategories")
    void onCategories(ValueChangeEvent<Boolean> e) {
        if(e.getValue()){
            table.setUseCategories(true);
            GWT.log("Categories checked");
        } else {
            table.setUseCategories(false);
            GWT.log("Categories not checked");
        }
        table.getView().setRedraw(true);
        table.getView().refresh();
    }
}