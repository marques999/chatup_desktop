package com.chatup.gui;

import com.chatup.http.HttpResponse;
import com.chatup.model.Room;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class GUIPassword extends JDialog
{
    private int roomId;

    public GUIPassword(final Frame paramFrame, int paramId)
    {
        super(paramFrame);

        final JButton buttonSubmit = new JButton();
        final JButton buttonCancel = new JButton();
        final ButtonGroup buttonGroup1 = new ButtonGroup();
        final JLabel labelPrompt = new JLabel();
        final JPanel panelButtons = new JPanel();
        final JPanel panelContainer = new JPanel();

        roomId = paramId;
        textPassword = new JPasswordField();
        panelContainer.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panelContainer.setLayout(new BorderLayout(0, 10));
        labelPrompt.setFont(new Font("Tahoma", 1, 11));
        labelPrompt.setHorizontalAlignment(SwingConstants.CENTER);
        labelPrompt.setText("Please enter the room password");
        panelContainer.add(labelPrompt, BorderLayout.PAGE_START);
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 4, 0));
        buttonSubmit.setText("Validate");
        buttonGroup1.add(buttonSubmit);
        buttonSubmit.addActionListener(this::buttonSubmitActionPerformed);
        panelButtons.add(buttonSubmit);
        buttonCancel.setText("Cancel");
        buttonGroup1.add(buttonCancel);
        buttonCancel.addActionListener(this::buttonCancelActionPerformed);
        panelButtons.add(buttonCancel);
        panelContainer.add(panelButtons, BorderLayout.PAGE_END);
        panelContainer.add(textPassword, BorderLayout.CENTER);
        getContentPane().add(panelContainer, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        setTitle("Private Room");
        pack();
        setLocationRelativeTo(paramFrame);
    }

    private void buttonCancelActionPerformed(final ActionEvent evt)
    {
        dispose();
    }

    private void buttonSubmitActionPerformed(final ActionEvent evt)
    {
        final ChatupClient chatupInstance = ChatupClient.getInstance();

        chatupInstance.actionJoinRoom(roomId, new String(textPassword.getPassword()), (rv) ->
        {
            final Room selectedRoom = chatupInstance.getRoom(roomId);

            if (rv == HttpResponse.SuccessResponse)
            {
                if (selectedRoom != null)
                {
                    new GUIRoom(selectedRoom).setVisible(true);
                }
            }
            else
            {
                chatupInstance.showError(this, rv);
            }
        });
    }

    private final JPasswordField textPassword;
}