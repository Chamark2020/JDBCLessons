package Lesson_05.hw;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbclessons";
        String user = "root";
        String pass = "m?5C?Y9Wd#gaW{J@";

        Class.forName("com.mysql.cj.jdbc.Driver");

        ArrayList<String> sqlQueries = new ArrayList<>();
        String command1 = "insert into phonebook(name, phone) values('Alexey3', 88005553535)";
        String command2 = "insert into phonebook(names, phone) values('Nikolay3', 89001004000)";
        String command3 = "insert into phonebook(name, phone) values('Pluha3', 03)";
        sqlQueries.add(command1);
        sqlQueries.add(command2);
        sqlQueries.add(command3);


        try(Connection connection = DriverManager.getConnection(url, user, pass)) {
            executeListSqlQueries(connection, sqlQueries);
        }
    }

    static void executeListSqlQueries (Connection conn, ArrayList<String> queries) throws SQLException {
        try (Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            conn.setAutoCommit(false);

            try {
                for (String query : queries) {
                    statement.addBatch(query);
                }
                statement.executeBatch();
            } catch (SQLException ex) {
                conn.rollback();
                System.out.println("Произошла ошибка добавление строки в таблицу. Запущен метод rollback()");
            }
        } finally {
            conn.commit();
        }
    }
}
