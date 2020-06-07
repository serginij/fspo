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

    EditLicence(DefaultTableModel tableModel) {
        setContentPane(main);
        setTitle("Создание ВУ");
        setPreferredSize(new Dimension(600, 500));

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
                String name = nameField.getText(),
                        surname = surnameField.getText(),
                        fatherName = fatherNameField.getText(),
                        number = numberField.getText(),
                        birthday = birthdayField.getText(),
                        expiredDate = expiredDateField.getText();

                if(!checkEmpty()) {
                    if(checkValid()) {
                        String query = "insert into driver_licence (name, surname, father_name, number, birthday, expired_date) values (" +
                                "'" + name + "'," + "'"+ surname +"'," + "'"+fatherName+"'," + "'"+ number +"'," + "'"+ birthday +"'," + "'"+ expiredDate +"'" +
                                ")";

                        ResultSet res = DB.insert(query);

                        try {
                            int id = res.getInt(1);

                            licence.setId(id);
                            licence.setName(name);
                            licence.setSurname(surname);
                            licence.setNumber(number);
                            licence.setFatherName(fatherName);
                            licence.setBirthday(LocalDate.parse(birthday));
                            licence.setExpiredDate(LocalDate.parse(expiredDate));

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
            JOptionPane.showMessageDialog(null, "Данные введены в неверном формате." +
                    "\nФормат даты - гггг-мм-дд" +
                    "\nНомер ВУ - 8 символов, цифры и латинские буквы" +
                    "\nФамилия, Имя - обязательные, кирилица" +
                    "\nОтчество - необязательное поле, кирилица", "Внимание", JOptionPane.WARNING_MESSAGE);

            return false;
        } else return true;
    }

    EditLicence(DefaultTableModel tableModel, int index) {
        setContentPane(main);
        setTitle("Редактирование ВУ");
        setPreferredSize(new Dimension(600, 500));

        try {
            ResultSet row = DB.select("select * from driver_licence where id=" + tableModel.getValueAt(index, 0));

            row.next();
            Licence licence = new Licence(row);

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
                    String name = nameField.getText(),
                            surname = surnameField.getText(),
                            fatherName = fatherNameField.getText(),
                            number = numberField.getText(),
                            birthday = birthdayField.getText(),
                            expiredDate = expiredDateField.getText();

                    if(!checkEmpty()) {
                        if(checkValid()) {
                            String query = "update driver_licence " +
                                    "set name='" + name +
                                    "', surname='"+ surname +
                                    "', father_name='"+fatherName+
                                    "', number='"+ number +
                                    "', birthday='"+ birthday +
                                    "', expired_date='"+ expiredDate + "'" +
                                    "where id=" + licence.getId();

                            boolean res = DB.update(query);

                            if(res) {
                                licence.setName(name);
                                licence.setSurname(surname);
                                licence.setNumber(number);
                                licence.setFatherName(fatherName);
                                licence.setBirthday(LocalDate.parse(birthday));
                                licence.setExpiredDate(LocalDate.parse(expiredDate));

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
