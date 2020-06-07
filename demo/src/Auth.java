import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDateTime;

/*  Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class Auth extends JFrame{
    private JButton ok;
    private JButton cancel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPanel auth;
    private JLabel time;
    protected Timer tm = null;
    protected int sec = 30;
    protected int loginCount = 0;

    /* Для авторизации необходимо ввести данные:
        Логин - admin
        Пароль - admin
     */

    Auth() {
        setContentPane(auth);
        setTitle("Авторизация");
        setPreferredSize(new Dimension(500, 400));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - WIDTH /2,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - HEIGHT / 2);

        setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("images/car.png")));

        time.setVisible(false);

        // Подключаемся к бд
        DB.connect();

        try {
            // Получаем время последнего неуспешного входа
            String query = "select * from logins where id=1";

            ResultSet res = DB.select(query);

            // Если в бд есть запись, то проверям, прошло ли 30 секунд с того момента
            if(res.next()) {
                int seconds = res.getInt("sec");
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime dbTime = LocalDateTime.parse(res.getString("system_time")).plusSeconds(seconds);

                Duration diff = Duration.between(now, dbTime);

                if(diff.getSeconds() > 0 && now.isBefore(dbTime)) {
                    sec = (int) diff.getSeconds();
                    time.setVisible(true);
                    time.setText(sec + "");
                    timer();
                    ok.setEnabled(false);
                }
            } else {
                // Если в бд нет записи, то инициализируем в ней данные
                query = "insert into logins (system_time, sec, id) values ('" + LocalDateTime.now() + "', 0, 1)";
                DB.insert(query);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Получаем текст из полей ввода
                String login = loginField.getText(), password = passwordField.getText();

                System.out.println(login + " " + password);

                // Если хоть одно из полей пустое, то показываем сообщение пользователю
                if(login.trim().equals("") || password.trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Поля для ввода не должны быть пустыми", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Если логин и пароль правильные, то переходим на следующий экран
                    if(login.equals("admin") && password.equals("admin")) {
                        System.out.println("SIGNIN");
                        Licences main = new Licences();
                        main.pack();
                        main.setVisible(true);
                        dispose();
                    }
                    else {
                        // Если логин или пароль неправильные, то записываем попытку входа, показываем сообщение
                        loginCount++;
                        JOptionPane.showMessageDialog(null, "Неверный логин или пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);

                        // Если число попыток входа равно 3, то показываем сообщение, блокируем вход и запускаем таймер
                        if(loginCount == 3) {
                            ok.setEnabled(false);
                            time.setVisible(true);
                            timer();
                            JOptionPane.showMessageDialog(null, "Попытки входа исчерапы. Следующие попытки будут доступны через 30 секунд", "Внимание", JOptionPane.INFORMATION_MESSAGE);
                        }

                    }
                }
            }
        });

        // При нажатии на кнопку "Отмена" закрываем приложение
        cancel.addActionListener(new ActionListener() {
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
                // Записываем в бд попытку входа со временем и оставшимися секундами
                String query = "update logins set sec=" + sec + ", system_time='" + LocalDateTime.now() +"' where id=1";
                DB.update(query);

                // Останавливаем таймер, если прошло время
                if(sec == 0) {
                    tm.stop();
                    time.setVisible(false);
                    time.setText("60");
                    sec = 30;
                    ok.setEnabled(true);
                    loginCount = 0;
                } else {
                    sec--;
                    time.setText(sec + "");
                }
            }
        });
        tm.start();
    }

    public static void loadFont() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,
                    Auth.class.getResourceAsStream("fonts/Bellota-Regular.ttf"))
                    .deriveFont(Font.PLAIN, 14);

            UIManager.put("TextField.font", font);
            UIManager.put("Button.font", font);
            UIManager.put("Label.font", font);
            UIManager.put("ComboBox.font", font);
            UIManager.put("Table.font", font);
            UIManager.put("TableHeader.font", font);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadFont();

        Auth app = new Auth();
        app.pack();
        app.setVisible(true);
    }
}
