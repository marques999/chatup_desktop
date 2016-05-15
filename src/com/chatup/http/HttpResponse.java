package com.chatup.http;

public enum HttpResponse
{
    AuthenticationFailed("authenticationFailed"),
    EmptyResponse("emptyResponse"),
    InvalidCommand("unexpectedCommand"),
    InvalidMethod("invalidMethod"),
    InvalidResponse("invalidResponse"),
    InvalidToken("invalidToken"),
    MissingParameters("missingParameters"),
    OperationFailed("operationFailed"),
    ProtocolError("protocolError"),
    RoomNotFound("roomNotFound"),
    ServiceOffline("serviceOffline"),
    SuccessResponse("success");

    private final String responseMessage;

    private HttpResponse(final String paramResponse)
    {
        responseMessage = paramResponse;
    }

    @Override
    public String toString()
    {
        return responseMessage;
    }

    public static HttpResponse fromString(final String httpResponse)
    {
        if (httpResponse != null)
        {
            for (final HttpResponse hr : HttpResponse.values())
            {
                if (httpResponse.equalsIgnoreCase(hr.responseMessage))
                {
                    return hr;
                }
           }
       }

       return HttpResponse.InvalidResponse;
    }
    
    public static String getErrorMessage(final HttpResponse httpResponse)
    {
	switch (httpResponse)
	{
	case AuthenticationFailed:
	    return "Invalid username/password combination entered!";
	case EmptyResponse:
	    return "Received empty response from server!";
	case InvalidMethod:
	    return "Service does not support this method!";
	case InvalidToken:
	    return "User token not recognized by remote server!";
	case RoomNotFound:
	    return "Selected chat room does not exist!";
	case ProtocolError:
	    return "Connection to server lost!";
	case ServiceOffline:
	    return "This service is currenttly not available!";
	}
	
	return httpResponse.toString();
    }
}