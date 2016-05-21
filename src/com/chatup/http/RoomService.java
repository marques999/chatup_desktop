package com.chatup.http;

import com.chatup.gui.ChatupClient;
import com.chatup.gui.ChatupGlobals;

import com.chatup.model.CreateRoom;
import com.chatup.model.LeaveRoom;
import com.chatup.model.GetRooms;
import com.chatup.model.JoinRoom;

import java.net.InetAddress;
import java.net.MalformedURLException;

public class RoomService extends HttpService
{
	public RoomService(final InetAddress serviceAddress, int servicePort) throws MalformedURLException
	{
		super(serviceAddress, ChatupGlobals.RoomServiceUrl, servicePort);
	}

	@Override
	protected HttpResponse responseHandler(final String httpMethod, final String httpParameters)
	{
		return new RoomServiceHandler(httpMethod, httpParameters).processRequest();
	}

	public void getRooms(final HttpCallback actionCallback)
	{
		GET(new GetRooms(), actionCallback);
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
		POST(new JoinRoom(roomId, ChatupClient.getInstance().getToken(), roomPassword), actionCallback);
	}
}