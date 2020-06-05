import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

/*  Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class Licences extends JFrame{
    private JComboBox comboBox;
    private JTextField searchField;
    private JButton searchBtn;
    private JButton editBtn;
    private JButton createBtn;
    private JButton deleteBtn;
    private JTable table;
    private JPanel main;
    protected DefaultTableModel tableModel;
    protected DefaultComboBoxModel comboModel;
    protected ArrayList<Licence> licences;

    Licences() {
        setContentPane(main);
        setTitle("Водительские удостоверения");
        setPreferredSize(new Dimension(500, 400));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - WIDTH /2,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - HEIGHT / 2);
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/car.png"));

        // Инициализируем модели и массив данных
        comboModel = new DefaultComboBoxModel();
        tableModel = new DefaultTableModel();
        licences = new ArrayList<>();

        // Связываем comboBox с моделью
        comboBox.setModel(comboModel);
        comboModel.addElement("Водительские удостоверения");

        // Связываем таблицу с моделью
        table.setModel(tableModel);
        table.setDefaultEditor(Object.class, null);

        // Добавляем в талицу столбцы
        tableModel.addColumn("ФИО");
        tableModel.addColumn("№ВУ");
        tableModel.addColumn("Годен до");

        // Получаем данные о ВУ
        getDriverLicences("select * from driver_licence");


        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Открываем окно добавления ВУ
                EditLicence edit = new EditLicence(licences, tableModel);
                edit.pack();
                edit.setVisible(true);
            }
        });

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Проверяем, что пользователь выделил строку для редактирования
                int idx = table.getSelectedRow();
                if(idx == -1) JOptionPane.showMessageDialog(null , "Необходимо выбрать строку для редактирвоания", "Внимание", JOptionPane.INFORMATION_MESSAGE);
                    else {
                        // Открываем окно редаткирования ВУ
                        EditLicence edit = new EditLicence(licences, tableModel, idx);
                        edit.pack();
                        edit.setVisible(true);
                }
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = searchField.getText();
                System.out.println(text);
                // Вызываем функцию обновления данных с запросом на поиск по имени, фамилии, отчеству, номеру ВУ
                getDriverLicences("select * from driver_licence where name like'%"+text+
                        "%' or surname like '%"+text+
                        "%' or father_name like '%"+text+
                        "%' or number like '%"+text+"%'");

                // Выдем предупреждение, если поле пустое
                if(text.trim().equals(""))JOptionPane.showMessageDialog(null , "Необходимо ввести строку для поиска", "Внимание", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Проверяем, что пользователь выделеил строку для удалнеия
                int idx = table.getSelectedRow();
                if(idx == -1) JOptionPane.showMessageDialog(null , "Необходимо выбрать строку для удаления", "Внимание", JOptionPane.INFORMATION_MESSAGE);
                else {
                    // Запрашиваем подтверждение удаления
                    int result = JOptionPane.showConfirmDialog(null, "Вы точно хотите удалить элемент","Внимание", JOptionPane.OK_CANCEL_OPTION);
                    if(result == 0) {
                        try {
                            // Создаем запрос на удаление
                            String query = "delete from driver_licence where id = " + licences.get(idx).getId();
                            boolean res = DB.delete(query);
                            // Если успешно, то удаляем данные из модели и массива с данными
                            if(res) {
                                licences.remove(idx);
                                tableModel.removeRow(idx);
                                JOptionPane.showMessageDialog(null , "Водитлеьское удостоверение удалено", "Успешно", JOptionPane.INFORMATION_MESSAGE);
                            }
                            System.out.println("delete");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null , "Произошла ошибка при обращении к бд", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            System.out.println("delete");
                        }
                    }
                }
            }
        });
    }

    protected void getDriverLicences(String query) {
        ResultSet res = null;
        try {
            // Выполняем запрос к бд
            res = DB.select(query);

            // Обнуляем данные в массиве и модели таблицы
            licences.clear();
            tableModel = new DefaultTableModel();
            table.setModel(tableModel);

            // Добавляем в модель столбцы
            tableModel.addColumn("ФИО");
            tableModel.addColumn("№ВУ");
            tableModel.addColumn("Годен до");

            while(res.next()) {

                // Записываем в класс данные из БД
                Licence el = new Licence(res.getInt("id"),
                        res.getString("name"),
                        res.getString("surname"),
                        res.getString("father_name"),
                        res.getString("number"),
                        res.getString("birthday"),
                        res.getString("expired_date"));

                // Добавляем данные в модель и в массив с данными
                licences.add(el);
                tableModel.addRow(new String[]{el.getFullName(), el.getNumber(), el.getExpiredDate().toString()});
            }
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null , "Произошла ошибка при обращении к бд", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

}
