package Lesson_05;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbclessons";
        String user = "root";
        String pass = "m?5C?Y9Wd#gaW{J@";

        Class.forName("com.mysql.cj.jdbc.Driver");


        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            String createTable = "create table fruit(name varchar(15) not null, amount int, price double)";
            String command1 = "insert into fruit(name, amount, price) values('Apple', 200, 3.50)";
            String command2 = "insert into fruit(name, amount, price) values('Orange', 50, 5.50)";
            String command3 = "insert into fruit(name, amount, price) values('Lemon', 30, 5.50)";
            String command4 = "insert into fruit(name, amount, price) values('Pineapple', 20, 7.50)";

            // Пошаговое добавление строк в таблицу
          /*  connection.setAutoCommit(false);
            statement.executeUpdate(createTable);
            statement.executeUpdate(command1);
            Savepoint savepoint = connection.setSavepoint();
            statement.executeUpdate(command2);
            statement.executeUpdate(command3);
            statement.executeUpdate(command4);
            connection.rollback(savepoint);
            connection.commit();
            connection.releaseSavepoint(savepoint);*/

            // Добавление всех строк в таблицу за один шаг
            connection.setAutoCommit(true);
            statement.addBatch(createTable);
            statement.addBatch(command1);
            statement.addBatch(command2);
            statement.addBatch(command3);
            statement.addBatch(command4);
            statement.executeBatch();

        }
    }
}
