/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

/**
 *
 * @author oskar.norberg
 */
public class Server {

    private static final int PORT = 5000;
    private Model m;

    public Server() {
        m = new Model();
        ConnectionListener conLis = new ConnectionListener(m, PORT);
    }
}
