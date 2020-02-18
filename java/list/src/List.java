import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class List extends JFrame{
    private JPanel main;
    private JButton add;
    private JButton delete;
    private JButton edit;
    private JList list;
    private JButton clear;
    ArrayList<UserData> datalist;

    List() {
        setContentPane(main);
        setPreferredSize(new Dimension(600, 400));
        setTitle("Список данных");
        datalist = new ArrayList<>();
        DefaultListModel<String> m = new DefaultListModel<>();
//        m.addElement("hello");
        list.setModel(m);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DataDialog d = new DataDialog(datalist, m);
                d.pack();
                d.setVisible(true);
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = list.getSelectedIndex();
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать элемент", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    m.remove(index);
                    datalist.remove(index);
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = list.getSelectedIndex();
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать элемент", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    DataDialog d = new DataDialog(datalist, m, index);
                    d.pack();
                    d.setVisible(true);
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int input = JOptionPane.showConfirmDialog(null, "При подтверждении все данные будут удалены", "Внимание", JOptionPane.OK_CANCEL_OPTION);
                if(input == 0) {
                    datalist.clear();
                    m.clear();
                }
            }
        });

    }

    public static void main(String[] args) {
        List l = new List();
        l.pack();
        l.setVisible(true);
    }
}
