package Lesson_04.hw;

import Lesson_03.Main;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class AfrerLesson {

    static String url = "jdbc:mysql://localhost:3306/jdbclessons";
    static String user = "root";
    static String pass = "m?5C?Y9Wd#gaW{J@";

    static ResultSet setNewRow (ResultSet resultSet, String sName, String sPhone) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        PreparedStatement prepStat = null;
        ResultSet rs = resultSet;
        try (Connection conn = DriverManager.getConnection(url, user, pass)){
            prepStat = conn.prepareStatement("insert into phonebook(name, phone) values (?, ?)");
            prepStat.setString(1, sName);
            prepStat.setString(2, sPhone);
            prepStat.execute();

            rs = prepStat.executeQuery("select * from phonebook");
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet crs = factory.createCachedRowSet();
            crs.populate(rs);
            return crs;
        }
    }

    static void getAllData (ResultSet rs) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        ResultSet resultSet = rs;
        try (Connection conn = DriverManager.getConnection(url, user, pass);
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            resultSet = statement.executeQuery("select  * from phonebook");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String phone = resultSet.getString(3);
                System.out.printf("ID = %d, name = %s, phone = %s", id, name, phone);
                System.out.println("");

            }
        }
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = null;
            try {
                resultSet = statement.getResultSet();
                getAllData(resultSet);

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }
        }
    }


            /*ResultSet resultSet = null;
            try (Connection connection = DriverManager.getConnection(url, user, pass);
                Statement statement = connection.createStatement()){
                resultSet = statement.executeQuery("select * from phonebook");
                resultSet = setNewRow(resultSet, "Arina3", "89831111111");
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String phone = resultSet.getString(3);
                    System.out.printf("ID = %d, name = %s, phone = %s", id, name, phone);
                    System.out.println("");
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                } else {
                    System.out.println("Ошибка подключения к БД");
                }
            }
        }*/




       /* try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = null;

            try {
                resultSet = statement.executeQuery("SELECT * from phonebook");

                *//*while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String phone = resultSet.getString(3);
                    System.out.printf("ID = %d, name = %s, phone = %s", id,name,phone);
                    System.out.println("");
                }*//*

                System.out.println("----");
                ResultSetMetaData rsmd = resultSet.getMetaData();
                while(resultSet.next()) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        String field = rsmd.getColumnName(i);
                        String value = resultSet.getString(field);
                        System.out.print(field + ": " + value + " ");
                    }
                    System.out.println("");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                } else {
                    System.out.println("Ошибка подключения к БД");
                }
            }
        }*/

}
