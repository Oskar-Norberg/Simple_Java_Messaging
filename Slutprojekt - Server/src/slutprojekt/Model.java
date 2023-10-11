/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author oskar.norberg
 */
public class Model {

    public static String STRING_SEPARATOR = "|-|-|", STRING_SEPARATOR_2 = "|-2|2-|", STRING_SEPARATOR_3 = "|-3|3-|";
    public static int GENERAL_ID = 0, MAX_MESSAGE_LENGTH = 500;

    private ArrayList<SocketManager> sockets;
    private AccountManager accountManager;
    private RoomManager roomManager;

    public Model() {
        sockets = new ArrayList<>();
        roomManager = new RoomManager();
        accountManager = new AccountManager(this);
    }

    //Updates all sockets in the same room to display new messages, detects if a client has disconnected. Cleans up if they have.
    public void fetchForSocketsInSameRoom(String roomName) throws RoomDoesNotExistException {
        for (int i = 0; i < sockets.size(); i++) {
            if (sockets.get(i).getCurrentUser() != null && sockets.get(i).getCurrentRoom().getName().equals(roomName)) {
                try {
                    sockets.get(i).getOutStream().writeUTF(fetchChat(sockets.get(i).getCurrentRoom().getName()));
                } catch (IOException ex) {
                    cleanupThreads(i);
                }
            }
        }
    }

    public void addSocket(SocketManager s) {
        sockets.add(s);
    }

    public Room getRoomGeneral() throws RoomDoesNotExistException {
        return roomManager.getRoom("general");
    }

    public Room getRoom(String s) throws RoomDoesNotExistException {
        return roomManager.getRoom(s);
    }

    public Room getRoom(int id) throws RoomDoesNotExistException {
        return roomManager.getRoom(id);
    }

    public void createAccount(String username, String password) throws UserAlreadyExistsException, UserIsAlreadyAMemberOfThatRoomException, RoomDoesNotExistException {
        accountManager.addUser(username, password);
    }

    public String getUserRoomNames(User u) throws UserDoesNotExistException, RoomDoesNotExistException {
        ArrayList<Room> rooms = u.getJoinedRooms();
        String tmp = "";
        for (Room r : rooms) {
            tmp += r.getName() + ", ";
        }
        return tmp;
    }

    //Password to room was not specified
    public void joinRoom(String roomName, User user) throws RoomDoesNotExistException, UserIsAlreadyAMemberOfThatRoomException, WrongPasswordException, UserDoesNotExistException {
        Room room = roomManager.getRoom(roomName);
        //Checks if room is password protected
        if (room.getPassword() == null) {
            addRoomToUser(user, room);
        } else {
            throw new WrongPasswordException();
        }
    }

    //Password to room was specified
    public void joinRoom(String roomName, String roomPassword, User user) throws RoomDoesNotExistException, UserDoesNotExistException, UserIsAlreadyAMemberOfThatRoomException, WrongPasswordException {
        Room room = roomManager.getRoom(roomName);
        if (room.checkPassword(roomPassword)) {
            addRoomToUser(user, room);
        } else {
            throw new WrongPasswordException();
        }
    }

    //No password
    public void createRoom(String roomName, User user) throws UserDoesNotExistException, RoomAlreadyExistsException, UserIsAlreadyAMemberOfThatRoomException {
        roomManager.createRoom(roomName, user);
    }

    //Has password
    public void createRoom(String roomName, String roomPassword, User user) throws UserDoesNotExistException, RoomAlreadyExistsException, UserIsAlreadyAMemberOfThatRoomException, InvalidPasswordException {
        if (roomPassword.equals("")) {
            throw new InvalidPasswordException();
        }
        roomManager.createRoom(roomName, roomPassword, user);
    }

    public String fetchChat(String name) throws RoomDoesNotExistException {
        alertOnlineList();
        Room room = roomManager.getRoom(name);
        return room.toString();
    }

    public User login(String username, String password) throws WrongPasswordException, UserDoesNotExistException {
        User tmpUser = accountManager.login(username, password);
        if (tmpUser != null) {
            return tmpUser;
        } else {
            throw new WrongPasswordException();
        }
    }

    public void sendMessage(String room, User user, String message) throws RoomDoesNotExistException, IOException {
        roomManager.sendMessageToRoom(room, user, message);
        fetchForSocketsInSameRoom(room);
    }

    //Adds the room, private access, only accessed after checking password
    private void addRoomToUser(User user, Room room) throws UserDoesNotExistException, RoomDoesNotExistException, UserIsAlreadyAMemberOfThatRoomException {
        user.joinRoom(room);
    }

    public void alertOnlineList() {
        String tmp = "";

        for (int i = 0; i < sockets.size(); i++) {
            if (sockets.get(i).getCurrentUser() != null) {
                tmp += sockets.get(i).getCurrentUser().getUsername() + ", ";
            }
        }
        for (int i = 0; i < sockets.size(); i++) {
            if (sockets.get(i).getCurrentUser() != null) {
                try {
                    sockets.get(i).getOutStream().writeUTF("List-Online-Users: " + STRING_SEPARATOR + tmp + STRING_SEPARATOR);
                } catch (IOException ex) {
                    cleanupThreads(i);
                    alertOnlineList(); //Calls itself to remove the user which had disconnected
                    break;
                }
            }
        }
    }

    private void cleanupThreads(int i) {
        sockets.get(i).getThread().interrupt();
        sockets.remove(i);
    }
}
