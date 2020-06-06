import javax.swing.*;
import java.sql.*;
import java.util.Properties;

/*  Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class DB {
    protected static Connection db = null;

    // Подключение к БД
    public static void connect() {
        try {
            db = getConnection();
            if(db != null) {
                System.out.println("Connected to db successful");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    // Получение Connection
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/auth?serverTimezone=UTC";
            String user = "root";
            String password = "12345678";
            String useUnicode = "true";
            String encoding = "utf-8";

            // Установка параметров подключения
            Properties p = new Properties();
            p.setProperty("user", user);
            p.setProperty("password", password);
            p.setProperty("useUnicode", useUnicode);
            p.setProperty("characterEncoding", encoding);

            return DriverManager.getConnection(url, p);
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Произошла ошикба при подключении к бд", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Запрос на select
    public static ResultSet select(String query) {
        ResultSet res = null;
        try {
            Statement st = db.createStatement();
            res = st.executeQuery(query);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return res;
    }

    // Запросы на insert
    public static ResultSet insert(String query) {
        try {
            Statement st = db.createStatement();
            st.executeUpdate(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet res = st.getGeneratedKeys();
            if(res.next()) {
                return res;
            } else return null;

        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    // Запросы на delete
    public static boolean delete(String query) {
        try {
            Statement st = db.createStatement();
            st.executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    // Запросы на update
    public static boolean update(String query) {
        try {
            Statement st = db.createStatement();
            st.executeUpdate(query);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    // Закрытие БД
    public static void close() {
        try {
            db.close();
            db = null;
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
