package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Distance extends Owner {
    public String destination_name = null;
    public Double kilometers = null;

    public Distance() {}
    @SuppressWarnings("unused")
    public Distance(ResultSet from) {
        parse(from);
    }
    @Override
    public Distance parse(ResultSet from) {
        super.parse(from);

        try {
            destination_name = from.getString("destination_name");
            kilometers = from.getDouble("kilometers");
        } catch (SQLException ignored) {}
        return this;
    }
    @Override
    public String toString() {
        return destination_name;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public void setDestination_name(String destination_name) {
        this.destination_name = destination_name;
    }

    public Double getKilometers() {
        return kilometers;
    }

    public void setKilometers(Double kilometers) {
        this.kilometers = kilometers;
    }
}
