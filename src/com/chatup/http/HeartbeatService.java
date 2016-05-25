package com.chatup.http;

import com.chatup.gui.ChatupClient;
import com.chatup.gui.ChatupGlobals;
import com.chatup.model.Heartbeat;

import com.eclipsesource.json.Json;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

public class HeartbeatService implements Runnable
{
    private final HttpCallback httpCallback;
    private final String serviceUrl;

    public HeartbeatService(final HttpService httpService, final HttpCallback paramCallback)
    {
	serviceUrl = "http://" 
	    + httpService.getAddress() + ":" 
	    + httpService.getPort() + "/" 
	    + ChatupGlobals.HeartbeatServiceUrl
	    + new Heartbeat(ChatupClient.getInstance().getToken()).getMessage();
	httpCallback = paramCallback;
    }

    private HttpResponse httpError;
    private HttpURLConnection httpConnection;

    @Override
    public void run()
    {
	boolean exceptionThrown = false;

	try
	{
	    httpConnection = (HttpURLConnection) new URL(serviceUrl).openConnection();
	    httpConnection.setRequestMethod("GET");
	    httpConnection.setRequestProperty("Accept", ChatupGlobals.JsonType);
	    httpConnection.setRequestProperty("User-Agent", ChatupGlobals.UserAgent);
	    httpConnection.getResponseCode();
	}
	catch (final Throwable ex)
	{
	    exceptionThrown = true;
	    httpError = HttpResponse.ProtocolError;
	}

	if (exceptionThrown)
	{
	    httpCallback.execute(Json.object().add("error", httpError.toString()));
	}
	else
	{
	    final StringBuffer response = new StringBuffer();

	    try (final BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream())))
	    {
		for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine())
		{
		    response.append(inputLine);
		}
	    }
	    catch (final Throwable ex)
	    {
		exceptionThrown = true;
		httpError = HttpResponse.ProtocolError;
	    }

	    if (exceptionThrown)
	    {
		httpCallback.execute(Json.object().add("error", httpError.toString()));
	    }
	    else
	    {
		httpCallback.execute(new HttpDispatcher(response.toString()).processRequest());
	    }
	}
    }
}