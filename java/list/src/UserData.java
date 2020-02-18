public class UserData {
    protected String data[];
    UserData(String name, String surname, String phone, String email) {
        data = new String[4];
        data[0] = name;
        data[1] = surname;
        data[2] = phone;
        data[3] = email;
    }

    String getName(){return data[0];}

    String getSurname(){return data[1];}

    String getPhone(){return data[2];}

    String getEmail(){return data[3];}

}
