import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Auth extends JFrame{
    private JPanel main;
    private JButton ok;
    private JButton exit;
    private JTextField login;
    private JPasswordField password;

    Auth() {
        setContentPane(main);
        setTitle("Авторизация");
        setPreferredSize(new Dimension(400, 300));

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String log = login.getText(), pwd = password.getText();

                if(log.trim().length() == 0 || pwd.trim().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Поля должны быть заполнены", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else if(log.equals("user") && pwd.equals("user")) {
                    System.out.println("user");
                    Main m = new Main(false);
                    m.setVisible(true);
                    m.pack();
                    dispose();
                } else if (log.equals("admin") && pwd.equals("admin")) {
                    System.out.println("admin");
                    Main m = new Main(true);
                    m.setVisible(true);
                    m.pack();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Логин или пароль введены неверно", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
            public void windowClosed(WindowEvent e) {
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
