import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame{
    private JPanel main;

    Main(String login, String password) {
        setContentPane(main);
        setTitle("Главное меню");
        setPreferredSize(new Dimension(400, 300));

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null ,"Логин:\n" + login + "\nПароль:\n" + password, "Текущий пользователь", JOptionPane.INFORMATION_MESSAGE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}
