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
import static slutprojekt.Model.GENERAL_ID;
import static slutprojekt.Model.STRING_SEPARATOR;
import static slutprojekt.Model.STRING_SEPARATOR_2;
import static slutprojekt.Model.STRING_SEPARATOR_3;

/**
 *
 * @author oskar.norberg
 */
public class AccountManager implements Saveable, Loadable {

    public static final String FILE_NAME = "users.txt";
    private ArrayList<User> users;
    private Model m;

    public AccountManager(Model m) {
        this.m = m;
        users = new ArrayList<>();
        readFromFile();
        Runtime.getRuntime().addShutdownHook(new Thread() { // Write to file when server closes
            public void run() {
                writeToFile();
            }
        });
    }
    
    public void addUser(String username, String password) throws UserAlreadyExistsException, RoomDoesNotExistException {
        boolean userAlreadyExists = false;
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                userAlreadyExists = true;
            }
        }

        if (!userAlreadyExists) {
            users.add(new User(username, PasswordFunctions.hashPassword(password), m.getRoomGeneral()));

        } else {
            throw new UserAlreadyExistsException();
        }
    }

    public User getUser(String username) throws UserDoesNotExistException {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        throw new UserDoesNotExistException();
    }

    public User getLatestUser() {
        return users.get(users.size() - 1);
    }

    public void joinRoom(User u, Room r) throws UserIsAlreadyAMemberOfThatRoomException {
        u.joinRoom(r);
    }

    public User login(String username, String password) throws UserDoesNotExistException {
        User userDatabase = null;
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                userDatabase = u;
                break;
            }
        }
        if (userDatabase == null) {
            throw new UserDoesNotExistException();
        } else {
            if (PasswordFunctions.checkPasswordHash(userDatabase.getPassword(), password)) {
                return userDatabase;
            } else {
                return null;
            }
        }
    }

    @Override
    public void writeToFile() {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("users.txt")));
            for (User u : users) {
                out.println(u.toString());
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void readFromFile() {
        try {
            Scanner sc = new Scanner(new File(FILE_NAME));
            while (sc.hasNext()) {
                String newUser = sc.nextLine();
                int usernameFirstIndex = newUser.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
                int usernameLastIndex = newUser.lastIndexOf(STRING_SEPARATOR);

                int passwordFirstIndex = newUser.indexOf(STRING_SEPARATOR_2) + STRING_SEPARATOR_2.length();
                int passwordLastIndex = newUser.lastIndexOf(STRING_SEPARATOR_2);

                int roomsFirstIndex = newUser.indexOf(STRING_SEPARATOR_3) + STRING_SEPARATOR_3.length();
                int roomsLastIndex = newUser.lastIndexOf(STRING_SEPARATOR_3);

                String username = newUser.substring(usernameFirstIndex, usernameLastIndex);
                String password = newUser.substring(passwordFirstIndex, passwordLastIndex);

                User tmpUser = new User(username, password, m.getRoomGeneral());

                String[] rooms = newUser.substring(roomsFirstIndex, roomsLastIndex).split(", ");

                for (String s : rooms) {
                    try {
                        if (!s.equals("")) {
                            int tmpID = Integer.parseInt(s);
                            if (tmpID != GENERAL_ID) {
                                tmpUser.joinRoom(m.getRoom(tmpID));
                            }
                        }
                    } catch (UserIsAlreadyAMemberOfThatRoomException ex) {
                        Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                users.add(tmpUser);
            }
        } catch (FileNotFoundException | RoomDoesNotExistException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
