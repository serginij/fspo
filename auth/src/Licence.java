import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Licence {
    protected String name, surname, fatherName, number;
    protected LocalDate birthday, expiredDate;
    protected int id;

    Licence() {
    }

    Licence(String name, String surname, String fatherName, String number, LocalDate birthday, LocalDate expiredDate, int id) {
        this.name = name;
        this.surname = surname;
        this.fatherName = fatherName;
        this.number = number;
        this.birthday = birthday;
        this.expiredDate = expiredDate;
        this.id = id;
    }

    public String getName () {return this.name;}
    public String getSurname () {return this.surname;}
    public String getFatherName () {return this.fatherName;}
    public String getNumber () {return this.number;}
    public LocalDate getExpiredDate () {return this.expiredDate;}
    public LocalDate getBirthday () {return this.birthday;}
    public int getId () {return this.id;}

    public void setName (String name ) {this.name = name;}
    public void setSurname (String surname) {this.surname = surname;}
    public void setFatherName (String fatherName) {this.fatherName = fatherName;}
    public void setNumber (String name) {this.number = name;}
    public void setExpiredDate (String date) {this.expiredDate = LocalDate.parse(date);}
    public void setBirthday (String birthday) {this.birthday = LocalDate.parse(birthday);}

    public String getFullName() {return this.surname + " " + this.name + " " + this.fatherName;}
//    public String getShortName() {return this.surname + " " + this.name. + " " + this.fatherName;}
}
