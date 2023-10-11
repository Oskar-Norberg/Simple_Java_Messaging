/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.awt.GridLayout;
import javax.swing.JLabel;

/**
 *
 * @author Araragi
 */
public class AlertView extends View {

    private JLabel lblAlert;

    public AlertView(Model m) {
        super(m);
        lblAlert = new JLabel();
        setLayout(new GridLayout(1,1));
        add(lblAlert);
    }

    @Override
    public void update() {
        lblAlert.setText(m.getAlert());
    }
}
