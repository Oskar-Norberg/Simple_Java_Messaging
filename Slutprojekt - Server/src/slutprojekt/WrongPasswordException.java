/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

/**
 *
 * @author oskar.norberg
 */
public class WrongPasswordException extends Exception {
    public WrongPasswordException(){
        super("Wrong password");
    }
}
