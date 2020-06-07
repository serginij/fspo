import com.mysql.fabric.ShardTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class app extends JFrame{
    private JPanel main;
    private JButton ok;
    private JButton registration;
    private JTextField login;
    private JPasswordField password;
    private JLabel time;
    protected Timer tm;
    private int sec = 30;
    private int loginCouts = 0;

    app() {
        setContentPane(main);
        setPreferredSize(new Dimension(400, 300));
        setTitle("Авторизация");

        System.out.println(System.nanoTime());

        DB.connect();

        driverLicence window = new driverLicence();
        window.setVisible(true);
        window.pack();
        dispose();

        try {
            String query = "select * from logins where id=1";
            ResultSet loginsRes = DB.select(query);
            if(loginsRes.next()) {
                int seconds = loginsRes.getInt("sec");
                String systemTime = loginsRes.getString("system_time");
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime locked = LocalDateTime.parse(systemTime).plusSeconds(seconds);
                Duration diff = Duration.between(now, locked);

                System.out.println(diff.getSeconds());
                System.out.println(now.isBefore(locked));

                if(diff.getSeconds() > 0 && now.isBefore(locked)) {
                    sec = (int) diff.getSeconds();
                    time.setVisible(true);
                    time.setText("60");
                    ok.setEnabled(false);
                    startTimer();
                }
            } else {
                DB.insert("insert into logins values ('"+ LocalDateTime.now() + "', 0, 1)");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }



        ok.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String loginText = login.getText();
                String pwdText = password.getText();

                if(loginText.trim().equals("") || pwdText.trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Поля для ввода не должны быть пустыми", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    System.out.println(loginCouts);
                    loginCouts++;
                    try {
                        String query = "select * from users where login='" + loginText + "' and password='" + pwdText + "'";
                        ResultSet res = DB.select(query);
                        if(res.next()) {
                            System.out.println(res.getInt("id") + " " + res.getString("name") + " " + res.getString("surname"));
                            driverLicence window = new driverLicence();
                            window.setVisible(true);
                            window.pack();
                            dispose();
                        } else {
                            System.out.println("user not found");
                            JOptionPane.showMessageDialog(null, "Неверный логин или пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);

                        }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    if(loginCouts == 3) {
                        startTimer();
                        time.setVisible(true);
                        time.setText("60");
                        ok.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "Попытки входа исчерпаны, до их возобновления осталось 1 минута", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dispose();
            }
        });
    }

    public void startTimer() {
        tm = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String query = "update logins set system_time='" + LocalDateTime.now() + "', sec=" + sec + " where id=1;";
                DB.insert(query);
                if(sec == 0) {
                    tm.stop();
                    time.setVisible(false);
                    time.setText("60");
                    ok.setEnabled(true);
                    loginCouts = 0;
                    sec = 10;
                } else {
                    sec--;
                    time.setText(sec+"");
                }
            }
        });
        tm.start();
    }

    public static void main(String[] args) {
        app main = new app();
        main.setVisible(true);
        main.pack();
    }
}
