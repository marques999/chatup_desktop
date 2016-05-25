package com.chatup.http;

import com.chatup.gui.ChatupClient;
import com.chatup.gui.ChatupGlobals;

import com.chatup.model.Heartbeat;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.net.MalformedURLException;

public class HeartbeatService extends HttpService
{
    public HeartbeatService(final String serviceAddress, int servicePort) throws MalformedURLException
    {
	super(serviceAddress, ChatupGlobals.HeartbeatServiceUrl, servicePort);
    }

    public void requestHeartbeat(int roomId, final HttpCallback actionCallback)
    {
	GET(new Heartbeat(ChatupClient.getInstance().getToken()), actionCallback);
    }

       private final HttpResponse validateResponse(final JsonValue jsonValue)
    {
	if (jsonValue == null || !jsonValue.isObject())
	{
	    return HttpResponse.InvalidCommand;
	}

	final JsonObject jsonObject = jsonValue.asObject();
	final String userToken = jsonObject.getString(HttpFields.UserToken, null);

	if (ChatupClient.getInstance().validateToken(userToken))
	{
	    return HttpResponse.SuccessResponse;
	}

	return HttpResponse.InvalidToken;
    }
}