package com.chatup.model;

import com.chatup.http.HttpCommands;
import com.chatup.http.HttpFields;
import com.chatup.http.HttpMethod;
import com.chatup.http.HttpRequest;

import com.eclipsesource.json.Json;

public class UserDisconnect extends HttpRequest
{
    public UserDisconnect(final String userEmail, final String userToken)
    {
        super(HttpMethod.DELETE, Json.object()
            .add(HttpCommands.UserDisconnect, Json.object()
            .add(HttpFields.UserEmail, userEmail)
            .add(HttpFields.UserToken, userToken)).toString());
    }
}