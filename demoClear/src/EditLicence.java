import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/*
    Разработал: Сергей Котлицкий
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
    private JPanel main;

    // Конструктор для создания ВУ
    EditLicence(DefaultTableModel tableModel) {
        setContentPane(main);
        setTitle("Создание ВУ");
        setPreferredSize(new Dimension(600, 500));

        // Устанавливаем окно по центру экрана
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 300,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 250);

        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/car.png")));

        // Создаем экземпляр класса ВУ
        Licence licence = new Licence();

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Получаем значения из полей ввода
                String name = nameField.getText(),
                        surname = surnameField.getText(),
                        fatherName = fatherNameField.getText(),
                        number = numberField.getText(),
                        birthday = birthdayField.getText(),
                        expiredDate = expiredDateField.getText();

                // Проверяем пустоту полей ввода
                if(!checkEmpty()) {
                    // Проверяем правильность ввода данных
                    if(checkValid()) {
                        // Если успешно, то формируем запрос на вставку данных
                        String query = "insert into driver_licence (name, surname, father_name, number, birthday, expired_date) values (" +
                                "'" + name + "'," + "'"+ surname +"'," + "'"+fatherName+"'," + "'"+ number +"'," + "'"+ birthday +"'," + "'"+ expiredDate +"'" +
                                ")";

                        ResultSet res = DB.insert(query);

                        try {
                            int id = res.getInt(1);

                            // Вставляем данные в ВУ
                            licence.setId(id);
                            licence.setName(name);
                            licence.setSurname(surname);
                            licence.setNumber(number);
                            licence.setFatherName(fatherName);
                            licence.setBirthday(LocalDate.parse(birthday));
                            licence.setExpiredDate(LocalDate.parse(expiredDate));

                            // Добавляем ВУ в таблицу
                            tableModel.addRow(new String[]{String.valueOf(id),
                                    licence.getFullName(),
                                    licence.getNumber(),
                                    licence.getExpiredDate().toString()});

                        } catch (Exception ex) {
                            System.out.println(ex);
                            JOptionPane.showMessageDialog(null,
                                    "Произошла ошибка при сохранении ВУ", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                        dispose();
                    }
                }
            }
        });
    }

    // Функция проверки на пустоту полей ввода
    public boolean checkEmpty () {
        String name = nameField.getText(),
                surname = surnameField.getText(),
                number = numberField.getText(),
                birthday = birthdayField.getText(),
                expiredDate = expiredDateField.getText();

        if(name.trim().equals("") ||
                surname.trim().equals("") ||
                number.trim().equals("") ||
                birthday.trim().equals("") ||
                expiredDate.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Поля ввода должны быть заполнены", "Внимание", JOptionPane.WARNING_MESSAGE);
            return true;
        } else return false;
    }

    // Функция проверки правильности ввода данных
    public boolean checkValid () {
        String name = nameField.getText(),
                surname = surnameField.getText(),
                fatherName = fatherNameField.getText(),
                number = numberField.getText(),
                birthday = birthdayField.getText(),
                expiredDate = expiredDateField.getText();

        if(!Helpers.checkTextCyrillic(name) ||
                !Helpers.checkTextDigits(number) ||
                !Helpers.checkTextCyrillic(surname) ||
                !Helpers.checkTextCyrillic(fatherName) && !fatherName.equals("") ||
                !Helpers.checkDate(birthday) ||
                !Helpers.checkDate(expiredDate) ||
                !Helpers.checkLengthStrict(number, 8) ||
                !Helpers.checkLength(name, 45) ||
                !Helpers.checkLength(surname, 45)) {

            // Если формат неправильный, выдаем сообщение
            JOptionPane.showMessageDialog(null,
                    "Данные введены в неверном формате." +
                    "\nФормат даты - гггг-мм-дд" +
                    "\nНомер ВУ - 8 символов, цифры и латинские буквы" +
                    "\nФамилия, Имя - обязательные, кирилица" +
                    "\nОтчество - необязательное поле, кирилица", "Внимание", JOptionPane.WARNING_MESSAGE);

            return false;
        } else return true;
    }

    // Конструктор для редактирования ВУ
    EditLicence(DefaultTableModel tableModel, int index) {
        setContentPane(main);
        setTitle("Редактирование ВУ");
        setPreferredSize(new Dimension(600, 500));

        // Устанавливаем окно по центру экрана
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 300,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - 250);

        try {
            // Получаем данные о ВУ из БД
            ResultSet row = DB.select("select * from driver_licence where id=" + tableModel.getValueAt(index, 0));

            row.next();
            Licence licence = new Licence(row);

            // Заполняем поля ввода значениями из БД
            surnameField.setText(licence.getSurname());
            nameField.setText(licence.getName());
            fatherNameField.setText(licence.getFatherName());
            numberField.setText(licence.getNumber());
            birthdayField.setText(licence.getBirthday().toString());
            expiredDateField.setText(licence.getExpiredDate().toString());

            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    dispose();
                }
            });

            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    // Забираем значения в переменные из полей ввода
                    String name = nameField.getText(),
                            surname = surnameField.getText(),
                            fatherName = fatherNameField.getText(),
                            number = numberField.getText(),
                            birthday = birthdayField.getText(),
                            expiredDate = expiredDateField.getText();

                    // Проверка на пустоту полей ввода
                    if(!checkEmpty()) {
                        // Проверка на правильность введения данных
                        if(checkValid()) {
                            // Если успешно, то формируем запрос на обновление данных
                            String query = "update driver_licence " +
                                    "set name='" + name +
                                    "', surname='"+ surname +
                                    "', father_name='"+fatherName+
                                    "', number='"+ number +
                                    "', birthday='"+ birthday +
                                    "', expired_date='"+ expiredDate + "'" +
                                    "where id=" + licence.getId();

                            // Выполняем обновление данных
                            boolean res = DB.update(query);

                            if(res) {
                                // Обновляем данные в классе
                                licence.setName(name);
                                licence.setSurname(surname);
                                licence.setNumber(number);
                                licence.setFatherName(fatherName);
                                licence.setBirthday(LocalDate.parse(birthday));
                                licence.setExpiredDate(LocalDate.parse(expiredDate));

                                // Обновляем данные в таблице
                                tableModel.setValueAt(licence.getFullName(), index, 1);
                                tableModel.setValueAt(licence.getNumber(), index, 2);
                                tableModel.setValueAt(licence.getExpiredDate().toString(), index, 3);

                            } else JOptionPane.showMessageDialog(null,
                                    "Произошла ошибка при сохранении ВУ", "Ошибка", JOptionPane.ERROR_MESSAGE);

                            dispose();
                        }
                    }
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(null, "Произошла ошибка при обращении к БД", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
