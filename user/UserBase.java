package user;

import util.UIDGenerator;

public class UserBase {
    
    protected String UID; 
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phoneNumber;
    protected double salary;
    protected String role;
    
    public UserBase() {
    }
    
    public UserBase(String firstName, String lastName, String address, String phoneNumber, double salary, String role) {
        this.UID = UIDGenerator.generateUID(role);        
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.role = role;        
    }

    // Getters and setters for all the fields
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }   
}
