package com.chatup.model;

import com.chatup.http.HttpCommands;
import com.chatup.http.HttpFields;
import com.chatup.http.HttpMethod;
import com.chatup.http.HttpRequest;

import com.eclipsesource.json.Json;

public class CreateRoom extends HttpRequest
{
    public CreateRoom(final String userToken, final String roomName, final String roomPassword)
    {
        super(HttpMethod.PUT, roomPassword == null ?
            Json.object()
                .add(HttpCommands.CreateRoom, Json.object()
                .add(HttpFields.UserToken, userToken)
                .add(HttpFields.RoomName, roomName)).toString()
            :
            Json.object()
                .add(HttpCommands.CreateRoom, Json.object()
                .add(HttpFields.UserToken, userToken)
                .add(HttpFields.RoomName, roomName)
                .add(HttpFields.RoomPassword, roomPassword)).toString()
            );
    }
}