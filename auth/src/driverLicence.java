/* Информация о разработчике:
    Котлицкий Сергей
    Контакты:
        email: kotlizkiy@gmail.com
        github: https://github.com/serginij
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class driverLicence extends JFrame{
    private JPanel main;
    private JComboBox comboBox;
    private JButton deleteBtn;
    private JButton createBtn;
    private JList list;
    private JButton editBtn;
    private JTextField searchField;
    private JButton search;
    protected DefaultComboBoxModel comboModel;
    protected ArrayList<Licence> licences;
    protected DefaultListModel listModel;
    protected int selectedWindow = 0;

    driverLicence() {
        setContentPane(main);
        setTitle("Водительские удостоверения");
        setPreferredSize(new Dimension(1000,600));

        comboModel = new DefaultComboBoxModel();
        comboBox.setModel(comboModel);

        comboModel.addElement("Водительские удостоверения");
        comboModel.addElement("Транспортные средства");

        listModel = new DefaultListModel();
        list.setModel(listModel);

        licences  = new ArrayList<>();

        String query = "select * from driver_licence";

        getLicencesData(query);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = comboBox.getSelectedIndex();
                if(index > -1 && index != selectedWindow) {
                    switch (index) {
                        case 0:
                            driverLicence window = new driverLicence();
                            window.pack();
                            window.setVisible(true);
                            dispose();
                            break;
                        case 1:
                            transport window1 = new transport();
                            window1.pack();
                            window1.setVisible(true);
                            dispose();
                            break;
                    }
                }
            }
        });

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = list.getSelectedIndex();

                if(index > -1) {
                    System.out.println("Select " + index + "\n" + licences.get(index).getFullName());
                    driverLicenceEdit edit = new driverLicenceEdit(listModel, licences, index);
                    edit.pack();
                    edit.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать строку", "Внимание", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                driverLicenceEdit create = new driverLicenceEdit(listModel, licences);
                create.pack();
                create.setVisible(true);
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = list.getSelectedIndex();
                if(index == -1) {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать элемент списка", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    String query = "delete from driver_licence where id=" + licences.get(index).getId();
                    boolean res = DB.insert(query);
                    if(res) {
                        JOptionPane.showMessageDialog(null, "Водительское удостоверение удалено", "Успешно", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Произошла ошибка при удалении", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = searchField.getText();
                String query = "select * from driver_licence where name like ('%"+text+"%') or surname like('%"+text+"%') or father_name like('%"+text+"%') or number like ('%"+text+"%')";
                if(text.trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Необходимо ввести текст для поиска", "Внимание", JOptionPane.WARNING_MESSAGE);
                    query = "select * from driver_licence";
                }
                getLicencesData(query);
            }
        });
    }

    protected void getLicencesData(String query) {
        ResultSet res = DB.select(query);

        try {
            licences.clear();
            listModel.clear();
            while(res.next()) {
                Licence data = new Licence(
                        res.getString("name"), res.getString("surname"),
                        res.getString("father_name"), res.getString("number"),
                        LocalDate.parse(res.getString("birthday")),
                        LocalDate.parse(res.getString("expired_date")),
                        res.getInt("id"));

                listModel.addElement(data.getFullName() + " №" + data.getNumber() + " до " + data.getExpiredDate());
                licences.add(data);
            }

        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Произошла ошибка при обращении к БД", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
