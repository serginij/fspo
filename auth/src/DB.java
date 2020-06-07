/* Информация о разработчике:
    Котлицкий Сергей
    Контакты:
        email: kotlizkiy@gmail.com
        github: https://github.com/serginij
*/
import javax.swing.*;
import java.sql.*;

public class DB {
    protected static Connection db = null;

    public static void connect() {
        try {
            db = getConnection();
            if(db == null) {
                JOptionPane.showMessageDialog(null, "Ошибка при подключении к БД", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Connection to Store DB successful!");
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/auth";
            String username = "root";
            String password = "12345678";

            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("OOPs, something went wrong");
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet select (String query) {
        System.out.println(query);
        ResultSet res = null;
        try {
            Statement st = db.createStatement();
            res = st.executeQuery(query);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return res;
    }

    public static boolean insert (String query) {
        System.out.println(query);

        try {
            Statement st = db.createStatement();
            st.executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }

    }

    public static void close() throws SQLException{
        db.close();
        db = null;
    }
}
