package com.chatup.http;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public abstract class HttpDispatcher
{
    private final String httpMethod;
    private final String httpParameters;

    public HttpDispatcher(final String urlMethod, final String urlParameters)
    {
	httpMethod = urlMethod;
	httpParameters = urlParameters;
    }

    public HttpResponse processRequest()
    {
	final JsonValue jsonObject = Json.parse(httpParameters);

	if (jsonObject == null)
	{
	    return HttpResponse.EmptyResponse;
	}

	final JsonValue extractedCommand = jsonObject.asObject().get(HttpCommands.OperationSuccess);

	if (extractedCommand == null)
	{
	    return HttpResponse.fromString(jsonObject.asObject().getString(HttpCommands.GenericError, null));
	}

	switch (httpMethod)
	{
	case "GET":
	    return parseGetResponse(extractedCommand);
	case "POST":
	    return parsePostResponse(extractedCommand);
	case "PUT":
	    return parsePutResponse(extractedCommand);
	case "DELETE":
	    return parseDeleteResponse(extractedCommand);
	}

	return HttpResponse.InvalidMethod;
    }

    protected final JsonArray extractArray(final JsonValue jsonObject, final String commandName)
    {
	final JsonValue extractedCommand = jsonObject.asObject().get(commandName);

	if (extractedCommand != null)
	{
	    if (extractedCommand.isArray())
	    {
		return extractedCommand.asArray();
	    }
	}

	return null;
    }

    protected final JsonObject extractResponse(final JsonValue jsonObject, final String commandName)
    {
	final JsonValue extractedCommand = jsonObject.asObject().get(commandName);

	if (extractedCommand != null)
	{
	    if (extractedCommand.isObject())
	    {
		return extractedCommand.asObject();
	    }
	}

	return null;
    }

    protected HttpResponse parseGetResponse(final JsonValue getValues)
    {
	return HttpResponse.InvalidMethod;
    }

    protected HttpResponse parsePostResponse(final JsonValue jsonValue)
    {
	return HttpResponse.InvalidMethod;
    }

    protected HttpResponse parsePutResponse(final JsonValue jsonValue)
    {
	return HttpResponse.InvalidMethod;
    }

    protected HttpResponse parseDeleteResponse(final JsonValue jsonValue)
    {
	return HttpResponse.InvalidMethod;
    }
}