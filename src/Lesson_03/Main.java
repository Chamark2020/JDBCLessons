package Lesson_03;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/booksshop";
        String user = "root";
        String pass = "m?5C?Y9Wd#gaW{J@";

        Class.forName("com.mysql.cj.jdbc.Driver");

        try  {
            Connection connection = DriverManager.getConnection(url, user, pass);
            PreparedStatement prep = null;
            try {
                prep = connection.prepareStatement("INSERT INTO books (name, price) VALUES (?, ?)");
                prep.setString(1, "Schindler's list");
                prep.setDouble(2, 32.5);
                prep.execute();

                ResultSet rs = null;
                try {
                    rs = prep.executeQuery("SELECT * FROM books");
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String name = rs.getString(2);
                        double price = rs.getDouble(3);
                        System.out.println("Id = " + id + ", Name = " + name + ", Price = " + price);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    } else {
                        System.err.println("Ошибка чтения с БД");
                    }
                }
            } catch (SQLException | RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                prep.close();
            }

            CallableStatement callStat = null;

            try {
                callStat = connection.prepareCall("{CALL booksCount(?)}");
                callStat.registerOutParameter(1, Types.INTEGER);
                callStat.execute();
                System.out.println("Количество записей в таблице: " + callStat.getInt(1));
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                callStat.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
