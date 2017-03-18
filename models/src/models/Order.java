package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

/**
 * Created by shrralis on 3/15/17.
 */
public class Order extends Owner {
    public Date date_from = null;
    public Date date_to = null;
    public Cottage cottage = null;
    public Client client = null;

    public Order() {}
    @SuppressWarnings("unused")
    public Order(ResultSet from) {
        parse(from);
    }
    @Override
    public Owner parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            date_from = DateWorker.convertToDate(from.getString("date_from"));
            date_to = DateWorker.convertToDate(from.getString("date_to"));
            cottage = new List<>(get("SELECT * FROM `cottages` WHERE `id` = " +
                    from.getInt("cottage") + ";", connection), Cottage.class, connection).get(0);
            client = new List<>(get("SELECT * FROM `clients` WHERE `id` = " +
                    from.getInt("client") + ";", connection), Client.class, connection).get(0);
        } catch (SQLException ignored) {}
        return this;
    }
    @Override
    public String toString() {
        return DateWorker.convertDateToString(date_from) + " - " +
                DateWorker.convertDateToString(date_to) + " (" + cottage + ")";
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public Cottage getCottage() {
        return cottage;
    }

    public void setCottage(Cottage cottage) {
        this.cottage = cottage;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
