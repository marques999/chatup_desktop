package com.chatup.http;

import com.chatup.gui.ChatupGlobals;
import com.chatup.model.FacebookProfile;

public class FacebookService extends HttpService
{
    public FacebookService() throws Exception
    {
	super(ChatupGlobals.FacebookService, "v2.3/me", -1);
    }

    public void requestInformation(final String accessToken, final HttpCallback actionCallback)
    {
	GET(new FacebookProfile(accessToken), 0, actionCallback);
    }
}