package com.chatup.http;

import com.chatup.gui.ChatupGlobals;

import com.chatup.model.UserDisconnect;
import com.chatup.model.UserLogin;

import java.net.MalformedURLException;

public class UserService extends HttpService
{
    public UserService(final String serviceAddress, int servicePort) throws MalformedURLException
    {
	super(serviceAddress, ChatupGlobals.UserServiceUrl, servicePort);
    }

    public void userLogin(final String userEmail, final String userToken, final HttpCallback actionCallback)
    {
	POST(new UserLogin(userEmail, userToken), actionCallback);
    }

    public void userDisconnect(final String userEmail, final String userToken, final HttpCallback actionCallback)
    {
	POST(new UserDisconnect(userEmail, userToken), actionCallback);
    }
}