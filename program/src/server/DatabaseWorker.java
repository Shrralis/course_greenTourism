package server;

import models.*;

import java.sql.*;

/**
 * Created by shrralis on 3/12/17.
 */
@SuppressWarnings("unchecked")
public class DatabaseWorker {
    private static DatabaseWorker iam = null;
    private Connection connection = null;

    private DatabaseWorker() {}

    public static boolean openConnection() {
        if (iam == null) {
            iam = new DatabaseWorker();
        } else {
            closeConnection();
            return openConnection();
        }

        final String sDatabaseName = "green_tourism";
        final String sServerUser = "ТУТ_ВВЕДИ_ІМ'Я_КОРИСТУВАЧА_MYSQL_SERVER";
        final String sServerPassword = "ТУТ_ВВЕДИ_ПАРОЛЬ_КОРИСТУВАЧА_MYSQL_SERVER";

        try {
            if (iam.connection == null || iam.connection.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                iam.connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + sDatabaseName + "?useUnicode=true&characterEncoding=UTF-8",
                        sServerUser,
                        sServerPassword
                );
            }
            return true;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void closeConnection() {
        try {
            if (iam != null) {
                if (iam.connection != null && !iam.connection.isClosed()) {
                    iam.connection.close();
                }
                iam = null;
            }
        } catch (SQLException ignored) {}
    }

    public static ServerResult processQuery(ServerQuery query) {
        if (query == null) {
            System.out.println("No query from the server!");
            return ServerResult.create(1, "No query");
        } else {
            String method = query.getMethodName();

            if (method.equalsIgnoreCase("disconnect")) {
                return ServerResult.create(0, "disconnect");
            }

            if (method.equalsIgnoreCase("get")) {
                return iam.get(query);
            }

            if (method.equalsIgnoreCase("add")) {
                return iam.add(query);
            }

            if (method.equalsIgnoreCase("delete")) {
                return iam.delete(query);
            }

            if (method.equalsIgnoreCase("edit")) {
                return iam.edit(query);
            }
        }
        return null;
    }

    private ServerResult get(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName().toLowerCase();
            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(companies)|(cottages(_has_((distance)|(service)|(specialization))s)?)" +
                        "|(clients)|(distances)|(services)|(specializations)|(orders)$")) {
                    if (table.equalsIgnoreCase("cottages_has_services")) {
                        String sql;

                        if (query.getMySQLCondition().matches("(\\D|\\d)+service(\\D|\\d)+")) {
                            sql = "SELECT c.* FROM `cottages_has_services` chs, `cottages` c" +
                                    query.getMySQLCondition() + " AND chs.`cottage` = c.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Cottage.class, connection));
                        } else {
                            sql = "SELECT s.* FROM `cottages_has_services` chs, `services` s" +
                                    query.getMySQLCondition() + " AND chs.`service` = s.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Service.class));
                        }
                        return result;
                    }

                    if (table.equalsIgnoreCase("cottages_has_specializations")) {
                        String sql;

                        if (query.getMySQLCondition().matches("(\\D|\\d)+specialization(\\D|\\d)+")) {
                            sql = "SELECT c.* FROM `cottages_has_specializations` chs, `cottages` c" +
                                    query.getMySQLCondition() + " AND chs.`cottage` = c.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Cottage.class, connection));
                        } else {
                            sql = "SELECT s.* FROM `cottages_has_specializations` chs, `specializations` s" +
                                    query.getMySQLCondition() + " AND chs.`specialization` = s.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Specialization.class));
                        }
                        return result;
                    }

                    if (table.equalsIgnoreCase("cottages_has_distances")) {
                        String sql;

                        if (query.getMySQLCondition().matches("(\\D|\\d)+distance(\\D|\\d)+")) {
                            sql = "SELECT c.* FROM `cottages_has_distances` chd, `cottages` c" +
                                    query.getMySQLCondition() + " AND chd.`cottage` = c.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Cottage.class, connection));
                        } else {
                            sql = "SELECT d.*, chd.`kilometers` FROM `cottages_has_distances` chd, `distances` d" +
                                    query.getMySQLCondition() + " AND chd.`distance` = d.`id`;";
                            result = ServerResult.create(new List(statement.executeQuery(sql), Distance.class));
                        }
                        return result;
                    }

                    ResultSet resultSet = statement.executeQuery("SELECT * FROM `" + table + "`" +
                            query.getMySQLCondition() + ";");

                    if (table.equalsIgnoreCase("companies")) {
                        result = ServerResult.create(new List(resultSet, Company.class, connection));
                    } else if (table.equalsIgnoreCase("cottages")) {
                        result = ServerResult.create(new List(resultSet, Cottage.class, connection));
                    } else if (table.equalsIgnoreCase("clients")) {
                        result = ServerResult.create(new List(resultSet, Client.class, connection));
                    } else if (table.equalsIgnoreCase("distances")) {
                        result = ServerResult.create(new List(resultSet, Distance.class));
                    } else if (table.equalsIgnoreCase("specializations")) {
                        result = ServerResult.create(new List(resultSet, Specialization.class));
                    } else if (table.equalsIgnoreCase("orders")) {
                        result = ServerResult.create(new List(resultSet, Order.class, connection));
                    } else if (table.equalsIgnoreCase("services")) {
                        result = ServerResult.create(new List(resultSet, Service.class, connection));
                    }
                } else {
                    System.out.println("Unknown table (" + table + ") for get()");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ServerResult add(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName().toLowerCase();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(companies)|(cottages(_has_((distance)|(service)|(specialization))s)?)" +
                        "|(clients)|(distances)|(services)|(specializations)|(orders)$")) {
                    System.out.println(query.getInsertMysqlQuery());

                    int iResult = statement.executeUpdate(query.getInsertMysqlQuery(), Statement.RETURN_GENERATED_KEYS);

                    if (iResult >= 0) {
                        ResultSet rs = statement.getGeneratedKeys();
                        int id = 0;

                        if (rs.next()) {
                            id = rs.getInt(1);
                        }

                        if (table.matches("^cottages_has_((distance)|(service)|(specialization))s$")) {
                            result = ServerResult.create(0, "successfully added");
                        } else {
                            result = ServerResult.create(
                                    new List(statement.executeQuery("SELECT * FROM `" + table + "` WHERE `id` = " + id + ";"),
                                            query.getObjectToProcess().getClass(), connection)
                            );
                        }
                    } else {
                        result = ServerResult.create(1, "not added");
                    }
                }
            }
        } catch (SQLException | IllegalAccessException ignored) {}
        return result;
    }

    private ServerResult delete(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName().toLowerCase();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(companies)|(cottages(_has_((distance)|(service)|(specialization))s)?)" +
                        "|(clients)|(distances)|(services)|(specializations)|(orders)$")) {
                    int iResult = statement.executeUpdate("DELETE FROM `" + table + "` WHERE `id` = "
                            + ((Owner) query.getObjectToProcess()).getId() + ";");

                    if (iResult >= 0) {
                        result = ServerResult.create(0, "deleted");
                    } else {
                        result = ServerResult.create(1, "not deleted");
                    }
                }
            }
        } catch (SQLException ignored) {}
        return result;
    }

    private ServerResult edit(ServerQuery query) {
        ServerResult result = null;

        try {
            String table = query.getTableName();

            if (connection != null && !connection.isClosed()) {
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (table.matches("^(companies)|(cottages(_has_((distance)|(service)|(specialization))s)?)" +
                        "|(clients)|(distances)|(services)|(specializations)|(orders)$")) {
                    int iResult = statement.executeUpdate(query.getUpdateMysqlQuery(), Statement.RETURN_GENERATED_KEYS);

                    if (iResult >= 0) {
                        int id = ((Owner) query.getObjectToProcess()).getId();

                        result = ServerResult.create(
                                new List(statement.executeQuery("SELECT * FROM `" + table + "` WHERE `id` = " + id + ";"),
                                        query.getObjectToProcess().getClass(), connection)
                        );
                        System.out.println("Size: " + result.getObjects().size());
                    } else {
                        result = ServerResult.create(1, "not updated");
                    }
                }
            }
        } catch (SQLException | IllegalAccessException ignored) {}
        return result;
    }
}
