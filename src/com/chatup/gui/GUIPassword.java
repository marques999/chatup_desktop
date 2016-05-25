package com.chatup.gui;

import com.chatup.http.HttpFields;
import com.chatup.http.HttpResponse;
import com.chatup.model.Room;

import com.eclipsesource.json.JsonObject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.net.MalformedURLException;

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
    public GUIPassword(final Frame paramFrame, final Room paramRoom)
    {
        super(paramFrame);
        currentRoom = paramRoom;
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
	getRootPane().setDefaultButton(buttonSubmit);
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
	final String roomPassword = new String(textPassword.getPassword());

        chatupInstance.actionJoinRoom(currentRoom.getId(), roomPassword, (jsonValue) ->
        {
	    if (chatupInstance.validateResponse(this, jsonValue))
	    {
		final JsonObject jsonObject = chatupInstance.extractResponse(jsonValue);

		if (jsonObject == null)
		{
		    chatupInstance.showError(this, HttpResponse.EmptyResponse);
		}
		else
		{
		    final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		    if (ChatupClient.getInstance().validateToken(userToken))
		    {
			final String serverAddress = jsonObject.getString(HttpFields.ServerAddress, null);
			int serverPort = jsonObject.getInt(HttpFields.ServerPort, -1);

			if (serverAddress == null || serverPort < 0 || serverAddress.isEmpty())
			{
			    chatupInstance.showError(this, HttpResponse.MissingParameters);
			}
			else
			{
			    try
			    {
				GUIRoom guiRoom = new GUIRoom(currentRoom, serverAddress, serverPort);
				chatupInstance.insertRoom(currentRoom.getId(), guiRoom);
				guiRoom.setVisible(true);
			    }
			    catch (final MalformedURLException ex)
			    {
				chatupInstance.showError(this, HttpResponse.ServiceOffline);
			    }
			}
		    }
		    else
		    {
			chatupInstance.showError(this, HttpResponse.InvalidToken);
		    }
		}
	    }
	    
	    dispose();
        });
    }

    private final Room currentRoom;
    private final ButtonGroup buttonGroup1 = new ButtonGroup();
    private final JButton buttonSubmit = new JButton();
    private final JButton buttonCancel = new JButton();
    private final JLabel labelPrompt = new JLabel();
    private final JPanel panelButtons = new JPanel();
    private final JPanel panelContainer = new JPanel();
    private final JPasswordField textPassword = new JPasswordField();;
}