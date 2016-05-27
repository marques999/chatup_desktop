package com.chatup.http;

import com.chatup.gui.ChatupGlobals;
import com.chatup.model.FacebookLogin;
import com.chatup.model.FacebookStatus;

import java.net.MalformedURLException;

public class LoginService extends HttpService
{
    public LoginService(final String serviceEndpoint) throws MalformedURLException
    {
	super(ChatupGlobals.FacebookGraphUrl, "v2.6/device/" + serviceEndpoint, -1);
    }
    
    public void requestLogin(final HttpCallback actionCallback)
    {
	POST(new FacebookLogin(ChatupGlobals.FacebookToken, ChatupGlobals.FacebookPermissions), actionCallback);
    }
    
    public void checkStatus(final String accessCode, final HttpCallback actionCallback)
    {
	POST(new FacebookStatus(ChatupGlobals.FacebookToken, accessCode), actionCallback);
    }
}