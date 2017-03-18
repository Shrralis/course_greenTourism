package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class DistanceToCottage extends Model {
    public Cottage cottage = null;
    public Distance distance = null;

    public DistanceToCottage() {}
    @Override
    public DistanceToCottage parse(ResultSet from, Connection connection) {
        try {
            cottage = new List<>(get("SELECT * FROM `cottages` WHERE `id` = " +
                    from.getInt("cottage") + ";", connection), Cottage.class, connection).get(0);
            distance = new List<>(get("SELECT * FROM `distances` WHERE `id` = " +
                    from.getInt("distance") + ";", connection), Distance.class, connection).get(0);
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

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
