package com.chatup.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUICreateRoom extends JDialog
{
    public GUICreateRoom(final Frame paramFrame)
    {
        super(paramFrame);

        final JPanel panelButtons = new JPanel();
        final JPanel panelForm = new JPanel();
        final JLabel labelName = new JLabel();
        final JLabel labelPassword = new JLabel();
        final JButton buttonCancel = new JButton();
        final JButton buttonCreate = new JButton();

        inputName = new JTextField();
        inputPassword = new JPasswordField();
        panelButtons.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        buttonCreate.setText("Create");
        buttonCreate.addActionListener(this::buttonCreateActionPerformed);
        panelButtons.add(buttonCreate);
        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(this::buttonCancelActionPerformed);
        panelButtons.add(buttonCancel);
        getContentPane().add(panelButtons, BorderLayout.PAGE_END);
        panelForm.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
        panelForm.setLayout(new GridLayout(2, 2, 0, 8));
        labelName.setText("Room Name");
        panelForm.add(labelName);
        inputName.setColumns(15);
        panelForm.add(inputName);
        labelPassword.setText("Room Password");
        panelForm.add(labelPassword);
        inputPassword.setColumns(15);
        panelForm.add(inputPassword);
        getContentPane().add(panelForm, BorderLayout.CENTER);
        getRootPane().setDefaultButton(buttonCreate);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create Room");
        setModal(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(paramFrame);
    }

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt)
    {
        dispose();
    }

    private void buttonCreateActionPerformed(java.awt.event.ActionEvent evt)
    {
        final ChatupClient chatupInstance = ChatupClient.getInstance();
        final String roomName = inputName.getText();

        if (roomName.isEmpty())
        {
            chatupInstance.showError(this, "Please enter a name for the room you want to create!");
        }
        else
        {
            final char[] roomPassword = inputPassword.getPassword();

            if (roomPassword.length == 0)
            {
                chatupInstance.actionCreateRoom(inputName.getText(), null);
            }
            else
            {
                chatupInstance.actionCreateRoom(inputName.getText(), new String(roomPassword));
            }
        }
    }

    private final JTextField inputName;
    private final JPasswordField inputPassword;
}