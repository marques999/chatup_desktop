package com.chatup.http;

import com.chatup.gui.ChatupClient;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

class UserServiceHandler extends HttpDispatcher
{
	UserServiceHandler(final String httpMethod, final String httpParameters)
	{
		super(httpMethod, httpParameters);
	}

	@Override
	protected final HttpResponse parsePostResponse(final JsonValue jsonValue)
	{
		final ChatupClient chatupInstance = ChatupClient.getInstance();
		final JsonObject jsonObject = extractResponse(jsonValue);

		if (jsonObject == null)
		{
			return HttpResponse.InvalidCommand;
		}

		final String userEmail = jsonObject.getString(HttpFields.UserEmail, null);
		final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		if (userEmail == null || userToken == null)
		{
			return HttpResponse.MissingParameters;
		}

		if (chatupInstance.responseLogin(userEmail, userToken))
		{
			return HttpResponse.SuccessResponse;
		}

		return HttpResponse.OperationFailed;
	}

	@Override
	protected final HttpResponse parseDeleteResponse(final JsonValue jsonValue)
	{
		final ChatupClient chatupInstance = ChatupClient.getInstance();
		final JsonObject jsonObject = extractResponse(jsonValue);

		if (jsonObject == null)
		{
			return HttpResponse.InvalidCommand;
		}

		final String userEmail = jsonObject.getString(HttpFields.UserEmail, null);
		final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		if (userEmail == null || userToken == null)
		{
			return HttpResponse.MissingParameters;
		}

		if (chatupInstance.userDisconnect(userEmail, userToken))
		{
			return HttpResponse.SuccessResponse;
		}

		return HttpResponse.OperationFailed;
	}
}