package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Client extends Owner {
    public String surname = null;
    public String passport = null;
    public String mobile = null;

    public Client() {}
    @SuppressWarnings("unused")
    public Client(ResultSet from) {
        parse(from);
    }

    @Override
    public Client parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            surname = from.getString("surname");
            passport = from.getString("passport");
            mobile = from.getString("mobile");
        } catch (SQLException ignored) {}
        return this;
    }
    @Override
    public String toString() {
        return name + " " + surname;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
