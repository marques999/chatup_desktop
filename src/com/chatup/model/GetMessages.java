package com.chatup.model;

import com.chatup.http.HttpFields;
import com.chatup.http.HttpQuery;
import com.chatup.http.HttpRequest;

public class GetMessages extends HttpRequest
{
    public GetMessages(int paramId, final String paramToken)
    {
	super("GET", new HttpQuery(new SimplePair[]
	{
	    new SimplePair(HttpFields.RoomId, Integer.toString(paramId)),
	    new SimplePair(HttpFields.UserToken, paramToken),
	}).toString());
    }
}