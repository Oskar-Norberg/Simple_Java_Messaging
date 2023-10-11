/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Araragi
 */
public class OnlineView extends View {

    private JTextArea txtaUsers;
    private JScrollPane txtsScrollPane;

    public OnlineView(Model m) {
        super(m);
        txtaUsers = new JTextArea();
        txtaUsers.setEditable(false);
        txtsScrollPane = new JScrollPane(txtaUsers);
        setLayout(new GridLayout(1, 1));
        add(txtsScrollPane);
        setBorder(new TitledBorder("Online Users:"));
    }

    @Override
    public void update() {
        String[] tmpArr = m.getOnlineUsers();
        if (tmpArr != null) {
            String tmp = "";
            for (String s : tmpArr) {
                tmp += s + "\n";
            }
            txtaUsers.setText(tmp);
        }
    }

}
