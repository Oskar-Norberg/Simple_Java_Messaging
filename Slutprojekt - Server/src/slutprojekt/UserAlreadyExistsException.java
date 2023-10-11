/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

/**
 *
 * @author oskar.norberg
 */
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
        super("User already found in AccountManager");
    }
}
