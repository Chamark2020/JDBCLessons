package Lesson_02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String pass = "m?5C?Y9Wd#gaW{J@";

        Class.forName("com.mysql.cj.jdbc.Driver");

        try  {
            Connection connection = DriverManager.getConnection(url, user, pass);
            BufferedReader sqlFile = new BufferedReader(new FileReader("C:\\Users\\MagicBook\\IdeaProjects\\untitled1\\src\\Lesson_02\\Books.sql"));
            Scanner scan = new Scanner(sqlFile);
            Statement statement = connection.createStatement();
            String line = "";
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }
                statement.executeUpdate(line);
            }

            ResultSet rs = null;
            try {
                statement.executeUpdate("use jdbclessons");
                rs = statement.executeQuery("select * from books");
                while (rs.next()) {
                    int id = rs.getInt("bookId");
                    String name = rs.getString("Name");
                    double price = rs.getDouble(3);
                    System.out.println("Id = " + id + ", Name = " + name + ", Price = " + price);
                }
            } catch (SQLException ex) {
                System.err.println("SQLException message: " + ex.getMessage());
                System.err.println("SQLException SQL state: " + ex.getSQLState());
                System.err.println("SQLException error code: " + ex.getErrorCode());
            } finally {
                if (rs != null) {
                    rs.close();
                } else {
                    System.err.println("Ошибка чтения данных с БД");
                }
            }

            statement.close();
            scan.close();
            sqlFile.close();
            connection.close();
        } catch (SQLException ex) {
        }
    }
}
