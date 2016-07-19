package gwt.material.design.sample.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import gwt.material.design.sample.shared.model.People;

import java.util.List;

@RemoteServiceRelativePath("personService")
public interface PersonService extends RemoteService {

      People getPeople(int startIndex, int viewSize, List<String> categories);

      List<String> getCategories();
}