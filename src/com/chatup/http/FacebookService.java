package com.chatup.http;

import com.chatup.gui.ChatupGlobals;
import com.chatup.model.FacebookProfile;

import java.net.MalformedURLException;

public class FacebookService extends HttpService
{
    public FacebookService() throws MalformedURLException
    {
	super(ChatupGlobals.FacebookGraphUrl, "v2.3/me", -1);
    }

    public void requestInformation(final String accessToken, final HttpCallback actionCallback)
    {
	GET(new FacebookProfile(accessToken), 0, actionCallback);
    }
}