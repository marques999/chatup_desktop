package com.chatup.model;

import com.chatup.http.HttpCommands;
import com.chatup.http.HttpFields;
import com.chatup.http.HttpMethod;
import com.chatup.http.HttpRequest;

import com.eclipsesource.json.Json;

public class SendMessage extends HttpRequest
{
    public SendMessage(final Message paramMessage)
    {
	super(HttpMethod.POST, Json.object()
	    .add(HttpCommands.SendMessage, Json.object()
	    .add(HttpFields.RoomId, paramMessage.getRoomId())
	    .add(HttpFields.UserToken, paramMessage.getSender())
	    .add(HttpFields.UserMessage, paramMessage.getContents())).toString());
    }
}