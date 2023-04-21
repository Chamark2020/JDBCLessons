package Lesson_03;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;


public class BlobExample {
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/jdbclessons";
        String user = "root";
        String pass = "m?5C?Y9Wd#gaW{J@";

        Class.forName("com.mysql.cj.jdbc.Driver");


        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Images (name varchar(15), d date, image BLOB)");

            PreparedStatement preparedStatement = null;
            try {
                BufferedImage image = ImageIO.read(new File("C:\\Users\\MagicBook\\IdeaProjects\\untitled1\\src\\smile.png"));
                Blob smile = connection.createBlob();


                try (OutputStream outputStream =  smile.setBinaryStream(1)){

                    ImageIO.write(image, "png", outputStream);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                preparedStatement = connection.prepareStatement("USE jdbclessons");
                preparedStatement = connection.prepareStatement("INSERT INTO Images (name, d, image) values (?, {d ?}, ?)");
                preparedStatement.setString(1, "Smile");
                preparedStatement.setDate(2, Date.valueOf("2022-08-07"));
                preparedStatement.setBlob(3, smile);
                preparedStatement.execute();

                ResultSet resultSet = null;
                try {
                    resultSet = preparedStatement.executeQuery("SELECT * FROM Images");
                    while (resultSet.next()) {
                        Blob newSmile = resultSet.getBlob("image");
                        BufferedImage image1 = ImageIO.read(newSmile.getBinaryStream());
                        File outputFile = new File("saved.png");
                        ImageIO.write(image1, "png", outputFile);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (resultSet != null) {
                        resultSet.close();
                    } else {
                        System.err.println("Ошибка подключения к БД");
                    }
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
