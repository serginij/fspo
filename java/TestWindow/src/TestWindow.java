import javax.swing.*;
import java.awt.event.*;

public class TestWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField login;
    private JTextField password;
    private JLabel label2;
    private JLabel label1;

    public TestWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(login.getText().equals("user") && password.getText().equals("1234")){
                    System.out.println("ok");
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

    }

    public static void main(String[] args) {
        TestWindow dialog = new TestWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
