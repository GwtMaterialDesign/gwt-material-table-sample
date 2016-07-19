package gwt.material.design.sample.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gwt.material.design.sample.shared.model.People;
import gwt.material.design.sample.shared.model.Person;
import gwt.material.design.sample.client.service.PersonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonServiceImpl extends RemoteServiceServlet implements PersonService {

    private static List<String> categories = new ArrayList<>();
    private static Map<String, List<Person>> peopleMap = new HashMap<>();
    static {
        categories.add("People");
        categories.add("Animal");
        categories.add("Plant");
        categories.add("Other");
        categories.add("Next");

        int index = 0;
        for(String category : categories) {
            for(int i = 0; i < 100; i++, index++) {
                List<Person> data = peopleMap.get(category);
                if(data == null) {
                    data = new ArrayList<>();
                    peopleMap.put(category, data);
                }
                data.add(new Person(index,
                    "http://joashpereira.com/templates/material_one_pager/img/avatar1.png",
                    category+index,
                    "Last"+index,
                    "Phone"+index,
                    category));
            }
        }
    }

    @Override
    public People getPeople(int startIndex, int viewSize, List<String> categories) {
        List<Person> flatData = new ArrayList<>();
        if(categories == null) {
            // Load all data
            for(String category : PersonServiceImpl.categories) {
                flatData.addAll(peopleMap.get(category));
            }
        } else {
            // Load data by categories
            for (String category : categories) {
                for (Person person : peopleMap.get(category)) {
                    flatData.add(person);
                }
            }
        }

        People people = new People();
        for(int i = startIndex; i < (startIndex + viewSize); i++) {
            try {
                people.add(flatData.get(i));
            } catch (IndexOutOfBoundsException e) {
                // ignored.
            }
        }
        people.setAbsoluteTotal(flatData.size());
        return people;
    }

    @Override
    public List<String> getCategories() {
        return categories;
    }
}