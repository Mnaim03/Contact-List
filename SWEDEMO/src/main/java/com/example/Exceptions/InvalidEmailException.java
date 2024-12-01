/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Exceptions;

/**
 *
 * @author Vincenzo Ragone
 */
public class InvalidEmailException extends Exception {
    
    public InvalidEmailException(String msg){
    super(msg);
    }
    
}
