package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class SpecializationToCottage extends Model {
    public Cottage cottage = null;
    public Specialization specialization = null;

    public SpecializationToCottage() {}
    @Override
    public SpecializationToCottage parse(ResultSet from, Connection connection) {
        try {
            cottage = new List<>(get("SELECT * FROM `cottages` WHERE `id` = " +
                    from.getInt("cottage") + ";", connection), Cottage.class, connection).get(0);
            specialization = new List<>(get("SELECT * FROM `specializations` WHERE `id` = " +
                    from.getInt("specialization") + ";", connection), Specialization.class, connection).get(0);
        } catch (SQLException ignored) {}
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public Cottage getCottage() {
        return cottage;
    }

    public void setCottage(Cottage cottage) {
        this.cottage = cottage;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
