/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author oskar.norberg
 */
public class MainFrame extends JFrame {

    private LoginController loginController;
    private MessageSendController messageSendController;
    private ChatView chatView;
    private Model model;
    private GridBagLayout gridbag;
    private RoomView roomView;
    private OnlineView onlineView;
    private AlertView alertView;

    public MainFrame() {
        model = new Model(this);
        setPreferredSize(new Dimension(1500, 850));

        gridbag = new GridBagLayout();
        GridBagConstraints c;
        setLayout(gridbag);

        loginController = new LoginController(model);
        alertView = new AlertView(model);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        gridbag.setConstraints(loginController, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 0.05;
        gridbag.setConstraints(alertView, c);

        add(loginController);
        add(alertView);

        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void loginSuccessful() {
        this.remove(loginController);
        loginController.revalidate();

        chatView = new ChatView(model);
        messageSendController = new MessageSendController(model);
        roomView = new RoomView(model);
        onlineView = new OnlineView(model);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.gridwidth = 1;
        c.weightx = 0.15;
        c.weighty = 1;
        gridbag.setConstraints(roomView, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        gridbag.setConstraints(chatView, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 0.1;
        gridbag.setConstraints(messageSendController, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.4;
        c.weighty = 1;
        gridbag.setConstraints(onlineView, c);

        add(roomView);
        add(chatView);
        add(messageSendController);
        add(onlineView);

        pack();
    }
}
