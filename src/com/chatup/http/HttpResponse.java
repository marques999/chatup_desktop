package com.chatup.http;

public enum HttpResponse
{
    MissingParameters("missingParameters"),
    InvalidToken("invalidToken"),
    ProtocolError("protocolError"),
    OperationFailed("operationFailed"),
    InvalidCommand("unexpectedCommand"),
    InvalidMethod("invalidMethod"),
    ServiceOffline("serviceOffline"),
    EmptyResponse("emptyResponse"),
    InvalidResponse("invalidResponse"),
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
}