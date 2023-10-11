/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

/**
 *
 * @author oskar.norberg
 */
public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(){
        super("User was not found in AccountManager");
    }
}
