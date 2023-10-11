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

/**
 *
 * @author Araragi
 */
public class RoomManager implements Loadable, Saveable {

    public static String FILENAME = "rooms.txt";
    private ArrayList<Room> rooms;

    public RoomManager() {
        rooms = new ArrayList<>();

        File f = new File(FILENAME);
        if (f.exists() && !f.isDirectory()) {
            readFromFile();
        } else {
            rooms.add(new Room("general", null));
        }

        Runtime.getRuntime().addShutdownHook(new Thread() { // Write to file when server closes
            public void run() {
                writeToFile();
            }
        });
    }

    public boolean getRoomPasswordProtectedStatus(String roomName) throws RoomDoesNotExistException {
        return getRoom(roomName).getPassword() != null;
    }

    public void createRoom(String roomName, User user) throws RoomAlreadyExistsException, UserIsAlreadyAMemberOfThatRoomException {
        boolean alreadyExists = false;
        for (Room r : rooms) {
            if (r.getName().equals(roomName)) {
                alreadyExists = true;
                break;
            }
        }
        if (alreadyExists) {
            throw new RoomAlreadyExistsException();
        } else {
            Room tmp = new Room(roomName, null);
            rooms.add(tmp);
            user.joinRoom(tmp);
        }
    }

    public void createRoom(String roomName, String password, User user) throws RoomAlreadyExistsException, UserIsAlreadyAMemberOfThatRoomException {
        boolean alreadyExists = false;
        for (Room r : rooms) {
            if (r.getName().equals(roomName)) {
                alreadyExists = true;
                break;
            }
        }
        if (alreadyExists) {
            throw new RoomAlreadyExistsException();
        } else {
            Room tmp = new Room(roomName, password);
            rooms.add(tmp);
            user.joinRoom(tmp);
        }
    }

    public void sendMessageToRoom(String room, User user, String message) throws RoomDoesNotExistException {
        getRoom(room).addMessage(user.getUsername(), message);
    }

    public Room getRoom(int ID) throws RoomDoesNotExistException {
        for (Room r : rooms) {
            if (r.getID() == ID) {
                return r;
            }
        }
        throw new RoomDoesNotExistException();
    }

    public Room getRoom(String name) throws RoomDoesNotExistException {
        for (Room r : rooms) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        throw new RoomDoesNotExistException();
    }

    @Override
    public void readFromFile() {
        try {
            Scanner sc = new Scanner(new File(FILENAME));
            while (sc.hasNext()) {
                String name = sc.next();
                String password = sc.next();
                if (password.equals("null")) {
                    rooms.add(new Room(name, null));
                } else {
                    Room tmp = new Room(name, null);
                    tmp.setPassword(password); //Bypasses hash to stop if from hashing twice
                    rooms.add(tmp);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeToFile() {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILENAME)));
            for (Room r : rooms) {
                out.println(r.getName() + " " + r.getPassword());
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
