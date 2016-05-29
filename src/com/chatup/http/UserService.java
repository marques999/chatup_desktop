package com.chatup.http;

import com.chatup.gui.ChatupGlobals;

import com.chatup.model.UserDisconnect;
import com.chatup.model.UserLogin;

public class UserService extends HttpService
{
    public UserService(final String serviceAddress, int servicePort) throws Exception
    {
	super(serviceAddress, ChatupGlobals.UserService, servicePort);
    }

    public void userLogin(final String userToken, final HttpCallback actionCallback)
    {
	POST(new UserLogin(userToken), actionCallback);
    }

    public void userDisconnect(final String userEmail, final String userToken, final HttpCallback actionCallback)
    {
	POST(new UserDisconnect(userEmail, userToken), actionCallback);
    }
}