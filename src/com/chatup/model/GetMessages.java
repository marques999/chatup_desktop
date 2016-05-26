package com.chatup.model;

import com.chatup.http.HttpFields;
import com.chatup.http.HttpMethod;
import com.chatup.http.HttpQuery;
import com.chatup.http.HttpRequest;

public class GetMessages extends HttpRequest
{
    public GetMessages(int roomId, long messageTimestamp, final String userToken)
    {
	super(HttpMethod.GET, new HttpQuery(new SimplePair[]
	{
	    new SimplePair(HttpFields.RoomId, Integer.toString(roomId)),
	    new SimplePair(HttpFields.UserToken, userToken),
	    new SimplePair(HttpFields.Timestamp, Long.toString(messageTimestamp))
	}).toString());
    }
}