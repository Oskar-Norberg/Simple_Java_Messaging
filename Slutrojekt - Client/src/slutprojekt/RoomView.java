/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Araragi
 */
public class RoomView extends View {

    private JList listRooms;
    private JTextField txtfRoomName;
    private JTextField txtfRoomPassword;

    private DefaultListModel defaultListModel;

    private JButton btnJoinRoom;
    private JButton btnCreateRoom;
    private JScrollPane txtsScrollPane;

    public RoomView(Model m) {
        super(m);
        listRooms = new JList();
        defaultListModel = new DefaultListModel();
        listRooms.setModel(defaultListModel);
        txtsScrollPane = new JScrollPane(listRooms);
        txtfRoomName = new JTextField();
        txtfRoomPassword = new JTextField();
        btnJoinRoom = new JButton("Join Room");
        btnCreateRoom = new JButton("Create room");

        setBorder(new TitledBorder(m.getCurrentUser() + "'s rooms"));

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c;
        setLayout(gridbag);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 1;
        gridbag.setConstraints(txtsScrollPane, c);

        JLabel lblRoomName = new JLabel("Room Name");

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0.05;
        gridbag.setConstraints(lblRoomName, c);

        JLabel lblRoomPassword = new JLabel("Room Password (leave blank for none)");

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0.05;
        gridbag.setConstraints(lblRoomPassword, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0.05;
        gridbag.setConstraints(txtfRoomName, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0.05;
        gridbag.setConstraints(txtfRoomPassword, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0.05;
        gridbag.setConstraints(btnCreateRoom, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0.05;
        gridbag.setConstraints(btnJoinRoom, c);

        add(txtsScrollPane);
        add(lblRoomName);
        add(lblRoomPassword);
        add(txtfRoomName);
        add(txtfRoomPassword);
        add(btnCreateRoom);
        add(btnJoinRoom);

        listRooms.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                m.setRoomName((String) listRooms.getSelectedValue());
            }
        });

        btnCreateRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.createRoom(txtfRoomName.getText(), txtfRoomPassword.getText());
                clearTextAreas();
            }

        });

        btnJoinRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.joinRoom(txtfRoomName.getText(), txtfRoomPassword.getText());
                clearTextAreas();
            }

        });
    }

    private void clearTextAreas() {
        txtfRoomName.setText("");
        txtfRoomPassword.setText("");
    }

    @Override
    public void update() {
        String[] tmp = m.getRooms();
        if (tmp != null) {
            int tmpSelected = -1;
            defaultListModel.clear();
            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i].equals(m.getRoomName())) {
                    tmpSelected = i;
                }
                defaultListModel.addElement(tmp[i]);
            }
            listRooms.setSelectedIndex(tmpSelected);
        }
    }
}
