package com.chatup.model;

import com.chatup.http.HttpCommands;
import com.chatup.http.HttpFields;
import com.chatup.http.HttpMethod;
import com.chatup.http.HttpRequest;

import com.eclipsesource.json.Json;

public class LeaveRoom extends HttpRequest
{
    public LeaveRoom(int roomId, final String userToken)
    {
        super(HttpMethod.DELETE, Json.object()
            .add(HttpCommands.LeaveRoom, Json.object()
            .add(HttpFields.RoomId, roomId)
            .add(HttpFields.UserToken, userToken)).toString());
    }
}