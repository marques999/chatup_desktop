package com.chatup.http;

import com.chatup.gui.ChatupGlobals;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public abstract class HttpService
{
    private final URL serviceUrl;
    private final String serviceAddress;
    private final String servicePath;

    HttpService(final InetAddress paramAddress, final String paramService, int paramPort) throws MalformedURLException
    {
	serviceAddress = paramAddress.getHostAddress();
	servicePort = paramPort;
	servicePath = paramService;
	serviceUrl = new URL("http://" + serviceAddress + ":" + servicePort + "/" + servicePath);
    }

    private final int servicePort;

    protected void GET(final HttpRequest httpRequest, final HttpCallback actionCallback)
    {
	try 
	{
	    new __GET(httpRequest, actionCallback).start();
	}
	catch (IOException ex)
	{
	    actionCallback.execute(HttpResponse.ServiceOffline);
	}
    }
    
    protected void POST(final HttpRequest httpRequest, final HttpCallback actionCallback)
    {
	try 
	{
	    new __POST(httpRequest, actionCallback).start();
	}
	catch (IOException ex)
	{
	    actionCallback.execute(HttpResponse.ServiceOffline);
	}
    }

    private class __GET extends Thread
    {
	private final HttpRequest httpRequest;
	private final HttpCallback httpCallback;
	private final HttpURLConnection httpConnection;
	
	public __GET(final HttpRequest paramRequest, final HttpCallback paramCallback) throws IOException
	{
	    final StringBuilder sb = new StringBuilder();
	   
	    sb.append("http://");
	    sb.append(serviceAddress);
	    sb.append(":");
	    sb.append(servicePort);
	    sb.append("/");
	    sb.append(servicePath);
	    sb.append("?id=3");
	    System.out.println("\nSending 'GET' request to URL : " + sb.toString());
	    httpConnection = (HttpURLConnection) new URL(sb.toString()).openConnection();
	    httpRequest = paramRequest;
	    httpCallback = paramCallback;
	}
	
	private HttpResponse httpError;

	@Override
	public void run()
	{
	    final String httpMethod = httpRequest.getMethod();
	    boolean exceptionThrown = false;
	    int responseCode;
	    
	    try
	    {
		httpConnection.setRequestMethod(httpMethod);
		httpConnection.setRequestProperty("Accept", ChatupGlobals.JsonType);
		httpConnection.setRequestProperty("User-Agent", ChatupGlobals.UserAgent);
		responseCode = httpConnection.getResponseCode();
		System.out.println("Response Code : " + responseCode);
	    }
	    catch (IOException ex)
	    {
		exceptionThrown = true;
		httpError = HttpResponse.ProtocolError;
	    }
	    
	    if (exceptionThrown)
	    {
		httpCallback.execute(httpError);
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
		catch (IOException ex)
		{
		    exceptionThrown = true;
		    httpError = HttpResponse.ProtocolError;
		}
		
		if (exceptionThrown)
		{
		    httpCallback.execute(httpError);
		}
		else
		{
		    System.out.println(response.toString());
		    httpCallback.execute(responseHandler(httpMethod, response.toString()));
		}
	    }
	}
    }

    private class __POST extends Thread
    {
	private final HttpRequest httpRequest;
	private final HttpCallback httpCallback;

	public __POST(final HttpRequest paramRequest, final HttpCallback paramCallback) throws IOException
	{
	    httpRequest = paramRequest;
	    httpCallback = paramCallback;  
	    httpConnection = (HttpURLConnection) serviceUrl.openConnection();
	}

	private HttpResponse httpError;
	private HttpURLConnection httpConnection = null;
	
	@Override
	public void run()
	{
	    boolean exceptionThrown = false;
	    final String httpMethod = httpRequest.getMethod();

	    try
	    {
		httpConnection.setRequestMethod(httpMethod);
		httpConnection.setRequestProperty("Accept", ChatupGlobals.JsonType);
		httpConnection.setRequestProperty("Content-Type", ChatupGlobals.JsonType);
		httpConnection.setRequestProperty("User-Agent", ChatupGlobals.UserAgent);
		httpConnection.setDoInput(true);
		httpConnection.setDoOutput(true);
	    }
	    catch (ProtocolException ex)
	    {
		exceptionThrown = true;
		httpError = HttpResponse.ProtocolError;
	    }

	    if (exceptionThrown)
	    {
		httpCallback.execute(httpError);
	    }
	    else
	    {
		System.out.println("\nSending " + httpMethod + " request to URL : " + serviceUrl);
		System.out.println("Post parameters : " + httpRequest.getMessage());

		try (final DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream()))
		{
		    wr.writeBytes(httpRequest.getMessage());
		    wr.flush();
		}
		catch (IOException ex)
		{
		    exceptionThrown = true;
		    httpError = HttpResponse.ProtocolError;
		}

		if (exceptionThrown)
		{
		    httpCallback.execute(httpError);
		}
		else
		{
		    int responseCode;

		    try
		    {
			responseCode = httpConnection.getResponseCode();
			System.out.println("Response Code : " + responseCode);
		    }
		    catch (IOException ex)
		    {
			exceptionThrown = true;
			httpError = HttpResponse.ProtocolError;
		    }

		    if (exceptionThrown)
		    {
			httpCallback.execute(httpError);
		    }
		    else
		    {
			final StringBuffer response = new StringBuffer();

			try (BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream())))
			{
			    for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine())
			    {
				response.append(inputLine);
			    }
			}
			catch (IOException ex)
			{
			    exceptionThrown = true;
			    httpError = HttpResponse.ProtocolError;
			}

			if (exceptionThrown)
			{
			    httpCallback.execute(httpError);
			}
			else
			{
			    httpCallback.execute(responseHandler(httpMethod, response.toString()));
			}
		    }
		}
	    }
	}
    }

    protected abstract HttpResponse responseHandler(final String httpMethod, final String httpParameters);
}