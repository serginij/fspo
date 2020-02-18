import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Auth extends JFrame {
    private JPanel main;
    private JButton ok;
    private JButton exit;
    private JTextField login;
    private JTextField password;

    Auth(){
        setContentPane(main);
        setTitle("Авторизация");
        setPreferredSize(new Dimension(400, 300));

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String log = login.getText();
                String pwd = password.getText();
                if(log.length() == 0 || pwd.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Поля для ввода должны быть заполнены", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else {
                    if(log.equals("user") && pwd.equals("password")) {
                        System.out.println("Ok");
                        Main m = new Main(log, pwd);
                        m.pack();
                        m.setVisible(true);

                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Неправильный логин или пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        Auth a = new Auth();
        a.pack();
        a.setVisible(true);

    }
}
