/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Contact;

import com.example.interfaces.CheckerEmail;

/**
 *
 * @author Vincenzo Ragone
 */
public class ContactEmail implements CheckerEmail{
    private String email ; 
    
    
    public ContactEmail(String email){
    this.email=email; 
    }
    
    public String getAssociatedEmail(){
    return email; 
    }
    public void setAssociatedEmail(String email){
    this.email=email;
    }
    //da rivedere
    @Override
    public boolean isValidEmail(){
     return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    
    
    
    
}
