import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Main extends JFrame{
    private JPanel main;
    private JButton add;
    private JButton edit;
    private JButton delete;
    private JList list;
    private String data[] = {"One", "Two", "Three"};
    private DefaultListModel<String> model = new DefaultListModel<String>();
    private ArrayList<UserData> datalist= new ArrayList<>();

    Main(boolean isAdmin) {
        setContentPane(main);
        setTitle("Главное окно");
        setPreferredSize(new Dimension(500, 400));

//        for (int i = 0; i < data.length; i++) {
//            model.addElement(data[i]);
//        }

//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream("data");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            datalist = (ArrayList) ois.readObject();
//            ois.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }


        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);


        if(!isAdmin) {
            add.setVisible(false);
            delete.setVisible(false);
            edit.setVisible(false);
        }
        delete.setEnabled(false);
        edit.setEnabled(false);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {

                    if (list.getSelectedIndex() == -1) {
                        //No selection, disable fire button.
                        delete.setEnabled(false);
                        delete.setEnabled(false);

                    } else {
                        //Selection, enable the fire button.
                        delete.setEnabled(true);
                        edit.setEnabled(true);
                    }
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(list.getSelectedIndex() != -1) {
                    int input = JOptionPane.showConfirmDialog(null, "Удалить элемент списка ?", "Внимание", JOptionPane.OK_CANCEL_OPTION);

                    if(input == 0) {
                        model.remove(list.getSelectedIndex());
                    }
                    list.setSelectedIndex(-1);

                }
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddEditForm form = new AddEditForm(model, false, -1, datalist);
                form.pack();
                form.setVisible(true);
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddEditForm form = new AddEditForm(model, true, list.getSelectedIndex(), datalist);
                form.pack();
                form.setVisible(true);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dispose();
            }
        });
    }
}
