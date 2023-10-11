/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static slutprojekt.Model.STRING_SEPARATOR;
import static slutprojekt.Model.STRING_SEPARATOR_2;

/**
 *
 * @author Araragi
 */
public class Room implements Loadable, Saveable {

    public static int amtRooms = 0;
    private int id;
    private String name;
    private String password;
    private String filename;
    private ArrayList<Message> messages;

    public Room(String name, String password) {
        filename = "room-" + amtRooms + ".txt";
        id = amtRooms;
        amtRooms++;
        this.name = name;
        if (password != null) { //Just so it doesn't try to hash null
            this.password = PasswordFunctions.hashPassword(password);
        } else {
            this.password = null;
        }
        messages = new ArrayList<>();

        File f = new File(filename);
        if (f.exists() && !f.isDirectory()) {
            readFromFile();
        }

        Runtime.getRuntime().addShutdownHook(new Thread() { // Write to file when server closes
            public void run() {
                writeToFile();
            }
        });
    }

    public void addMessage(String username, String message) {
        messages.add(new Message(username, message));
    }

    public boolean checkPassword(String password) {
        return PasswordFunctions.checkPasswordHash(this.password, password);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        String tmp = "Room-Name: " + STRING_SEPARATOR + name + STRING_SEPARATOR;
        for (Message m : messages) {
            tmp += "\n" + m.toString();
        }
        return tmp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void readFromFile() {
        System.out.println("Room " + name + " initialized from file:  " + filename);
        try {
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNext()) {
                String message = sc.nextLine();

                int usernameFirstIndex = message.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
                int usernameLastIndex = message.lastIndexOf(STRING_SEPARATOR);

                int messageFirstIndex = message.indexOf(STRING_SEPARATOR_2) + STRING_SEPARATOR_2.length();
                int messageLastIndex = message.lastIndexOf(STRING_SEPARATOR_2);

                String username = message.substring(usernameFirstIndex, usernameLastIndex);
                String string = message.substring(messageFirstIndex, messageLastIndex);

                messages.add(new Message(username, string));

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeToFile() {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            for (Message m : messages) {
                out.println(m.toString());
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
