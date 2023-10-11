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
public class ChatView extends View {

    private JTextArea txtaMessages;
    private JScrollPane txtsScrollPane;

    public ChatView(Model m) {
        super(m);
        txtaMessages = new JTextArea();
        txtsScrollPane = new JScrollPane(txtaMessages);
        txtaMessages.setEditable(false);
        setLayout(new GridLayout(1, 1));
        setBorder(new TitledBorder(""));
        add(txtsScrollPane);
    }

    @Override
    public void update() {
        setBorder(new TitledBorder(m.getRoomName()));
        txtaMessages.setText(m.getMessages());
        txtaMessages.setCaretPosition(txtaMessages.getDocument().getLength());
    }
}
