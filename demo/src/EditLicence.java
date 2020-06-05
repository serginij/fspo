import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

/*  Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class EditLicence extends JFrame{
    private JButton save;
    private JButton cancel;
    private JTextField surnameField;
    private JTextField nameField;
    private JTextField fatherNameField;
    private JTextField numberField;
    private JTextField birthdayField;
    private JTextField expiredDateField;
    private JPanel window;

    EditLicence(ArrayList<Licence> licences, DefaultTableModel tableModel) {
        setContentPane(window);
        setPreferredSize(new Dimension(500, 400));
        setTitle("Создание ВУ");
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - WIDTH /2,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - HEIGHT / 2);
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/car.png"));

        Licence licence = new Licence();

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameField.getText(), surname = surnameField.getText(), fatherName = fatherNameField.getText();
                String number = numberField.getText();
                String birthday = birthdayField.getText(), expiredDate = expiredDateField.getText();

                if(name.trim().equals("") ||
                        surname.trim().equals("") ||
                        fatherName.trim().equals("") ||
                        number.trim().equals("") ||
                        birthday.trim().equals("") ||
                        expiredDate.trim().equals("")) {

                    JOptionPane.showMessageDialog(null, "Поля для ввода не должны быть пустыми", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    if(!Helpers.checkText(name) ||
                            !Helpers.checkText(surname) ||
                            !Helpers.checkText(fatherName) ||
                            !Helpers.checkDate(birthday) ||
                            !Helpers.checkDate(expiredDate) ||
                            !Helpers.checkLengthStrict(number, 8)
                    ) {
                        JOptionPane.showMessageDialog(null, "Данные введены в некорректном формате", "Внимание", JOptionPane.WARNING_MESSAGE);
                    } else {
                        try {
                            String query = "insert into driver_licence (name, surname, father_name, number, birthday, expired_date) values ('" + name +
                                    "', '" + surname +
                                    "', '" +fatherName +
                                    "', '" + number +
                                    "', '"+birthday+
                                    "', '" +expiredDate + "')";

                            // Получаем id вставленной строки из БД
                            ResultSet res = DB.insert(query);
                            int id = res.getInt(1);

                            // Добавляем данные в класс
                            licence.setId(id);
                            licence.setName(name);
                            licence.setSurname(surname);
                            licence.setFatherName(fatherName);
                            licence.setNumber(number);
                            licence.setBirthday(birthday);
                            licence.setExpiredDate(expiredDate);

                            // Добавляем данные в массив с данными
                            licences.add(licence);
                            tableModel.addRow(new String[]{licence.getFullName(), licence.getNumber(), licence.getExpiredDate().toString()});

                            System.out.println(id);
                        } catch (Exception ex) {
                            System.out.println(ex);
                            JOptionPane.showMessageDialog(null, "Произошла ошибка при сохранении в бд", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                        dispose();
                    }
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }

    EditLicence(ArrayList<Licence> licences, DefaultTableModel tableModel, int index) {
        setContentPane(window);
        setPreferredSize(new Dimension(500, 400));
        setTitle("Редактирвоание ВУ");

        // Получаем ВУ из массива данных
        Licence licence = licences.get(index);

        // Заполняем текстовые поля данными
        nameField.setText(licence.getName());
        surnameField.setText(licence.getSurname());
        fatherNameField.setText(licence.getFatherName());
        numberField.setText(licence.getNumber());
        birthdayField.setText(licence.getBirthday().toString());
        expiredDateField.setText(licence.getExpiredDate().toString());


        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameField.getText(), surname = surnameField.getText(), fatherName = fatherNameField.getText();
                String number = numberField.getText();
                String birthday = birthdayField.getText(), expiredDate = expiredDateField.getText();

                // Проверка на пустые поля
                if(name.trim().equals("") ||
                        surname.trim().equals("") ||
                        fatherName.trim().equals("") ||
                        number.trim().equals("") ||
                        birthday.trim().equals("") ||
                        expiredDate.trim().equals("")) {

                    JOptionPane.showMessageDialog(null, "Поля для ввода не должны быть пустыми", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Проверка на корректность ввода данных
                    if(!Helpers.checkText(name) ||
                            !Helpers.checkText(surname) ||
                            !Helpers.checkText(fatherName) ||
                            !Helpers.checkDate(birthday) ||
                            !Helpers.checkDate(expiredDate) ||
                            !Helpers.checkLengthStrict(number, 8)
                    ) {
                        JOptionPane.showMessageDialog(null, "Данные введены в некорректном формате", "Внимание", JOptionPane.WARNING_MESSAGE);
                    } else {
                        try {
                            // Строка с запросом на редактирование
                            String query = "update driver_licence set name='" + name +
                                    "', surname='" + surname +
                                    "', father_name='" +fatherName +
                                    "', number='" + number +
                                    "', birthday='"+birthday+
                                    "', expired_date='" +expiredDate +
                                    "' where id=" + licence.getId();

                            DB.update(query);

                            // Заполняем поля класса обновленными значениями
                            licence.setName(name);
                            licence.setSurname(surname);
                            licence.setFatherName(fatherName);
                            licence.setNumber(number);
                            licence.setBirthday(birthday);
                            licence.setExpiredDate(expiredDate);

                            // Обновляем массив с данными и модель таблицы
                            licences.set(index, licence);
                            tableModel.setValueAt(licence.getFullName(), index, 0);
                            tableModel.setValueAt(licence.getNumber(), index, 1);
                            tableModel.setValueAt(licence.getExpiredDate(), index, 2);
                        } catch (Exception ex) {
                            System.out.println(ex);
                            JOptionPane.showMessageDialog(null, "Произошла ошибка при сохранении в бд", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                        dispose();
                    }
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
