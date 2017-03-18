package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/18/17.
 */
public class Service extends Owner {
    public Service() {}
    @SuppressWarnings("unused")
    public Service(ResultSet from) {
        parse(from);
    }
}
