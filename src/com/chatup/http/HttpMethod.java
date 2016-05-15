package com.chatup.http;

public enum HttpMethod
{
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");
    
    private HttpMethod(final String paramMethod)
    {
	httpMethod = paramMethod;
    }
    
    private final String httpMethod;
    
    @Override
    public String toString()
    {
	return httpMethod;
    }
}