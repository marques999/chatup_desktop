package com.chatup.gui;

import com.chatup.http.HttpCallback;
import com.chatup.http.HttpResponse;
import com.chatup.http.UserService;
import com.chatup.http.RoomService;

import com.chatup.model.Room;
import com.chatup.model.RoomModel;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.awt.Component;
import java.awt.EventQueue;

import java.util.HashMap;

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
	    roomService = new RoomService(ChatupGlobals.ServerAddress, ChatupGlobals.ServerPort);
	    userService = new UserService(ChatupGlobals.ServerAddress, ChatupGlobals.ServerPort);
	    rooms = new HashMap<>();
	}
	catch (final Throwable ex)
	{
	    System.exit(1);
	}
    }
    
    public RoomService getRoomService()
    {
	return roomService;
    }

    boolean validateResponse(final Component parent, final JsonValue jsonValue)
    {
	boolean receivedError = false;

	if (jsonValue == null)
	{
	   showError(parent, HttpResponse.InvalidCommand);
	   receivedError = true;
	}
	else
	{
	    if (jsonValue.isObject())
	    {
		final JsonObject jsonObject = jsonValue.asObject();
		final String errorMessage = jsonObject.getString("error", null);

		if (errorMessage != null)
		{
		    showError(parent, HttpResponse.fromString(errorMessage));
		    receivedError = true;
		}
	    }
	}
            
	return !receivedError;
    }

    protected void showError(final Component parent, final HttpResponse httpResponse)
    {
	JOptionPane.showMessageDialog(parent, HttpResponse.getErrorMessage(httpResponse), "Chatup Client : ERROR", JOptionPane.ERROR_MESSAGE);
    }

    protected void showError(final Component parent, final String errorMessage)
    {
	JOptionPane.showMessageDialog(parent, errorMessage, "Chatup Client : ERROR", JOptionPane.ERROR_MESSAGE);
    }

    protected JsonArray extractArray(final JsonValue jsonObject)
    {
	return jsonObject.isArray() ? jsonObject.asArray() : null;
    }

    protected JsonObject extractResponse(final JsonValue jsonObject)
    {
	return jsonObject.isObject() ? jsonObject.asObject() : null;
    }

    private HashMap<Integer, GUIRoom> rooms;
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

    public HttpResponse responseGetRooms(final JsonArray jsonArray)
    {
	if (myRooms.insertRooms(jsonArray))
	{
	    return HttpResponse.SuccessResponse;
	}

	return HttpResponse.OperationFailed;
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

    public HttpResponse validateUser(String userEmail, String userToken)
    {
	if (userEmail.equals(sessionEmail) && userToken.equals(sessionToken))
	{
	    return HttpResponse.SuccessResponse;
	}

	return HttpResponse.OperationFailed;
    }

    public void actionGetRooms(final HttpCallback httpCallback)
    {
	roomService.getRooms(sessionToken, httpCallback);
    }

    public void actionUserLogin(final String userEmail, final String userToken, final HttpCallback actionCallback)
    {
	setLogin(userEmail, userToken);
	userService.userLogin(userEmail, userToken, actionCallback);
    }

    public void actionUserDisconnect(final HttpCallback httpCallback)
    {
	userService.userDisconnect(sessionEmail, sessionToken, httpCallback);
    }

    public void actionLeaveRoom(int roomId, final HttpCallback httpCallback)
    {
	roomService.leaveRoom(roomId, httpCallback);
    }

    public void actionCreateRoom(final String roomName, final String roomPassword, final HttpCallback httpCallback)
    {
	roomService.createRoom(roomName, roomPassword, httpCallback);
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

    public final String getToken()
    {
	return sessionToken;
    }

    public int createRoom(final JsonObject jsonObject)
    {
	return myRooms.insertRoom(jsonObject);
    }

    protected final Room getRoom(int roomId)
    {
	return myRooms.getById(roomId);
    }

    void insertRoom(int id, GUIRoom currentRoom)
    {
	rooms.put(id, currentRoom);
    }

    public boolean validateToken(final String userToken)
    {
	return userToken != null && sessionToken.equals(userToken);
    }
}