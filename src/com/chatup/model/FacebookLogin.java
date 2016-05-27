package com.chatup.model;

import com.chatup.http.HttpMethod;
import com.chatup.http.HttpRequest;

public class FacebookLogin extends HttpRequest
{
    public FacebookLogin(final String applicationToken, final String requestScope)
    {
	super(HttpMethod.POST, "access_token=" + applicationToken + "&scope=" + requestScope);
    }
}