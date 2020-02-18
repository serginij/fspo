public class UserData {
    protected String name, surname, phone, email;

    public UserData(String name, String surname, String phone, String email) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public void addName(String name) {
        this.name = name;
    }

    public void addSurname(String surname) {
        this.surname = surname;
    }

    public void addPhone(String phone) {
        this.phone = phone;
    }

    public void addEmail(String email) {
        this.email = email;
    }

    public String getName(){return name;}
    public String getSurname(){return surname;}
    public String getPhone(){return phone;}
    public String getEmail(){return email;}
}
