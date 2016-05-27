package com.chatup.http;

import com.chatup.gui.ChatupClient;
import com.chatup.gui.ChatupGlobals;

import com.chatup.model.CreateRoom;
import com.chatup.model.LeaveRoom;
import com.chatup.model.GetRooms;
import com.chatup.model.JoinRoom;

import java.net.MalformedURLException;

public class RoomService extends HttpService
{
    public RoomService(final String serviceAddress, int servicePort) throws MalformedURLException
    {
	super("http://" + serviceAddress, ChatupGlobals.RoomServiceUrl, servicePort);
    }

    public void getRooms(final String userToken, final HttpCallback actionCallback)
    {
	GET(new GetRooms(userToken), 0, actionCallback);
    }

    public void createRoom(final String roomName, final String roomPassword, final HttpCallback actionCallback)
    {
	POST(new CreateRoom(ChatupClient.getInstance().getToken(), roomName, roomPassword), actionCallback);
    }

    public void leaveRoom(int roomId, final HttpCallback actionCallback)
    {
	POST(new LeaveRoom(roomId, ChatupClient.getInstance().getToken()), actionCallback);
    }

    public void joinRoom(int roomId, final String roomPassword, final HttpCallback actionCallback)
    {
	POST(new JoinRoom(roomId, roomPassword, ChatupClient.getInstance().getToken()), actionCallback);
    }
}