import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class driverLicenceEdit extends JFrame {
    private JPanel main;
    private JTextField surname;
    private JTextField name;
    private JTextField fatherName;
    private JTextField birtday;
    private JButton save;
    private JButton cancel;
    private JTextField number;
    private JTextField expiredDate;

    driverLicenceEdit(DefaultListModel listModel, ArrayList<Licence> licences) {
        setContentPane(main);
        setTitle("Редактирование ВУ");
        setPreferredSize(new Dimension(500, 400));

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nameText = name.getText(), surnameText = surname.getText(), fatherText = fatherName.getText();
                String birthDay = birtday.getText(), expiredText = expiredDate.getText();
                String numberText = number.getText();

                Licence licence = new Licence();

                //Проверка всех полей на заполненность
                //Если хотя бы одно поле пустое, то выдать предупреждение
                if(nameText.trim().equals("") || surnameText.trim().equals("") || fatherText.trim().equals("") || birthDay.trim().equals("") || expiredText.trim().equals("") || numberText.trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Поля для ввода не должны быть пустыми", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {

                    String query = "insert into driver_licence (name, surname, father_name, birthday, number, expired_date) values ('" + nameText +"', '" + surnameText+ "', '" + fatherText +"', '" + birthDay +"', '"+numberText+"','"+expiredText+"')";
                    boolean res = DB.insert(query);
                    if(res) {
                        licence.setName(nameText);
                        licence.setSurname(surnameText);
                        licence.setFatherName(fatherText);
                        licence.setBirthday(birthDay);
                        licence.setNumber(numberText);
                        licence.setExpiredDate(expiredText);

//                        licences.(index, licence);
                        licences.add(licence);
//                        listModel.set(index, licence.getFullName() + " №" + licence.getNumber() + " до " + licence.getExpiredDate());
                        listModel.addElement(licence.getFullName() + " №" + licence.getNumber() + " до " + licence.getExpiredDate());

                        dispose();
                        JOptionPane.showMessageDialog(null, "Данные сохранены", "Успешно", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Произошла ошибка при сохранении в базу данных", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }

                System.out.println(nameText + " " + surnameText + " " + fatherText + " " + birthDay );
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

    }

    driverLicenceEdit(DefaultListModel listModel, ArrayList<Licence> licences, int index) {
        setContentPane(main);
        setTitle("Редактирование ВУ");
        setPreferredSize(new Dimension(500, 400));

        //Если окно открывается на редактирование, то записать в поля ввода существующие значения
        Licence licence;

        licence = licences.get(index);
        name.setText(licence.getName());
        surname.setText(licence.getSurname());
        fatherName.setText(licence.getFatherName());
        birtday.setText(String.valueOf(licence.getBirthday()));
        number.setText(licence.getNumber());
        expiredDate.setText(String.valueOf(licence.getExpiredDate()));

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nameText = name.getText(), surnameText = surname.getText(), fatherText = fatherName.getText();
                String birthDay = birtday.getText(), expiredText = expiredDate.getText();
                String numberText = number.getText();

                //Проверка всех полей на заполненность
                //Если хотя бы одно поле пустое, то выдать предупреждение
                if(nameText.trim().equals("") || surnameText.trim().equals("") || fatherText.trim().equals("") || birthDay.trim().equals("") || expiredText.trim().equals("") || numberText.trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Поля для ввода не должны быть пустыми", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else if(!Helpers.isDate(birthDay) || !Helpers.isDate(expiredText) || !Helpers.isText(nameText) || !Helpers.isText(surnameText) || !Helpers.checkLengthStrict(numberText, 8)) {
                    JOptionPane.showMessageDialog(null, "Данные введены в неверном формате", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else{
                    String query = "update driver_licence set name = '" + nameText +"', surname = '" + surnameText+ "', father_name = '" + fatherText +"', birthday = '" + birthDay +"' where id = " + licence.getId();
                    boolean res = DB.insert(query);
                    if(res) {
                        licence.setName(nameText);
                        licence.setSurname(surnameText);
                        licence.setFatherName(fatherText);
                        licence.setBirthday(birthDay);
                        licence.setNumber(numberText);
                        licence.setExpiredDate(expiredText);

                        licences.set(index, licence);
                        listModel.set(index, licence.getFullName() + " №" + licence.getNumber() + " до " + licence.getExpiredDate());

                        dispose();
                        JOptionPane.showMessageDialog(null, "Данные сохранены", "Успешно", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Произошла ошибка при сохранении в базу данных", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }

                System.out.println(nameText + " " + surnameText + " " + fatherText + " " + birthDay );
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
