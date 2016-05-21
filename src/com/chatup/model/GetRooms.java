package com.chatup.model;

import com.chatup.http.HttpMethod;
import com.chatup.http.HttpQuery;
import com.chatup.http.HttpRequest;

public class GetRooms extends HttpRequest
{
	public GetRooms()
	{
		super(HttpMethod.GET, new HttpQuery(new SimplePair[]{}).toString());
	}
}