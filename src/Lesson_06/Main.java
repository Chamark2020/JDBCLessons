package Lesson_06;

import java.sql.*;

public class Main {
    static String url = "jdbc:mysql://localhost:3306/jdbclessons";
    static String user = "root";
    static String pass = "m?5C?Y9Wd#gaW{J@";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);

            //dirty-read
//            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//            statement.executeUpdate("update books set price = 100 where bookId = 1");
//            new OtherTransaction().start();
//            Thread.sleep(2000);
//            connection.rollback();

            // read unrepeatable
//            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            //repeatable read
//            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
//            ResultSet rs = statement.executeQuery("select * from books");
//            while (rs.next()) {
//                System.out.println("Name = " + rs.getString("name") + ", Price = " + rs.getDouble("price"));
//            }
//            new OtherTransaction().start();
//            Thread.sleep(2000);
//
//            ResultSet rs1 = statement.executeQuery("select * from books");
//            while (rs1.next()) {
//                System.out.println("Name = " + rs1.getString("name") + ", Price = " + rs1.getDouble("price"));
//            }


            // phantom read
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ResultSet rs = statement.executeQuery("select * from books where bookId > 4");
            while (rs.next()) {
                System.out.println("Name = " + rs.getString("name") + ", Price = " + rs.getDouble("price"));
            }
            new OtherTransaction().start();
            Thread.sleep(2000);

            ResultSet rs1 = statement.executeQuery("select * from books where bookId > 4");
            while (rs1.next()) {
                System.out.println("Name = " + rs1.getString("name") + ", Price = " + rs1.getDouble("price"));
            }
        }
    }

    static class OtherTransaction extends Thread {
        @Override
        public void run() {
            try (Connection connection = DriverManager.getConnection(url, user, pass);
                 Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);

                //dirty-read
//                connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//                ResultSet rs = statement.executeQuery("SELECT * from books");
//                while (rs.next()) {
//                    System.out.println("Name = " + rs.getString("name") + ", Price = " + rs.getDouble("price"));
//                }

                //read unrepeatable
//                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                //repeatble read
//                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
//                statement.executeUpdate("update books set price = price + 20 where name = 'Solomon Key'");
//                connection.commit();

                // phantom read
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                statement.executeUpdate("insert into books(name, price) values('new Bool', 10)");
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
