/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oskar.norberg
 */
public class SocketReceiver extends Controller implements Runnable {

    private Socket s;
    private DataInputStream in;
    private Thread t;

    public SocketReceiver(Model m, Socket s) {
        super(m);
        this.s = s;
        try {
            in = in = new DataInputStream(s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SocketReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                m.setReply(in.readUTF());
            } catch (IOException ex) {
                break;
            }
        }
    }
}
