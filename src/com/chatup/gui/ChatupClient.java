package com.chatup.gui;

import com.chatup.http.HttpCallback;
import com.chatup.http.HttpResponse;
import com.chatup.http.MessageService;
import com.chatup.http.UserService;
import com.chatup.http.RoomService;
import com.chatup.model.Message;
import com.chatup.model.Room;
import com.chatup.model.RoomModel;
import com.eclipsesource.json.JsonArray;

import java.awt.Component;
import java.awt.EventQueue;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
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
	catch (MalformedURLException | UnknownHostException ex)
	{
	     Logger.getLogger(GUIMain.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    protected void showError(final Component parent, final HttpResponse httpResponse)
    {
	JOptionPane.showMessageDialog(parent, HttpResponse.getErrorMessage(httpResponse), "Chatup Client : ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
        protected void showError(final Component parent, final String errorMessage)
    {
	JOptionPane.showMessageDialog(parent, errorMessage, "Chatup Client : ERROR", JOptionPane.ERROR_MESSAGE);
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

    public boolean responseGetRooms(final JsonArray jsonArray)
    {
	return myRooms.insertRooms(jsonArray);
    }
   
    public String getUser()
    {
	return "marques999";
    }

    public void setLogin(final String userEmail, final String userPassword)
    {
	sessionEmail = userEmail;
	sessionToken = userPassword;
    }

    public boolean responseLogin(final String userEmail, final String userToken)
    {
	if (sessionEmail.equals(userEmail))
	{
	    sessionToken = userToken;
	}

	return true;
    }

    public void actionLogin(final String userEmail, final String userToken, final HttpCallback actionCallback)
    {
	setLogin(userEmail, userToken);
	userService.userLogin(userEmail, userToken, actionCallback);
    }

    public void actionGetRooms(final HttpCallback httpCallback)
    {
	roomService.getRooms(httpCallback);
    }

    public void actionDisconnect(final HttpCallback httpCallback)
    {
	userService.userDisconnect(sessionEmail, sessionToken, httpCallback);
    }

    public void actionLeaveRoom(int roomId, final HttpCallback httpCallback)
    {
	roomService.leaveRoom(roomId, httpCallback);
    }
    
    public void actionCreateRoom(final String roomName, final String roomPassword)
    {
	roomService.createRoom(roomName, roomPassword, (rv) ->
	{
	});
    }

    public void actionJoinRoom(int roomid, final String roomPassword, final HttpCallback httpCallback)
    {
	roomService.joinRoom(roomid, roomPassword, httpCallback);
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
	    new GUILogin().setVisible(true);
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
	throw new UnsupportedOperationException("Not supported yet.");
    }

    protected final Room getRoom(int roomId)
    {
	return myRooms.getById(roomId);
    }
}