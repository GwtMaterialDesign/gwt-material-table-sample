package gwt.material.design.sample.client.ui.datasource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;
import gwt.material.design.sample.client.service.PersonServiceAsync;
import gwt.material.design.sample.shared.model.People;
import gwt.material.design.sample.shared.model.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonDataSource implements DataSource<Person> {

    private final PersonServiceAsync personService;

    public PersonDataSource(PersonServiceAsync personService) {
        this.personService = personService;
    }

    @Override
    public void load(LoadConfig<Person> loadConfig, LoadCallback<Person> callback) {
        List<CategoryComponent> categories = loadConfig.getOpenCategories();
        List<String> categoryNames = categories.stream().map(CategoryComponent::getCategory).collect(Collectors.toList());

        personService.getPeople(loadConfig.getOffset(), loadConfig.getLimit(), categoryNames,
                new AsyncCallback<People>() {
            @Override
            public void onSuccess(People people) {
                callback.onSuccess(new LoadResult<Person>() {
                    @Override
                    public List<Person> getData() {
                        return people;
                    }
                    @Override
                    public int getOffset() {
                        return loadConfig.getOffset();
                    }
                    @Override
                    public int getTotalLength() {
                        return people.getAbsoluteTotal();
                    }
                });
            }
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("Getting people async call failed.", throwable);
                callback.onFailure(throwable);
            }
        });
    }

    @Override
    public boolean useRemoteSort() {
        return false;
    }
}
