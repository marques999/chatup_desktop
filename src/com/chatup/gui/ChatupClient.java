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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.Base64;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ChatupClient
{
    private File propertiesFile;
    private Properties propertiesObject;
    private HashMap<Integer, GUIRoom> rooms;
    private UserService userService;

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

    //--------------------------------------------------------------------------

    private static ChatupClient instance;

    public static ChatupClient getInstance()
    {
	if (instance == null)
	{
	    instance = new ChatupClient();
	}

	return instance;
    }

    //--------------------------------------------------------------------------

    private void initializePreferences()
    {
	if (propertiesObject == null)
	{
	    propertiesObject = new Properties();
	}

	if (propertiesFile == null)
	{
	    propertiesFile = new File(ChatupGlobals.PreferencesFilename);
	}
    }

    private boolean readPreferences()
    {
	initializePreferences();

	try (final FileInputStream fin = new FileInputStream(propertiesFile))
	{
	    propertiesObject.load(fin);
	    sessionEmail = getDecodedString(propertiesObject.getProperty(ChatupGlobals.FieldUserEmail, null));
	    sessionToken = getDecodedString(propertiesObject.getProperty(ChatupGlobals.FieldUserToken, null));
	}
	catch (final IOException ex)
	{
	    return false;
	}

	return true;
    }

    boolean savePreferences()
    {
	initializePreferences();

	try (final FileOutputStream fout = new FileOutputStream(propertiesFile))
	{
	    propertiesObject.setProperty(ChatupGlobals.FieldUserEmail, getEncodedString(sessionEmail));
	    propertiesObject.setProperty(ChatupGlobals.FieldUserToken, getEncodedString(sessionToken));
	    propertiesObject.store(fout, "chatup");
	}
	catch (final IOException ex)
	{
	    return false;
	}

	return true;
    }

    //--------------------------------------------------------------------------

    private String getEncodedString(final String paramInput)
    {
	return Base64.getEncoder().encodeToString(paramInput.getBytes(StandardCharsets.UTF_8));
    }

    private String getDecodedString(final String paramInput)
    {
	return new String(Base64.getDecoder().decode(paramInput));
    }

    //--------------------------------------------------------------------------

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

    boolean validateToken(final String userToken)
    {
	return userToken != null && sessionToken.equals(userToken);
    }

    final HttpResponse validateUser(String userEmail, String userToken)
    {
	if (userEmail.equals(sessionEmail) && userToken.equals(sessionToken))
	{
	    return HttpResponse.SuccessResponse;
	}

	return HttpResponse.AuthenticationFailed;
    }

    //--------------------------------------------------------------------------

    void showError(final Component parent, final HttpResponse httpResponse)
    {
	JOptionPane.showMessageDialog(parent, HttpResponse.getErrorMessage(httpResponse), "Chatup Client : ERROR", JOptionPane.ERROR_MESSAGE);
    }

    void showError(final Component parent, final String errorMessage)
    {
	JOptionPane.showMessageDialog(parent, errorMessage, "Chatup Client : ERROR", JOptionPane.ERROR_MESSAGE);
    }

    //--------------------------------------------------------------------------

    final JsonArray extractArray(final JsonValue jsonObject)
    {
	return jsonObject.isArray() ? jsonObject.asArray() : null;
    }

    final JsonObject extractResponse(final JsonValue jsonObject)
    {
	return jsonObject.isObject() ? jsonObject.asObject() : null;
    }

    //--------------------------------------------------------------------------

    private RoomService roomService;

    public RoomService getRoomService()
    {
	return roomService;
    }

    //--------------------------------------------------------------------------

    private String sessionToken;

    public final String getToken()
    {
	return sessionToken;
    }

    public void setLogin(final String userId, final String userEmail)
    {
	sessionToken = userId;
	sessionEmail = userEmail;
    }

    //--------------------------------------------------------------------------

    private String sessionEmail;

    public final String getEmail()
    {
	return sessionEmail;
    }

    //--------------------------------------------------------------------------

    private RoomModel myRooms = new RoomModel();

    public RoomModel getRooms()
    {
	return myRooms;
    }

    //--------------------------------------------------------------------------

    public HttpResponse responseGetRooms(final JsonArray jsonArray)
    {
	if (myRooms.insertRooms(jsonArray))
	{
	    return HttpResponse.SuccessResponse;
	}

	return HttpResponse.OperationFailed;
    }

    //--------------------------------------------------------------------------

    public void actionUserLogin(final String userToken, final HttpCallback actionCallback)
    {
	userService.userLogin(userToken, actionCallback);
    }

    public void actionUserDisconnect(final HttpCallback httpCallback)
    {
	userService.userDisconnect(sessionEmail, sessionToken, httpCallback);
    }

    //--------------------------------------------------------------------------

    public void actionCreateRoom(final String roomName, final String roomPassword, final HttpCallback httpCallback)
    {
	roomService.createRoom(roomName, roomPassword, httpCallback);
    }

    public void actionGetRooms(final HttpCallback httpCallback)
    {
	roomService.getRooms(sessionToken, httpCallback);
    }

    public void actionJoinRoom(int roomid, final String roomPassword, final HttpCallback httpCallback)
    {
	roomService.joinRoom(roomid, roomPassword, httpCallback);
    }

    public void actionLeaveRoom(int roomId, final HttpCallback httpCallback)
    {
	roomService.leaveRoom(roomId, httpCallback);
    }

    //--------------------------------------------------------------------------

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
	if (rooms.containsKey(id)) {
	    rooms.remove(id).dispose();
	}
	
	rooms.put(id, currentRoom);
    }

    //--------------------------------------------------------------------------

    public static void main(String args[])
    {
	try
	{
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
	catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
	{
	    ChatupGlobals.abort(ex);
	}

	EventQueue.invokeLater(() -> {
	    ChatupClient.getInstance();
	    new GUILogin().setVisible(true);
	});
    }
}