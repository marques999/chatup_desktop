package com.chatup.model;

import com.chatup.http.HttpFields;
import com.chatup.http.HttpMethod;
import com.chatup.http.HttpQuery;
import com.chatup.http.HttpRequest;

public class Heartbeat extends HttpRequest
{
    public Heartbeat(final String userToken)
    {
	super(HttpMethod.GET, new HttpQuery(new SimplePair[]
	{
	    new SimplePair(HttpFields.UserToken, userToken),
	}).toString());
    }
}