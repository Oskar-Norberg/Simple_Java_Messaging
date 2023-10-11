/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import javax.swing.JPanel;

/**
 *
 * @author oskar.norberg
 */
public abstract class Controller extends JPanel {

    Model m;

    public Controller(Model m) {
        this.m = m;
    }

    public Model getModel() {
        return m;
    }

}
