package com.chatup.http;

import com.chatup.gui.ChatupGlobals;

import com.chatup.model.UserDisconnect;
import com.chatup.model.UserLogin;

import java.net.InetAddress;
import java.net.MalformedURLException;

public class UserService extends HttpService
{
	public UserService(final InetAddress serviceAddress, int servicePort) throws MalformedURLException
	{
		super(serviceAddress, ChatupGlobals.UserServiceUrl, servicePort);
	}

	@Override
	protected HttpResponse responseHandler(final String httpMethod, final String httpParameters)
	{
		return new UserServiceHandler(httpMethod, httpParameters).processRequest();
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