package com.chatup.model;

import com.chatup.http.HttpMethod;
import com.chatup.http.HttpRequest;

public class FacebookStatus extends HttpRequest
{
    public FacebookStatus(final String applicationToken, final String accessCode)
    {
	super(HttpMethod.POST, "access_token=" + applicationToken + "&code=" + accessCode);
    }
}