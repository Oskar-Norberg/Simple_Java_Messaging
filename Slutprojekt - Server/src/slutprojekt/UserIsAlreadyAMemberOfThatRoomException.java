package slutprojekt;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Araragi
 */
public class UserIsAlreadyAMemberOfThatRoomException extends Exception {

    public UserIsAlreadyAMemberOfThatRoomException() {
        super("User is already a member of that room");
    }
}
