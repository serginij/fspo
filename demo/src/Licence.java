import java.time.LocalDate;

/*  Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class Licence {
    protected int id;
    protected String name, surname, fatherName, number;
    protected LocalDate birthday, expiredDate;

    // Пустой конструктор неообходим для дальнейшего использования сеттеров
    Licence() {
    }

    // Конструктор со всеми полями ВУ
    Licence(int id, String name, String surname, String fatherName, String number, String birthday, String expiredDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.fatherName = fatherName;
        this.number = number;
        this.birthday = LocalDate.parse(birthday);
        this.expiredDate = LocalDate.parse(expiredDate);
    }

    // Сеттеры - для записи в отдельные поля
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setFatherName(String fatherName) {this.fatherName = fatherName;}
    public void setNumber(String number) {this.number = number;}
    public void setBirthday(String birthday) {this.birthday = LocalDate.parse(birthday);}
    public void setExpiredDate(String expiredDate) {this.expiredDate = LocalDate.parse(expiredDate);}

    // Геттеры - для чтения из отдельных полей
    public int getId() {return this.id;}
    public String getName() {return this.name;}
    public String getSurname() {return this.surname;}
    public String getFatherName() {return this.fatherName;}
    public String getNumber() {return this.number;}
    public LocalDate getBirthday() {return this.birthday;}
    public LocalDate getExpiredDate() {return this.expiredDate;}

    public String getFullName() {
        if(this.fatherName != null) {
            return this.surname + " " + this.name.charAt(0) + ". " + this.fatherName.charAt(0) + ".";
        } else return this.surname + " " + this.name.charAt(0) + ".";
    }
}
