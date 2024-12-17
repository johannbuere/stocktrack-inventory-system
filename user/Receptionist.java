/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

/**
 *
 * @author Joy De Castro
 */
public class Receptionist extends UserBase {   
    private String password;
    private String username;

    public Receptionist(){
        
    }
    public Receptionist(String firstName, String lastName, String address, String phoneNumber, double salary, String role, String username, String password){
        super(firstName, lastName, address, phoneNumber, salary, role); 
        this.username = username;
        this.password = password;
    }
    
    
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String userName){
        this.username = userName;
    }
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    
}
