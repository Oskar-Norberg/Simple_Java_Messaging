/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package slutprojekt;

import java.util.Scanner;

/**
 *
 * @author oskar.norberg
 */
public class SlutprojektServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Server();
        Scanner sc = new Scanner(System.in); //Press any key to close server
        System.out.println("PRESS ANY KEY IN THE TERMINAL TO CLOSE THE SERVER. DO NOT FORCE SHUTDOWN. IT WILL NOT SAVE");
        sc.nextLine();
        System.exit(0);
    }
}
