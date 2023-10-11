/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static slutprojekt.Model.MAX_MESSAGE_LENGTH;
import static slutprojekt.Model.STRING_SEPARATOR;
import static slutprojekt.Model.STRING_SEPARATOR_2;
import static slutprojekt.Model.STRING_SEPARATOR_3;

/**
 *
 * @author oskar.norberg
 */
public class SocketManager implements Runnable {

    private Model m;
    private Thread t;
    private Socket s;
    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    private User currentUser;
    private Room currentRoom;

    public SocketManager(Model m, Socket s) {
        t = new Thread(this);
        this.m = m;
        this.s = s;
        currentUser = null;
        currentRoom = null;
        try {
            streamIn = new DataInputStream(s.getInputStream());
            streamOut = new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        t.start();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Thread getThread() {
        return t;
    }

    private void joinRoom(String input) throws UserDoesNotExistException, RoomDoesNotExistException, UserIsAlreadyAMemberOfThatRoomException, WrongPasswordException, IOException {
        int roomFirstIndex = input.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int roomLastIndex = input.lastIndexOf(STRING_SEPARATOR);

        String roomName = input.substring(roomFirstIndex, roomLastIndex);

        if (input.contains(STRING_SEPARATOR_2)) {
            int passwordFirstIndex = input.indexOf(STRING_SEPARATOR_2) + STRING_SEPARATOR_2.length();
            int passwordLastIndex = input.lastIndexOf(STRING_SEPARATOR_2);
            String roomPassword = input.substring(passwordFirstIndex, passwordLastIndex);
            m.joinRoom(roomName, roomPassword, currentUser);
        } else {
            m.joinRoom(roomName, currentUser);
        }
        sendAllJoinedRooms();
    }

    private void createRoom(String input) throws IOException, UserDoesNotExistException, RoomAlreadyExistsException, UserIsAlreadyAMemberOfThatRoomException, RoomDoesNotExistException, InvalidPasswordException, InvalidNameException {

        int roomFirstIndex = input.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int roomLastIndex = input.lastIndexOf(STRING_SEPARATOR);

        String roomName = input.substring(roomFirstIndex, roomLastIndex);

        checkInvalidInput(roomName);

        //User has specified a roomPassword
        if (input.contains(STRING_SEPARATOR_2)) {
            int passwordFirstIndex = input.indexOf(STRING_SEPARATOR_2) + STRING_SEPARATOR_2.length();
            int passwordLastIndex = input.lastIndexOf(STRING_SEPARATOR_2);

            String roomPassword = input.substring(passwordFirstIndex, passwordLastIndex);
            m.createRoom(roomName, roomPassword, currentUser);
        } //User has not specified a roomPassword
        else {
            m.createRoom(roomName, currentUser);
        }
        sendAllJoinedRooms();
    }

    private void checkInvalidInput(String input) throws InvalidNameException {
        if (input.contains(" ") || input.equals("") || input.contains(STRING_SEPARATOR) || input.contains(STRING_SEPARATOR_2) || input.contains(STRING_SEPARATOR_3)) {
            throw new InvalidNameException();
        }
    }

    private void checkInvalidInputAllowSpaces(String input) throws InvalidNameException {
        if (input.equals("") || input.contains(STRING_SEPARATOR) || input.contains(STRING_SEPARATOR_2) || input.contains(STRING_SEPARATOR_3)) {
            throw new InvalidNameException();
        }
    }

    private void sendMessage(String input) throws IOException, InvalidNameException, InvalidMessageException, RoomDoesNotExistException {
        int roomFirstIndex = input.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int roomLastIndex = input.lastIndexOf(STRING_SEPARATOR);

        int messageFirstIndex = input.indexOf(STRING_SEPARATOR_2) + STRING_SEPARATOR_2.length();
        int messageLastIndex = input.lastIndexOf(STRING_SEPARATOR_2);

        String room = input.substring(roomFirstIndex, roomLastIndex);
        checkInvalidInput(room);
        String message = input.substring(messageFirstIndex, messageLastIndex);
        if (message.length() >= MAX_MESSAGE_LENGTH) {
            throw new InvalidMessageException();
        }
        checkInvalidInputAllowSpaces(message);

        m.sendMessage(room, currentUser, message);

    }

    private void login(String input) throws IOException, InvalidNameException, WrongPasswordException, UserDoesNotExistException {
        int usernameFirstIndex = input.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int usernameLastIndex = input.lastIndexOf(STRING_SEPARATOR);

        int passwordFirstIndex = input.indexOf(STRING_SEPARATOR_2) + STRING_SEPARATOR_2.length();
        int passwordLastIndex = input.lastIndexOf(STRING_SEPARATOR_2);

        String username = input.substring(usernameFirstIndex, usernameLastIndex);
        checkInvalidInput(username);
        String password = input.substring(passwordFirstIndex, passwordLastIndex);
        checkInvalidInput(password);

        currentUser = m.login(username, password);
        streamOut.writeUTF("Login-Successful: " + STRING_SEPARATOR + currentUser.getUsername() + STRING_SEPARATOR);

    }

    private void sendAllJoinedRooms() throws UserDoesNotExistException, RoomDoesNotExistException, IOException {
        streamOut.writeUTF("List-Of-Rooms: " + STRING_SEPARATOR + m.getUserRoomNames(currentUser) + STRING_SEPARATOR);
    }

    private void createAccount(String input) throws IOException, RoomDoesNotExistException, InvalidNameException, UserAlreadyExistsException, UserIsAlreadyAMemberOfThatRoomException {
        int usernameFirstIndex = input.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int usernameLastIndex = input.lastIndexOf(STRING_SEPARATOR);

        int passwordFirstIndex = input.indexOf(STRING_SEPARATOR_2) + STRING_SEPARATOR_2.length();
        int passwordLastIndex = input.lastIndexOf(STRING_SEPARATOR_2);

        String username = input.substring(usernameFirstIndex, usernameLastIndex);
        checkInvalidInput(username);
        String password = input.substring(passwordFirstIndex, passwordLastIndex);
        checkInvalidInput(password);

        m.createAccount(username, password);
    }

    private void fetchSpecificChat(String input) throws RoomDoesNotExistException, IOException, InvalidNameException {
        int roomFirstIndex = input.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int roomLastIndex = input.lastIndexOf(STRING_SEPARATOR);

        String roomName = input.substring(roomFirstIndex, roomLastIndex);
        checkInvalidInput(roomName);

        streamOut.writeUTF(m.fetchChat(roomName));
        currentRoom = m.getRoom(roomName);
    }

    private void fetchGeneral() throws RoomDoesNotExistException, IOException {
        streamOut.writeUTF(m.fetchChat("general"));
        currentRoom = m.getRoomGeneral();
    }

    public DataOutputStream getOutStream() {
        return streamOut;
    }

    public DataInputStream getInStream() {
        return streamIn;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String input = streamIn.readUTF();
                int indexCommand = input.indexOf(":");
                if (indexCommand != -1) { //Om det innehåller ett : vilket betyder att det är ett kommando
                    String command = input.substring(0, indexCommand);
                    switch (command) {
                        case "Create-Account" ->
                            createAccount(input);
                        case "Login-Account" -> {
                            login(input);
                            sendAllJoinedRooms();
                        }
                        case "Send-Message" ->
                            sendMessage(input);
                        case "Create-Room" ->
                            createRoom(input);
                        case "Join-Room" ->
                            joinRoom(input);
                        default -> {
                        }
                    }
                } else if (input.contains("Fetch-All-Messages")) {
                    if (input.contains(STRING_SEPARATOR)) {
                        fetchSpecificChat(input);
                    } else {
                        fetchGeneral();
                    }
                }
            } catch (IOException ex) {
                break;
            } catch (UserDoesNotExistException ex) { // Det här känns så dumt
                Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    streamOut.writeUTF("ALERT: " + STRING_SEPARATOR + "User does not exist" + STRING_SEPARATOR);
                } catch (IOException ex1) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (RoomDoesNotExistException ex) {
                Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    streamOut.writeUTF("ALERT: " + STRING_SEPARATOR + "Room does not exist" + STRING_SEPARATOR);
                } catch (IOException ex1) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (RoomAlreadyExistsException ex) {
                Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    streamOut.writeUTF("ALERT: " + STRING_SEPARATOR + "Room already exists" + STRING_SEPARATOR);
                } catch (IOException ex1) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (UserIsAlreadyAMemberOfThatRoomException ex) {
                Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    streamOut.writeUTF("ALERT: " + STRING_SEPARATOR + "User is already a member of that room" + STRING_SEPARATOR);
                } catch (IOException ex1) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (InvalidPasswordException ex) {
                Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    streamOut.writeUTF("ALERT: " + STRING_SEPARATOR + "Invalid password" + STRING_SEPARATOR);
                } catch (IOException ex1) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (WrongPasswordException ex) {
                Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    streamOut.writeUTF("ALERT: " + STRING_SEPARATOR + "Wrong password" + STRING_SEPARATOR);
                } catch (IOException ex1) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (InvalidNameException ex) {
                Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    streamOut.writeUTF("ALERT: " + STRING_SEPARATOR + "Invalid name" + STRING_SEPARATOR);
                } catch (IOException ex1) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (InvalidMessageException ex) {
                try {
                    streamOut.writeUTF("ALERT: " + STRING_SEPARATOR + "Invalid message" + STRING_SEPARATOR);
                } catch (IOException ex1) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UserAlreadyExistsException ex) {
                try {
                    streamOut.writeUTF("ALERT: " + STRING_SEPARATOR + "User already exists" + STRING_SEPARATOR);
                } catch (IOException ex1) {
                    Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(SocketManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
