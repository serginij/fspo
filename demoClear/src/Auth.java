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

        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 300,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 250);

        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/car.png")));

        time.setVisible(false);

        // Подключение к БД
        DB.connect();

        try {
            String query = "select * from logins where id=1";
            ResultSet res = DB.select(query);
            if(res.next()) {
                LocalDateTime now = LocalDateTime.now();
                int seconds = res.getInt("sec");
                LocalDateTime dbTime = LocalDateTime.parse(res.getString("system_time")).plusSeconds(seconds);

                Duration diff = Duration.between(now, dbTime);

                if(diff.getSeconds() > 0 && now.isBefore(dbTime)) {
                    sec = (int) diff.getSeconds();
                    loginButton.setEnabled(false);
                    time.setVisible(true);
                    time.setText(sec + "");
                    timer();
                }
            } else {
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

                if(login.equals("") || pwd.equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Поля ввода не должны быть пустыми",
                            "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        String authQuery = "select * from users where login='" + login + "' and password='" + pwd +"';";
                        ResultSet user = DB.select(authQuery);
                        if(user.next()) {
                          Licences main = new Licences();
                          main.pack();
                          main.setVisible(true);
                          dispose();
                        }
                        else {
                            loginCount++;
                            JOptionPane.showMessageDialog(null,
                                    "Логин или пароль введены неверно",
                                    "Ошибка", JOptionPane.ERROR_MESSAGE);

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

    public void timer() {
        tm = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String query = "update logins set system_time='" + LocalDateTime.now() + "', sec="+ sec +" where id=1";
                DB.update(query);
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
