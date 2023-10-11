/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

/**
 *
 * @author Araragi
 */
public class RoomAlreadyExistsException extends Exception{
    public RoomAlreadyExistsException(){
        super("This room already exists");
    }
}
