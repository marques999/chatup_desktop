package com.chatup.gui;

import com.chatup.http.HttpFields;
import com.chatup.http.HttpResponse;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

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

    private void buttonCancelActionPerformed(final ActionEvent paramEvent)
    {
	dispose();
    }

    private void createRoom(final JsonValue jsonValue)
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	if (!chatupInstance.jsonError(this, jsonValue))
	{
	    final JsonObject jsonObject = chatupInstance.extractResponse(jsonValue);

	    if (jsonObject == null)
	    {
		chatupInstance.showError(this, HttpResponse.EmptyResponse);
	    }
	    else
	    {
		final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		if (userToken == null || userToken.isEmpty())
		{
		    chatupInstance.showError(this, HttpResponse.MissingParameters);
		}
		else if (ChatupClient.getInstance().validateToken(userToken))
		{
		    chatupInstance.createRoom(jsonObject);
		}
		else
		{
		    chatupInstance.showError(this, HttpResponse.InvalidToken);
		}
	    }
	}

	dispose();
    }

    private void buttonCreateActionPerformed(final ActionEvent paramEvent)
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
		chatupInstance.actionCreateRoom(roomName, null, this::createRoom);
	    }
	    else
	    {
		chatupInstance.actionCreateRoom(roomName, new String(roomPassword), this::createRoom);
	    }
	}
    }
    private final JPanel panelButtons = new JPanel();
    private final JPanel panelForm = new JPanel();
    private final JLabel labelName = new JLabel();
    private final JLabel labelPassword = new JLabel();
    private final JButton buttonCancel = new JButton();
    private final JButton buttonCreate = new JButton();
    private final JTextField inputName = new JTextField();
    private final JPasswordField inputPassword = new JPasswordField();
}