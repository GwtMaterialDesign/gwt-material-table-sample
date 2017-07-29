package gwt.material.design.sample.client.ui.factory;

import gwt.material.design.client.data.DataView;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.component.CategoryComponent.OrphanCategoryComponent;
import gwt.material.design.client.data.factory.CategoryComponentFactory;
import gwt.material.design.sample.client.ui.StandardTable.CustomCategoryComponent;

public class CustomCategoryFactory extends CategoryComponentFactory {

    @Override
    public CategoryComponent generate(DataView dataView, String categoryName) {
        CategoryComponent category = super.generate(dataView, categoryName);

        if(!(category instanceof OrphanCategoryComponent)) {
            category = new CustomCategoryComponent(categoryName);
        }
        return category;
    }
}
