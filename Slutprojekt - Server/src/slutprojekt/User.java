/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.util.ArrayList;
import static slutprojekt.Model.STRING_SEPARATOR;
import static slutprojekt.Model.STRING_SEPARATOR_2;
import static slutprojekt.Model.STRING_SEPARATOR_3;

/**
 *
 * @author oskar.norberg
 */
public class User {

    public static int amtUsers = 0;
    private int id;
    private String username, password;
    private ArrayList<Room> joinedRooms;

    public User(String username, String password, Room general) {
        id = amtUsers;
        amtUsers++;
        this.username = username;
        this.password = password;
        joinedRooms = new ArrayList<>();
        joinedRooms.add(general);
    }

    public void joinRoom(Room r) throws UserIsAlreadyAMemberOfThatRoomException {
        boolean alreadyAMember = false;
        for (Room ro : joinedRooms) {
            if (ro == r) {
                alreadyAMember = true;
                break;
            }
        }
        if (alreadyAMember) {
            throw new UserIsAlreadyAMemberOfThatRoomException();
        } else {
            joinedRooms.add(r);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getID() {
        return id;
    }

    public ArrayList<Room> getJoinedRooms() {
        return joinedRooms;
    }

    public String toString() {
        String tmp = "";
        for (Room r : joinedRooms) {
            tmp += r.getID() + ", ";
        }
        return STRING_SEPARATOR + username + STRING_SEPARATOR + STRING_SEPARATOR_2 + password + STRING_SEPARATOR_2 + STRING_SEPARATOR_3 + tmp + STRING_SEPARATOR_3;
    }
}
