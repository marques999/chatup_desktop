package com.chatup.model;

import com.chatup.http.HttpCommands;
import com.chatup.http.HttpFields;
import com.chatup.http.HttpMethod;
import com.chatup.http.HttpRequest;

import com.eclipsesource.json.Json;

public class JoinRoom extends HttpRequest
{
    public JoinRoom(int roomId, final String roomPassword, final String userToken)
    {
	super(HttpMethod.POST, roomPassword == null ?
	    Json.object()
		.add(HttpCommands.JoinRoom, Json.object()
		.add(HttpFields.RoomId, roomId)
		.add(HttpFields.UserToken, userToken)).toString()
	    :
	    Json.object()
		.add(HttpCommands.JoinRoom, Json.object()
		.add(HttpFields.RoomId, roomId)
		.add(HttpFields.RoomPassword, roomPassword)
		.add(HttpFields.UserToken, userToken)).toString()
	);
    }
}