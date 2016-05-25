package com.chatup.model;

import com.chatup.http.HttpCommands;
import com.chatup.http.HttpFields;
import com.chatup.http.HttpMethod;
import com.chatup.http.HttpRequest;

import com.eclipsesource.json.Json;

public class UserLogin extends HttpRequest
{
    public UserLogin(final String userEmail, final String userToken)
    {
	super(HttpMethod.POST, Json.object()
	    .add(HttpCommands.UserLogin, Json.object()
	    .add(HttpFields.UserEmail, userEmail)
	    .add(HttpFields.UserToken, userToken)).toString());
    }
}