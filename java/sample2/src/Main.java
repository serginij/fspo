import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main extends JFrame{
    private JPanel main;
    private JButton add;
    private JButton show;
    ArrayList<UserData> datalist;

    Main() {
        setContentPane(main);
        setPreferredSize(new Dimension(500, 400));
        setTitle("Главное окно");
        datalist = new ArrayList<>();

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddDialog a = new AddDialog(datalist);
                a.setVisible(true);
                a.pack();
            }
        });

        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for(int i=0; i < datalist.size(); i++) {
                    System.out.println(datalist.get(i).getName() + " " + datalist.get(i).getSurname() + " " + datalist.get(i).getPhone() + " " + datalist.get(i).getEmail());
                }
            }
        });
    }

}

/*
Написать программу с окном авторизации, через которое возможет вход двух типов пользователей.
Пользователи:
- пользователь системы, которому в дальнейшем будет доступна возможность только просмотра данных
- администратор, который может добавлять, удалять, редактировать и просматривать данные

Для администратора создать возможность полного управления списком данных:
- добавление данных ( Фамилия, Имя, Телефон, Почта )
- удаление данных ( удаляемые данные или находить по номеру телефона и удалять, или указывать вручную номер в списке )
- редактировать данные ( либо поиск по телефону, либо по номеру в списке ). При редактировании - новое окно с заполненными полями
- просмотр


*/
