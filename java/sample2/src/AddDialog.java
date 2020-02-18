import javax.swing.*;
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
    private JTextField text4;

    AddDialog(ArrayList<UserData> datalist){
        setContentPane(main);
        setTitle("Добавление");
        setPreferredSize(new Dimension(400, 400));

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(text1.getText().trim().equals("") || text2.getText().trim().equals("") || text3.getText().trim().equals("") || text4.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Не все поля заполнены", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
//                    UserData item = new UserData();
//                    item.addName(text1.getText());
//                    item.addSurname(text2.getText());
//                    item.addPhone(text3.getText());
//                    item.addEmail(text4.getText());

                    datalist.add(new UserData(text1.getText(), text2.getText(), text3.getText(), text4.getText()));

                    dispose();
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
}
