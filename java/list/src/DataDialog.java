import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class DataDialog extends  JDialog{
    private JPanel main;
    private JButton ok;
    private JButton exit;
    private JTextField data1;
    private JTextField data2;
    private JTextField data3;
    private JTextField data4;

    DataDialog(ArrayList<UserData> datalist, DefaultListModel<String> m){
        setContentPane(main);
        setPreferredSize(new Dimension(400, 300));

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(data1.getText().length() == 0 || data2.getText().length() == 0 || data3.getText().length() == 0 || data4.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Не все поля заполнены", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    if(checkUnique(datalist, data1.getText())) {
                        datalist.add(new UserData(data1.getText(), data2.getText(), data3.getText(), data4.getText()));
                        sort(datalist);
                        m.clear();
                        for(int i = 0; i < datalist.size(); i ++) {
                            m.addElement(datalist.get(i).getName());
                        }
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Имя должно быть уникально", "Ошибка", JOptionPane.WARNING_MESSAGE);
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
    }

    DataDialog(ArrayList<UserData> datalist, DefaultListModel<String> m, int index) {
        setContentPane(main);
        setPreferredSize(new Dimension(400, 300));
        data1.setText(datalist.get(index).getName());
        data2.setText(datalist.get(index).getSurname());
        data3.setText(datalist.get(index).getPhone());
        data4.setText(datalist.get(index).getEmail());

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(data1.getText().length() == 0 || data2.getText().length() == 0 || data3.getText().length() == 0 || data4.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Не все поля заполнены", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    if(checkUnique(datalist, data1.getText())) {
                        datalist.set(index, new UserData(data1.getText(), data2.getText(), data3.getText(), data4.getText()));
                        sort(datalist);
                        m.clear();
                        for(int i = 0; i < datalist.size(); i ++) {
                            m.addElement(datalist.get(i).getName());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Имя должно быть уникально", "Ошибка", JOptionPane.WARNING_MESSAGE);
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
    }

    boolean checkUnique(ArrayList<UserData> datalist, String name) {
        if(datalist.size() > 0 ) {
            int flag = 0;
            for(int i = 0; i < datalist.size(); i ++) {
                if(datalist.get(i).getName().equals(name)) {
                    flag = 1;
                }
            }
            return flag == 0;
        } else {
            return true;
        }
    }

    void sort(ArrayList<UserData> datalist) {
        datalist.sort(new Comparator<UserData>() {
            @Override
            public int compare(UserData userData, UserData t1) {
                return userData.getName().compareTo(t1.getName());
            }
        });
    }
}
