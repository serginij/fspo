import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Main extends JFrame{
    private JPanel main;
    private JComboBox comboBox;
    private JTable table;
    private JButton add;
    private ArrayList<UserData> dataList;
    private DefaultTableModel tableModel;


    Main() {
        setContentPane(main);
        setPreferredSize(new Dimension(400, 300));

        dataList = new ArrayList<>();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        tableModel = new DefaultTableModel();

        model.addElement("All");
        model.addElement("Name");
        model.addElement("Phone");
        model.addElement("Email");

        tableModel.addColumn("Name");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");
        table.setModel(tableModel);

        TableColumn name_col = table.getColumn("Name");
        TableColumn phone_col =table.getColumn("Phone");
        TableColumn email_col =table.getColumn("Email");

        comboBox.setModel(model);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(comboBox.getSelectedIndex() + "");
                if(comboBox.getSelectedIndex() != -1) {
//                    tableModel = new DefaultTableModel();

                    table.removeColumn(name_col);
                    table.removeColumn(phone_col);
                    table.removeColumn(email_col);
                    switch (comboBox.getSelectedIndex()) {
                        case 0:
                            table.addColumn(name_col);
                            table.addColumn(phone_col);
                            table.addColumn(email_col);
//                            for(int i = 0; i < dataList.size(); i++) {
//                                tableModel.addRow(new String[] {dataList.get(i).getName(), dataList.get(i).getPhone(), dataList.get(i).getEmail()});
//                            }
                            break;
                        case 1:
                            table.addColumn(name_col);
//                            for(int i = 0; i < dataList.size(); i++) {
//                                tableModel.addRow(new String[] {dataList.get(i).getName()});
//                            }
                            break;
                        case 2:
                            table.addColumn(phone_col);
//                            tableModel.addColumn("Phone");
//                            for(int i = 0; i < dataList.size(); i++) {
//                                tableModel.addRow(new String[] {dataList.get(i).getPhone()});
//                            }
                            break;
                        case 3:
                            table.addColumn(email_col);
//                            tableModel.addColumn("Email");
//                            for(int i = 0; i < dataList.size(); i++) {
//                                tableModel.addRow(new String[] {dataList.get(i).getEmail()});
//                            }
                            break;
                    }
                }
            }
        });




//        tableModel.setColumnIdentifiers(new String[] {"Name", "Phone", "Email"});

        table.setGridColor(Color.blue);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddDialog d = new AddDialog(dataList, tableModel);
                d.pack();
                d.setVisible(true);
            }
        });

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                int i = table.getEditingRow();
                if(i != -1) {
                    String name = tableModel.getValueAt(i, 0).toString();
                    String phone = tableModel.getValueAt(i, 1).toString();
                    String email = tableModel.getValueAt(i, 2).toString();

                    dataList.set(table.getSelectedRow(), new UserData(name, phone, email));
                    JOptionPane.showMessageDialog(null, "Данные изменены успешно");
                }
            }
        });

    }

    public static void main(String[] args) {
        Main m = new Main();
        m.pack();
        m.setVisible(true);
    }
}
