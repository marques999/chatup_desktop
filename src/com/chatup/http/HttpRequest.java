package com.chatup.http;

public abstract class HttpRequest
{
    private final String requestMethod;
    private final String requestMessage;

    public HttpRequest(final String paramMethod, final String paramMessage)
    {
        requestMethod = paramMethod;
        requestMessage = paramMessage;
    }

    public String getMethod()
    {
        return requestMethod;
    }

    public String getMessage()
    {
        return requestMessage;
    }
}