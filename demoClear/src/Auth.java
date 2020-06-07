import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDateTime;

/*
    Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class Auth extends JFrame {
    private JButton loginButton;
    private JButton exitButton;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPanel auth;
    private JLabel time;
    protected Timer tm;
    protected int sec=60;
    protected int loginCount = 0;

    /*
        Данные для авторизации
        Логин: admin
        Пароль: admin
    */

    Auth() {
        setContentPane(auth);
        setTitle("Авторизация");
        setPreferredSize(new Dimension(600, 500));

        // Устанавливаем окно по центру экрана
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 300,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 250);

        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/car.png")));

        time.setVisible(false);

        // Подключение к БД
        DB.connect();

        // Блок проверки последнего входа
        try {
            // Получаем данные о последнем входе
            String query = "select * from logins where id=1";
            ResultSet res = DB.select(query);
            if(res.next()) {
                LocalDateTime now = LocalDateTime.now();

                // Секунды, оставшиеся до обновления попыток входа
                int seconds = res.getInt("sec");

                // Время получения следующих попыток входа
                LocalDateTime dbTime = LocalDateTime.parse(res.getString("system_time")).plusSeconds(seconds);

                Duration diff = Duration.between(now, dbTime);

                // Проверяем, что время получения попыток еще не наступило
                if(diff.getSeconds() > 0 && now.isBefore(dbTime)) {
                    // Вычисляем, сколько секунд осталось ждать
                    sec = (int) diff.getSeconds();
                    loginButton.setEnabled(false);
                    time.setVisible(true);
                    time.setText(sec + "");
                    timer();
                }
            } else {
                // Если записи в БД нет, то добавляем ее
                query = "insert into logins (id, system_time, sec) values (1, 23423432234, 0)";
                DB.insert(query);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String login = loginField.getText().trim(), pwd = passwordField.getText().trim();

                // Проверка логина и пароля на пустые значения
                if(login.equals("") || pwd.equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Поля ввода не должны быть пустыми",
                            "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        // Проверка связки логина и пароля в БД
                        String authQuery = "select * from users where login='" + login + "' and password='" + pwd +"';";
                        ResultSet user = DB.select(authQuery);

                        // Если существует такая запись, то запускаем следующее окно
                        if(user.next()) {
                          Licences main = new Licences();
                          main.pack();
                          main.setVisible(true);
                          dispose();
                        }
                        else {
                            // Записываем кол-во попыток входа
                            loginCount++;
                            JOptionPane.showMessageDialog(null,
                                    "Логин или пароль введены неверно",
                                    "Ошибка", JOptionPane.ERROR_MESSAGE);

                            // Блокируем возможность входа, запускаем таймер
                            if(loginCount == 3) {
                                loginButton.setEnabled(false);
                                time.setVisible(true);
                                timer();
                                JOptionPane.showMessageDialog(null,
                                        "3 попытки входа были исчерпаны, следующие будут доступны через 60 секунд",
                                        "Внимание", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Произошла ошибка при обработке данных",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }

    // Функция для запуска и остановки таймера
    public void timer() {
        tm = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Записываем в БД дынные о последней попытке входа
                String query = "update logins set system_time='" + LocalDateTime.now() + "', sec="+ sec +" where id=1";
                DB.update(query);

                // Останавливаем таймер, если время исчерпано
                if(sec == 0) {
                    tm.stop();
                    sec = 60;
                    time.setVisible(false);
                    time.setText(sec + "");
                    loginButton.setEnabled(true);
                    loginCount = 0;
                } else {
                    sec--;
                    time.setText(sec + "");
                }
            }
        });

        tm.start();
    }

    // Функция для подулючения стороннего шрифта
    public static void connectFont () {
        try {
            Font font = Font.createFont(
                    Font.TRUETYPE_FONT,
                    Auth.class.getResourceAsStream("fonts/Bellota-Regular.ttf")
            ).deriveFont(Font.PLAIN, 14);

            UIManager.put("Label.font", font);
            UIManager.put("TextField.font", font);
            UIManager.put("Button.font", font);
            UIManager.put("Label.font", font);
            UIManager.put("ComboBox.font", font);
            UIManager.put("Table.font", font);
            UIManager.put("TableHeader.font", font);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        connectFont();
        Auth app = new Auth();
        app.pack();
        app.setVisible(true);
    }
}
