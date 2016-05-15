package com.chatup.http;

import com.chatup.gui.ChatupClient;
import com.chatup.model.Message;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

class MessageServiceHandler extends HttpDispatcher
{
    MessageServiceHandler(final String httpMethod, final String httpParameters)
    {
        super(httpMethod, httpParameters);
    }

    @Override
    protected HttpResponse parseGetResponse(JsonValue jsonValue)
    {
        final JsonObject jsonObject = extractResponse(jsonValue);

        if (jsonObject == null)
        {
            return HttpResponse.InvalidCommand;
        }

        return HttpResponse.SuccessResponse;
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

        final Message newMessage = new Message(
            jsonObject.getInt(HttpFields.RoomId, -1),
            jsonObject.getString(HttpFields.UserToken, null),
            jsonObject.getLong(HttpFields.Timestamp, -1L),
            jsonObject.getString(HttpFields.UserMessage, null)
        );

        if (chatupInstance.insertMessage(newMessage))
        {
            return HttpResponse.SuccessResponse;
        }

        return HttpResponse.OperationFailed;
    }
}