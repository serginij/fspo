public class UserData {
    protected String data[];

    UserData(String name, String phone, String email) {
        data = new String[3];
        data[0] = name;
        data[1] = phone;
        data[2] = email;
    }

    public String getName(){return data[0];}

    public String getPhone(){return data[1];}

    public String getEmail(){return data[2];}

    public String[] getAll(){return data;}
}
