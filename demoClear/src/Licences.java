import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/*
    Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class Licences extends JFrame{
    private JComboBox comboBox;
    private JTextField searchField;
    private JButton searchButton;
    private JButton editButton;
    private JButton createButton;
    private JButton deleteButton;
    private JTable table;
    private JPanel main;
    protected DefaultComboBoxModel comboBoxModel;
    protected  DefaultTableModel tableModel;

    Licences() {
        setContentPane(main);
        setTitle("Водительские удостоверения");
        setPreferredSize(new Dimension(600, 500));

        // Устанавливаем положение окна по центру экрана
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 300,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 250);

        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/car.png")));

        // Присваиваем переменные к соответствущие моделям
        comboBoxModel = new DefaultComboBoxModel();
        tableModel = new DefaultTableModel();

        // Связываем модели
        comboBox.setModel(comboBoxModel);
        comboBoxModel.addElement("Водительские удостоверения");

        table.setModel(tableModel);

        // Добавляем столбцы в таблицу
        tableModel.addColumn("ID");
        tableModel.addColumn("ФИО");
        tableModel.addColumn("№ ВУ");
        tableModel.addColumn("До");

        getLicences("select * from driver_licence");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = searchField.getText().trim();

                // Обновляем таблицу, осуществляя поиск по ФИО и №ВУ
                getLicences("select * from driver_licence where name like ('%"+ text
                        +"%') or surname like ('%"+text
                        +"%') or father_name like ('%"+ text
                        +"%') or number like ('%"+ text +"%')");

                if(text.equals("")) JOptionPane.showMessageDialog(null,
                        "Необходимо ввести текст для поиска", "Внимание", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EditLicence add = new EditLicence(tableModel);
                add.setVisible(true);
                add.pack();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = table.getSelectedRow();

                // Проверяем, выбрал ли пользователь строку для редактирования
                if(index == -1) JOptionPane.showMessageDialog(null,
                        "Необходимо выбрать строку для редактирования", "Внимание", JOptionPane.INFORMATION_MESSAGE);
                else {
                    EditLicence edit = new EditLicence(tableModel, index);
                    edit.pack();
                    edit.setVisible(true);
                }

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int idx = table.getSelectedRow();

                // Проверяем, выбрал ли пользователь строку для удаления
                if(idx == -1) JOptionPane.showMessageDialog(null,
                        "Необходимо выбрать строку для удаления", "Внимание", JOptionPane.INFORMATION_MESSAGE);
                else {
                    int res = JOptionPane.showConfirmDialog(null,
                            "Вы действительно хотите удалить данные?", "Внимание",JOptionPane.OK_CANCEL_OPTION);

                    if(res == 0) {
                        String query = "delete from driver_licence where id = "+ tableModel.getValueAt(idx, 0);
                        boolean result = DB.delete(query);

                        if(result) {
                            JOptionPane.showMessageDialog(null,
                                    "Выбранные данные удалены", "Успешно", JOptionPane.INFORMATION_MESSAGE);
                            tableModel.removeRow(idx);
                        }
                    }
                }
            }
        });

    }

    protected void getLicences (String query) {
        try {
            ResultSet res =  DB.select(query);

            //Обнуляем таблицу
            tableModel = new DefaultTableModel();

            // Добавляем столбцы в таблицу
            tableModel.addColumn("ID");
            tableModel.addColumn("ФИО");
            tableModel.addColumn("№ ВУ");
            tableModel.addColumn("До");

            table.setModel(tableModel);

            // Достаем данные из БД
            while(res.next()) {
                Licence row = new Licence(res);

                // Записываем в модель таблицы
                tableModel.addRow(new String[]{String.valueOf(row.getId()), row.getFullName(), row.getNumber(), row.getExpiredDate().toString()});
            }
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Произошла ошибка при получении данных", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
