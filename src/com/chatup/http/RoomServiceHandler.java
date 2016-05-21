package com.chatup.http;

import com.chatup.gui.ChatupClient;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

class RoomServiceHandler extends HttpDispatcher
{
	RoomServiceHandler(final String httpMethod, final String httpParameters)
	{
		super(httpMethod, httpParameters);
	}

	@Override
	protected HttpResponse parseGetResponse(JsonValue jsonValue)
	{
		final JsonArray jsonArray = extractArray(jsonValue, HttpCommands.RetrieveRooms);

		if (jsonArray == null)
		{
			return HttpResponse.InvalidCommand;
		}

		if (ChatupClient.getInstance().responseGetRooms(jsonArray))
		{
			return HttpResponse.SuccessResponse;
		}

		return HttpResponse.OperationFailed;
	}

	@Override
	protected HttpResponse parsePostResponse(JsonValue jsonValue)
	{
		final ChatupClient chatupInstance = ChatupClient.getInstance();
		final JsonObject jsonObject = extractResponse(jsonValue);

		if (jsonObject == null)
		{
			return HttpResponse.InvalidCommand;
		}

		final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		if (userToken == null && !chatupInstance.getToken().equals(userToken))
		{
			return HttpResponse.InvalidToken;
		}

		return HttpResponse.SuccessResponse;
	}

	@Override
	protected HttpResponse parsePutResponse(JsonValue jsonValue)
	{
		final ChatupClient chatupInstance = ChatupClient.getInstance();
		final JsonObject jsonObject = extractResponse(jsonValue);

		if (jsonObject == null)
		{
			return HttpResponse.InvalidCommand;
		}

		final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		if (userToken == null && !chatupInstance.getToken().equals(userToken))
		{
			return HttpResponse.InvalidToken;
		}

		if (chatupInstance.createRoom(jsonObject.getString(HttpFields.RoomName, null), jsonObject.getString(HttpFields.RoomPassword, null)))
		{
			return HttpResponse.SuccessResponse;
		}

		return HttpResponse.OperationFailed;
	}

	@Override
	protected HttpResponse parseDeleteResponse(JsonValue jsonValue)
	{
		final ChatupClient chatupInstance = ChatupClient.getInstance();
		final JsonObject jsonObject = extractResponse(jsonValue);

		if (jsonObject == null)
		{
			return HttpResponse.InvalidCommand;
		}

		final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		if (userToken == null || !chatupInstance.getToken().equals(userToken))
		{
			return HttpResponse.InvalidToken;
		}

		/*
		 * if (chatupInstance.actionLeaveRoom(jsonObject.getInt(HttpFields.RoomId, -1))) { return HttpResponse.SuccessResponse; }
		 */

		return HttpResponse.OperationFailed;
	}
}