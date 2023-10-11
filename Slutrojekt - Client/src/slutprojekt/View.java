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
public abstract class View extends JPanel {

    Model m;

    public View(Model m) {
        m.registerView(this);
        this.m = m;
    }

    public void update() {

    }

}
