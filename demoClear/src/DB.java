import javax.swing.*;
import java.sql.*;
import java.util.Properties;

/*
    Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class DB {
    protected static Connection db = null;
    // Функция подключения к бд, записывает в переменную db подключение к БД
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
    // Функция получения подключения
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
    // Функция для запросов select
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
    // Функция для запросов insert. Возвращает сгенерированные ключи (id)
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
    // Функция для запросов update, возвращает флаг успешности операции
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
    // Функция для запросов delete, возвращает флаг успешности операции
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
