import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AddEditForm extends JDialog {
    private JButton save;
    private JButton cancel;
    private JTextField name;
    private JTextField surname;
    private JTextField phone;
    private JTextField email;
    private JPanel main;

    AddEditForm(DefaultListModel<String> model, boolean mode, int index, ArrayList<UserData> datalist) {
        setContentPane(main);
        setTitle("Окно редактирования");
        setPreferredSize(new Dimension(300, 300));

        if(mode) {
            UserData current = datalist.get(index);
            String nameText = current.name, surnameText = current.surname, phoneText = current.phone, emailText = current.email;

            name.setText(nameText);
            surname.setText(surnameText);
            phone.setText(phoneText);
            email.setText(emailText);
        }

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nameText = name.getText(), surnameText = surname.getText(), phoneText = phone.getText(), emailText = email.getText();

                if(nameText.trim().length() == 0 || surnameText.trim().length() == 0 || phoneText.trim().length() == 0 || emailText.trim().length() == 0) {
                    JOptionPane.showMessageDialog(null, "Поля должны быть заполнены", "Внимание", JOptionPane.WARNING_MESSAGE);
                } else {
                    UserData data = new UserData(nameText, surnameText, phoneText, emailText);
                    if(mode && index != -1) {
                        model.setElementAt(nameText + "\t" + surnameText + "\t" + phoneText + "\t" + emailText, index);
                        datalist.set(index, data);
                    } else {
                        model.addElement(nameText + "\t" + surnameText + "\t" + phoneText + "\t" + emailText);
                        datalist.add(data);
                    }
//                    ArrayList al = new ArrayList()
                    //do something with your ArrayList

//                    String json = new Gson().toJson(list);

//                    try {
//                        FileOutputStream fos = null;
//                        fos = new FileOutputStream("data");
//                        ObjectOutputStream oos = new ObjectOutputStream(fos);
//                        oos.writeObject(datalist);
//                        oos.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    dispose();
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
