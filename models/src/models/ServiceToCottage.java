package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/18/17.
 */
public class ServiceToCottage extends Model {
    public Cottage cottage = null;
    public Service service = null;

    public ServiceToCottage() {}
    @Override
    public ServiceToCottage parse(ResultSet from, Connection connection) {
        try {
            cottage = new List<>(get("SELECT * FROM `cottages` WHERE `id` = " +
                    from.getInt("cottage") + ";", connection), Cottage.class, connection).get(0);
            service = new List<>(get("SELECT * FROM `services` WHERE `id` = " +
                    from.getInt("service") + ";", connection), Service.class, connection).get(0);
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
