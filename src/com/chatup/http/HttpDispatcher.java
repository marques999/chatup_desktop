package com.chatup.http;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class HttpDispatcher
{
    private final String httpParameters;

    public HttpDispatcher(final String urlParameters)
    {
	httpParameters = urlParameters;
    }

    public final JsonValue processRequest()
    {
	final JsonValue jsonObject = Json.parse(httpParameters);

	if (jsonObject == null)
	{
	    return Json.object().add("error", "emptyResponse");
	}

	final JsonObject jsonCommand = jsonObject.asObject();
	final JsonValue extractedCommand = jsonCommand.get(HttpCommands.SuccessResponse);

	if (extractedCommand == null)
	{
	    return jsonCommand;
	}

	return extractedCommand;
    }

    protected JsonArray extractArray(final JsonValue jsonObject, final String commandName)
    {
	return jsonObject.isArray() ? jsonObject.asArray() : null;
    }

    protected JsonObject extractResponse(final JsonValue jsonObject)
    {
	return jsonObject.isObject() ? jsonObject.asObject() : null;
    }
}