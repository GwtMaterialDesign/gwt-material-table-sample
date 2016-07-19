package gwt.material.design.sample.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

public class People extends ArrayList<Person> implements Serializable {

    private int absoluteTotal = 0;

    public People() {
    }

    public void setAbsoluteTotal(int absoluteTotal) {
        this.absoluteTotal = absoluteTotal;
    }

    public int getAbsoluteTotal() {
        return absoluteTotal;
    }
}
