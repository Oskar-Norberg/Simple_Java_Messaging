/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author oskar.norberg
 */
public class LoginController extends Controller implements ActionListener {

    private JLabel lblServerIP, lblServerPort, lblUsername, lblPassword;
    private JTextField txtfServerIP, txtfServerPort, txtfUsername;
    private JPasswordField txtpPassword;
    private JButton btnCreate, btnLogin;

    public LoginController(Model m) {
        super(m);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c;
        setLayout(gridbag);

        JLabel lblLogin = new JLabel("Logga in");
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        gridbag.setConstraints(lblLogin, c);

        lblServerIP = new JLabel("ServerIP:");

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(lblServerIP, c);

        txtfServerIP = new JTextField();

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(txtfServerIP, c);

        lblServerPort = new JLabel("ServerPort:");

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(lblServerPort, c);

        txtfServerPort = new JTextField();

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(txtfServerPort, c);

        lblUsername = new JLabel("Username:");

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(lblUsername, c);

        txtfUsername = new JTextField();

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(txtfUsername, c);

        lblPassword = new JLabel("Password:");

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(lblPassword, c);

        txtpPassword = new JPasswordField();

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(txtpPassword, c);

        btnCreate = new JButton("Create");

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(btnCreate, c);

        btnLogin = new JButton("Login");

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        gridbag.setConstraints(btnLogin, c);

        add(lblLogin);

        add(lblServerIP);
        add(txtfServerIP);

        add(lblServerPort);
        add(txtfServerPort);

        add(lblUsername);
        add(txtfUsername);

        add(lblPassword);
        add(txtpPassword);

        add(btnCreate);
        add(btnLogin);

        btnCreate.addActionListener(this);
        btnLogin.addActionListener(this);

        txtfServerIP.setText("localhost");
        txtfServerPort.setText("5000");
        txtfUsername.setText("jorto");
        txtpPassword.setText("jorto");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String serverIP = txtfServerIP.getText();
        int serverPort = Integer.parseInt(txtfServerPort.getText());
        String username = txtfUsername.getText();
        String password = txtpPassword.getText();

        if (e.getSource() == btnCreate) {
            getModel().createAccount(serverIP, serverPort, username, password);
        } else if (e.getSource() == btnLogin) {
            getModel().login(serverIP, serverPort, username, password);
        }
    }

}
