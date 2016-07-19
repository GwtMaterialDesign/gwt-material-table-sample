package gwt.material.design.sample.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.material.design.sample.shared.model.People;

import java.util.List;

public interface PersonServiceAsync {
    void getPeople(int startIndex, int viewSize, List<String> categories, AsyncCallback<People> async);

    void getCategories(AsyncCallback<List<String>> async);
}
