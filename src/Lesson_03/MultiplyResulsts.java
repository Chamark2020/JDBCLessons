package Lesson_03;

import java.sql.*;

public class MultiplyResulsts {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbclessons";
        String user = "root";
        String pass = "m?5C?Y9Wd#gaW{J@";

        Class.forName("com.mysql.cj.jdbc.Driver");


        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
             CallableStatement callableStatement = null;
            try {
                callableStatement = connection.prepareCall("{CALL tablesCount}");
                boolean hasResults = callableStatement.execute();
                ResultSet resultSet = null;
                try {
                    while (hasResults) {
                        resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            System.out.println("Количество записей в таблице: " + resultSet.getInt(1));
                        }
                        hasResults = callableStatement.getMoreResults();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (resultSet != null) {
                        resultSet.close();
                    } else {
                        System.out.println("Ошибка подключения к БД");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (callableStatement != null) {
                    callableStatement.close();
                } else {
                    System.out.println("Ошибка подключения к БД");
                }
            }
        }

    }
}
