/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import static slutprojekt.Model.STRING_SEPARATOR;
import static slutprojekt.Model.STRING_SEPARATOR_2;

/**
 *
 * @author Araragi
 */
public class Message {

    private final String USERNAME, MESSAGE;

    public Message(String username, String message) {
        this.USERNAME = username;
        this.MESSAGE = message;
    }

    public String getUsername() {
        return USERNAME;
    }

    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public String toString() {
        return STRING_SEPARATOR + USERNAME + STRING_SEPARATOR + STRING_SEPARATOR_2 + MESSAGE + STRING_SEPARATOR_2;
    }

}
