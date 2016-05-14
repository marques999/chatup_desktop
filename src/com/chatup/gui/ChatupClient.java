package com.chatup.gui;

import com.chatup.http.HttpCallback;
import com.chatup.http.HttpFields;
import com.chatup.http.MessageService;
import com.chatup.http.UserService;
import com.chatup.http.RoomService;

import com.chatup.model.Message;
import com.chatup.model.Room;
import com.chatup.model.RoomModel;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.awt.EventQueue;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ChatupClient
{
    private static ChatupClient instance;

    private ChatupClient()
    {
	try
	{
	    roomService = new RoomService(InetAddress.getLocalHost(), 8080);
	    userService = new UserService(InetAddress.getLocalHost(), 8080);
	}
	catch (UnknownHostException ex)
	{
	    Logger.getLogger(GuiMain.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    private MessageService messageService;
    private RoomService roomService;
    private UserService userService;
    private String sessionToken;
    private String sessionEmail;

    private RoomModel myRooms = new RoomModel();

    public RoomModel getRooms()
    {
	return myRooms;
    }

    public static ChatupClient getInstance()
    {
	if (instance == null)
	{
	    instance = new ChatupClient();
	}

	return instance;
    }

    public void getRooms(final HttpCallback actionCallback)
    {
	roomService.getRooms(actionCallback);
    }

    public boolean insertRooms(final JsonArray jsonArray)
    {
	boolean newRooms = false;

	for (final JsonValue jsonValue : jsonArray)
	{
	    if (jsonValue.isObject())
	    {
		JsonObject jsonObject = jsonValue.asObject();
		myRooms.insertRoom(
		    jsonObject.getInt(HttpFields.RoomId, -1),
		    jsonObject.getString(HttpFields.RoomName, null),
		    jsonObject.getString(HttpFields.RoomPassword, null),
		    jsonObject.getString(HttpFields.UserToken, null));
		newRooms = true;
	    }
	}

	return newRooms;
    }
   
    public String getUser()
    {
	return "marques999";
    }

    protected void setLogin(final String userEmail, final String userPassword)
    {
	sessionEmail = userEmail;
	sessionToken = userPassword;
    }

    public boolean userLogin(final String userEmail, final String userToken)
    {
	if (sessionEmail.equals(userEmail))
	{
	    sessionToken = userToken;
	}

	return true;
    }

    public void requestLogin(final String userEmail, final String userToken, final HttpCallback actionCallback)
    {
	setLogin(userEmail, userToken);
	userService.userLogin(userEmail, userToken, actionCallback);
    }

    public void disconnect(final HttpCallback actionCallback)
    {
	userService.userDisconnect(sessionEmail, sessionToken, actionCallback);
    }

    public boolean deleteRoom(int roomId)
    {
	return true;
    }

    public void joinRoom(int roomid, final String roomPassword, final HttpCallback actionCallback)
    {
	roomService.joinRoom(roomid, roomPassword, actionCallback);
    }

    public static void main(String args[])
    {
	try
	{
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
	catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
	{
	}

	EventQueue.invokeLater(() -> {
	    new GuiLogin().setVisible(true);
	});
    }

    public final boolean insertMessage(final Message paramMessage)
    {
	return true;
    }

    public final String getToken()
    {
	return sessionToken;
    }

    public boolean userDisconnect(String userEmail, String userToken)
    {
	return userEmail.equals(sessionEmail) && userToken.equals(sessionToken);
    }

    public boolean createRoom(String string, String string0)
    {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected final Room getRoom(int roomId)
    {
	return myRooms.getById(roomId);
    }
}