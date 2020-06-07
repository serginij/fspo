import javax.swing.*;
import java.sql.*;
import java.util.Properties;

public class DB {
    protected static Connection db = null;

    public static void connect() {
        try {
            db = getConnection();
            if(db != null) {
                System.out.println("Connected to DB successful");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static Connection getConnection() {
        String user = "root";
        String password = "12345678";
        String url = "jdbc:mysql://localhost:3306/auth?serverTimezone=UTC";
        String encoding = "utf-8";
        String useUnicode = "true";

        Properties p = new Properties();

        p.setProperty("user", user);
        p.setProperty("password", password);
        p.setProperty("useUnicode", useUnicode);
        p.setProperty("characterEncoding", encoding);

        try {
            return DriverManager.getConnection(url, p);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables);
            JOptionPane.showMessageDialog(null, "Произошла ошибка при подключении к БД", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static ResultSet select(String query) {
        try {
            Statement st = db.createStatement();
            return st.executeQuery(query);
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Произошла ошибка при подключении к БД", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static ResultSet insert(String query) {
        try {
            ResultSet res;
            Statement st = db.createStatement();
            st.executeUpdate(query, PreparedStatement.RETURN_GENERATED_KEYS);
            res = st.getGeneratedKeys();
            if(res.next()) {
                return res;
            } else return null;
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Произошла ошибка при подключении к БД", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static boolean update(String query) {
        try {
            Statement st = db.createStatement();
            st.executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Произошла ошибка при подключении к БД", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean delete(String query) {
        try {
            Statement st = db.createStatement();
            st.executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Произошла ошибка при подключении к БД", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
