import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {
    private JPanel mainPanel;
    private JButton back;
    private JTextField login;
    private JTextField password;
    private JButton ok;

    public Window() {
        setContentPane(mainPanel);
//        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(login.getText().length() == 0 || password.getText().length() == 0) {
                    System.out.println("no data in fields");
                } else {
                    if (login.getText().equals("user") && password.getText().equals("password")) {
                        System.out.println("new window");
                        MainForm m = new MainForm();
                        m.pack();
                        m.setVisible(true);

                        dispose();
                    } else {
                        System.out.println("Error");
                    }
                }
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
        Window dialog = new Window();
        dialog.pack();
        dialog.setVisible(true);
    }
}
