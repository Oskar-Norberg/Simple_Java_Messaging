/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oskar.norberg
 */
public class ConnectionListener extends Controller implements Runnable {

    private Thread t;
    private ServerSocket listeningSocket;

    public ConnectionListener(Model m, int port) {
        super(m);
        try {
            listeningSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        t = new Thread(this);
        t.start();
    }

    public Thread getThread() {
        return t;
    }

    public void run() {
        while (!t.isInterrupted()) {
            try {
                getModel().addSocket(new SocketManager(m, listeningSocket.accept()));
            } catch (IOException ex) {
                Logger.getLogger(ConnectionListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
