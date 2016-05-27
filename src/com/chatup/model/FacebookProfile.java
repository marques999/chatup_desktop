package com.chatup.model;

import com.chatup.http.HttpMethod;
import com.chatup.http.HttpQuery;
import com.chatup.http.HttpRequest;

public class FacebookProfile extends HttpRequest
{
    public FacebookProfile(final String accessToken)
    {
	super(HttpMethod.GET, new HttpQuery(new SimplePair[]
	{
	    new SimplePair("fields", "name,email"),
	    new SimplePair("access_token", accessToken),
	}).toString());
    }
}