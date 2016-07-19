package gwt.material.design.sample.client.ui.datasource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.infinite.InfiniteDataSource;
import gwt.material.design.client.data.infinite.InfiniteDataView;
import gwt.material.design.sample.client.service.PersonServiceAsync;
import gwt.material.design.sample.shared.model.People;
import gwt.material.design.sample.shared.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDataSource extends InfiniteDataSource<Person> {

    private final PersonServiceAsync personService;

    public PersonDataSource(PersonServiceAsync personService) {
        this.personService = personService;
    }

    @Override
    public void load(InfiniteDataView<Person> dataView, int startIndex, int viewSize, List<CategoryComponent> categories) {
        List<String> categoryNames = null;
        if(dataView.isUseCategories()) {
            categoryNames = new ArrayList<>();
            for (CategoryComponent category : categories) {
                categoryNames.add(category.getCategory());
            }
        }
        personService.getPeople(startIndex, viewSize, categoryNames, new AsyncCallback<People>() {
            @Override
            public void onSuccess(People people) {
                dataView.loaded(startIndex, people, people.getAbsoluteTotal(), true);
            }
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("Getting people async call failed.", throwable);
            }
        });
    }
}
