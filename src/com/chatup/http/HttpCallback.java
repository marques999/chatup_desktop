package com.chatup.http;

import com.eclipsesource.json.JsonValue;

public interface HttpCallback
{
    void execute(final JsonValue rv);
}