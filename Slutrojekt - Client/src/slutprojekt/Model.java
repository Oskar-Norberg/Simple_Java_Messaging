/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oskar.norberg
 */
public class Model {

    public static String STRING_SEPARATOR = "|-|-|", STRING_SEPARATOR_2 = "|-2|2-|", STRING_SEPARATOR_3 = "|-3|3-|";

    private MainFrame mainFrame; //Modellen måste komma åt framen för att kunda ändra från login skärmen till inloggad skärmen.

    private ArrayList<View> views;

    private String serverIP;
    private int serverPort;
    private String errorMessage;
    private String alert;

    private String[] rooms;
    private String[] onlineUsers;
    private String currentUser;
    private String currentRoomName;
    private ArrayList<Message> messages;

    private Socket so;
    private DataInputStream in;
    private DataOutputStream out;
    private SocketReceiver socketReceiver;

    public Model(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        views = new ArrayList<>();
        messages = new ArrayList<>();
        rooms = null;
        onlineUsers = null;
        alert = null;
        socketReceiver = null;
    }

    public void createAccount(String serverIP, int serverPort, String username, String password) {
        connectToServer(serverIP, serverPort);
        try {
            out.writeUTF("Create-Account: " + STRING_SEPARATOR + username + STRING_SEPARATOR + "1" + STRING_SEPARATOR_2 + password + STRING_SEPARATOR_2);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void login(String serverIP, int serverPort, String username, String password) {
        connectToServer(serverIP, serverPort);
        try {
            out.writeUTF("Login-Account: " + STRING_SEPARATOR + username + STRING_SEPARATOR + STRING_SEPARATOR_2 + password + STRING_SEPARATOR_2);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createRoom(String roomName, String roomPassword) {
        try {
            if (roomPassword.equals("")) {
                out.writeUTF("Create-Room: " + STRING_SEPARATOR + roomName + STRING_SEPARATOR);
            } else {
                out.writeUTF("Create-Room: " + STRING_SEPARATOR + roomName + STRING_SEPARATOR + STRING_SEPARATOR_2 + roomPassword + STRING_SEPARATOR_2);
            }
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void joinRoom(String roomName, String roomPassword) {
        try {
            if (roomPassword.equals("")) {
                out.writeUTF("Join-Room: " + STRING_SEPARATOR + roomName + STRING_SEPARATOR);
            } else {
                out.writeUTF("Join-Room: " + STRING_SEPARATOR + roomName + STRING_SEPARATOR + STRING_SEPARATOR_2 + roomPassword + STRING_SEPARATOR_2);
            }
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerView(View v) {
        views.add(v);
    }

    private void updateViews() {
        for (View view : views) {
            view.update();
        }
    }

    public void setReply(String reply) {
        int indexCommand = reply.indexOf(":");
        String command = reply.substring(0, indexCommand);
        boolean resetAlert = true;
        switch (command) {
            case "Login-Successful" ->
                loginSuccessful(reply);
            case "Room-Name" ->
                setMessages(reply);
            case "List-Of-Rooms" ->
                setListRooms(reply);
            case "List-Online-Users" ->
                setOnlineUsers(reply);
            case "ALERT" -> {
                resetAlert = false;
                setAlert(reply);
            }
            default -> {
            }
        }
        if (resetAlert) {
            alert = null;
        }
        updateViews();
    }

    private void connectToServer(String serverIP, int serverPort) {
        try {
            so = new Socket(serverIP, serverPort);
            out = new DataOutputStream(so.getOutputStream());
            socketReceiver = new SocketReceiver(this, so);
        } catch (IOException ex) {
            Logger.getLogger(Model.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getMessages() {
        String tmp = "";
        for (Message m : messages) {
            tmp += m.toString() + "\n";
        }
        return tmp;
    }

    public String[] getRooms() {
        return rooms;
    }

    public String[] getOnlineUsers() {
        return onlineUsers;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getAlert() {
        return alert;
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF("Send-Message: " + STRING_SEPARATOR + currentRoomName + STRING_SEPARATOR + STRING_SEPARATOR_2 + message + STRING_SEPARATOR_2);
            fetchRoomMessages();
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRoomName() {
        return currentRoomName;
    }

    public void setRoomName(String selectedRoomName) {
        currentRoomName = selectedRoomName;
        fetchRoomMessages();
    }

    private void loginSuccessful(String reply) {
        int usernameFirstIndex = reply.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int usernameLastIndex = reply.lastIndexOf(STRING_SEPARATOR);
        String tmpUsername = reply.substring(usernameFirstIndex, usernameLastIndex);
        currentUser = tmpUsername;
        mainFrame.loginSuccessful();
        fetchGeneralMessages();
    }

    private void fetchGeneralMessages() {
        try {
            out.writeUTF("Fetch-All-Messages");
        } catch (IOException ex) {
            Logger.getLogger(Model.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fetchRoomMessages() {
        try {
            out.writeUTF("Fetch-All-Messages " + STRING_SEPARATOR + currentRoomName + STRING_SEPARATOR);
        } catch (IOException ex) {
            Logger.getLogger(Model.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setOnlineUsers(String reply) {
        int onlineFirstIndex = reply.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int onlineLastIndex = reply.lastIndexOf(STRING_SEPARATOR);
        String tmpOnlineUsers = reply.substring(onlineFirstIndex, onlineLastIndex);
        String[] tmpOnlineArr = tmpOnlineUsers.split(", ");
        onlineUsers = new String[tmpOnlineArr.length];
        System.arraycopy(tmpOnlineArr, 0, onlineUsers, 0, tmpOnlineArr.length);
    }

    private void setAlert(String reply) {
        int alertFirstIndex = reply.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int alertLastIndex = reply.lastIndexOf(STRING_SEPARATOR);
        alert = reply.substring(alertFirstIndex, alertLastIndex);
    }

    private void setListRooms(String reply) {
        int roomFirstIndex = reply.indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int roomLastIndex = reply.lastIndexOf(STRING_SEPARATOR);
        String tmpRooms = reply.substring(roomFirstIndex, roomLastIndex);
        String[] tmpRoomsArr = tmpRooms.split(", ");
        rooms = new String[tmpRoomsArr.length];
        System.arraycopy(tmpRoomsArr, 0, rooms, 0, tmpRoomsArr.length);
    }

    private void setMessages(String reply) {
        messages = new ArrayList<>();
        String[] messagesSplit = reply.split("\\n+");

        int roomnameFirstIndex = messagesSplit[0].indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
        int roomnameLastIndex = messagesSplit[0].lastIndexOf(STRING_SEPARATOR);

        currentRoomName = messagesSplit[0].substring(roomnameFirstIndex, roomnameLastIndex);

        for (int i = 1; i < messagesSplit.length; i++) {
            int usernameFirstIndex = messagesSplit[i].indexOf(STRING_SEPARATOR) + STRING_SEPARATOR.length();
            int usernameLastIndex = messagesSplit[i].lastIndexOf(STRING_SEPARATOR);

            int messageFirstIndex = messagesSplit[i].indexOf(STRING_SEPARATOR_2) + STRING_SEPARATOR_2.length();
            int messageLastIndex = messagesSplit[i].lastIndexOf(STRING_SEPARATOR_2);

            String tmpUsername = messagesSplit[i].substring(usernameFirstIndex, usernameLastIndex);
            String tmpMessage = messagesSplit[i].substring(messageFirstIndex, messageLastIndex);

            messages.add(new Message(tmpUsername, tmpMessage));
        }
    }
}
