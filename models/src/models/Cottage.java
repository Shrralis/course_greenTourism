package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Cottage extends Owner {
    public String district = null;
    public String village = null;
    public String address = null;
    public String coordinates = null;
    public Double price_per_day = null;
    public Company company = null;

    public Cottage() {}
    @SuppressWarnings("unused")
    public Cottage(ResultSet from) {
        parse(from);
    }
    @Override
    public Cottage parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            district = from.getString("district");
            village = from.getString("village");
            address = from.getString("address");
            coordinates = from.getString("coordinates");
            price_per_day = from.getDouble("price_per_day");
            company = new List<>(get("SELECT * FROM `companies` WHERE `id` = " +
                    from.getInt("company") + ";", connection), Company.class, connection).get(0);
        } catch (SQLException ignored) {}
        return this;
    }
    @Override
    public String toString() {
        return name + " (" + company.toString() + ")";
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Double getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(Double price_per_day) {
        this.price_per_day = price_per_day;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
