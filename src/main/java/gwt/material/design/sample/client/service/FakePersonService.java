package gwt.material.design.sample.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.material.design.sample.shared.model.People;
import gwt.material.design.sample.shared.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakePersonService implements PersonServiceAsync {
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
    public void getPeople(int startIndex, int viewSize, List<String> categories, AsyncCallback<People> async) {
        List<Person> flatData = new ArrayList<>();
        if(categories == null) {
            // Load all data
            for(String category : FakePersonService.categories) {
                flatData.addAll(peopleMap.get(category));
            }
        } else {
            // Load data by categories
            for (String category : categories) {
                flatData.addAll(peopleMap.get(category));
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
        // Fake a delay for the demo
        new Timer() {
            @Override
            public void run() {
                async.onSuccess(people);
            }
        }.schedule(Math.min(200, Random.nextInt(500)));
    }

    @Override
    public void getCategories(AsyncCallback<List<String>> async) {
        // Fake a delay for the demo
        new Timer() {
            @Override
            public void run() {
                async.onSuccess(categories);
            }
        }.schedule(Math.min(200, Random.nextInt(500)));
    }
}
