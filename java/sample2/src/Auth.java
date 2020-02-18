import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Auth extends JFrame{
    private JPanel main;
    private JTextField login;
    private JPasswordField password;
    private JButton ok;
    private JButton exit;

    Auth(){
        setContentPane(main);
        setTitle("Авторизация");
        setPreferredSize(new Dimension(400, 300));

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(login.getText().length() == 0 || password.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Не все поля заполнены", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else if(login.getText().equals("user") && password.getText().equals("password")) {
                    System.out.println("ok");
                    Main m = new Main();
                    m.pack();
                    m.setVisible(true);
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


    }

    public static void main(String[] args) {
        Auth a = new Auth();
        a.pack();
        a.setVisible(true);
    }
}
