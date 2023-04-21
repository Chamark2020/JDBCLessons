package Lesson_04;

import java.sql.*;

public class getMetaDataTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbclessons";
        String user = "root";
        String pass = "m?5C?Y9Wd#gaW{J@";

        Class.forName("com.mysql.cj.jdbc.Driver");


        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))  {
            ResultSet rs = null;
            try {
                rs = statement.executeQuery("SELECT * from books");
                /*while (rs.next()) {
                    int id = rs.getInt(1);
                    double price = rs.getDouble(3);
                    if (id == 5) {
                        rs.updateString(2, "Green Mile (discount)");
                        rs.updateDouble(3, price - 20.0);
                        rs.updateRow();
                    }
                }*/

                if (rs.absolute(2)) {
                    System.out.println(rs.getString("name"));
                }
                if (rs.previous()) {
                    System.out.println(rs.getString("name"));
                }
                if (rs.last()) {
                    System.out.println(rs.getString("name"));
                }
                if (rs.relative(-3)) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    while(rs.next()) {
                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                            String field = rsmd.getColumnName(i);
                            String value = rs.getString(field);
                            System.out.print(field + " : " + value + ", ");
                        }
                        System.out.println("");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (rs != null) {
                    rs.close();
                } else {
                    System.out.println("Ошибка подключения к БД");
                }
            }
        }
    }
}
