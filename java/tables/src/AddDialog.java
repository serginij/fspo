import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddDialog extends JDialog {
    private JPanel main;
    private JButton ok;
    private JButton exit;
    private JTextField text1;
    private JTextField text2;
    private JTextField text3;

    AddDialog(ArrayList<UserData> dataList, DefaultTableModel tableModel){
        setContentPane(main);
        setPreferredSize(new Dimension(300, 300));
        setTitle("Добавить пользователя");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = text1.getText(), phone = text2.getText(), email = text3.getText();

                if(name.trim().length() == 0 || phone.trim().length() == 0 || email.trim().length() == 0 ) {
                    JOptionPane.showMessageDialog(null, "Не все поля заполнены", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    dataList.add(new UserData(name, phone, email));
                    tableModel.addRow(new String[]{name, phone, email});
                    dispose();
                }
            }
        });
    }
}
