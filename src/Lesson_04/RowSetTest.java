package Lesson_04;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

public class RowSetTest {

    static String url = "jdbc:mysql://localhost:3306/jdbclessons";
    static String user = "root";
    static String pass = "m?5C?Y9Wd#gaW{J@";

    public static void main(String[] args) throws SQLException, ClassNotFoundException, MalformedURLException {
        ResultSet resultSet = getResSet();

        /*while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }*/

        CachedRowSet rowSet = (CachedRowSet) resultSet;
        rowSet.setCommand("select * from books where price > ?");
        rowSet.setDouble(1, 20);

        rowSet.setUrl(url);
        rowSet.setUsername(user);
        rowSet.setPassword(pass);
        rowSet.execute();

        rowSet.absolute(2);
        rowSet.deleteRow();
        rowSet.beforeFirst();
        Connection connection = DriverManager.getConnection(url, user, pass);
        connection.setAutoCommit(false);
        rowSet.acceptChanges(connection);

        while (rowSet.next()) {
            String name = rowSet.getString("name");
            double price = rowSet.getDouble(3);
            System.out.println(name + ": " + price);
        }
    }

    static ResultSet getResSet() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from books");
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet crs = factory.createCachedRowSet();
            crs.populate(rs);
            return crs;
        }
    }
}
