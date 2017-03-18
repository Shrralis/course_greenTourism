package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/15/17.
 */
public class Specialization extends Owner {

    public Specialization() {}
    @SuppressWarnings("unused")
    public Specialization(ResultSet from) {
        parse(from);
    }
}
