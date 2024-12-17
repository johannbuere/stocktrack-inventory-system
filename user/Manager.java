package user;

public class Manager extends UserBase {
    private String password;
    private String username;

    public Manager() {
    }

    public Manager(String firstName, String lastName, String address, String phoneNumber, double salary, String role, String username, String password) {
        super(firstName, lastName, address, phoneNumber, salary, role); 
        this.username = username;
        this.password = password;
    }      

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
