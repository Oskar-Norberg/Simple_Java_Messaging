/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Araragi
 */
public class MessageSendController extends Controller {

    private JTextField txtfNewMessage;
    private JButton btnSendMessage;

    public MessageSendController(Model m) {
        super(m);
        txtfNewMessage = new JTextField();
        btnSendMessage = new JButton("Send");
        setLayout(new GridLayout(1, 2));

        add(txtfNewMessage);
        add(btnSendMessage);

        btnSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.sendMessage(txtfNewMessage.getText());
                txtfNewMessage.setText("");
            }
        });
    }
}
