import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class transport extends JFrame{
    private JComboBox comboBox;
    private JPanel main;
    protected DefaultComboBoxModel comboModel;
//    protected ArrayList<Licence> licences;
    protected DefaultListModel listModel;
    protected int selectedWindow = 1;

    transport () {
        setContentPane(main);
        setTitle("Транспортные средства");
        setPreferredSize(new Dimension(500, 400));

        comboModel = new DefaultComboBoxModel();
        comboBox.setModel(comboModel);

        comboModel.addElement("Водительские удостоверения");
        comboModel.addElement("Транспортные средства");

        comboBox.setSelectedIndex(1);

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

    }
}
