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
    private URL serviceUrl;
    private String serviceAddress;
    private String servicePath;

    HttpService(final InetAddress paramAddress, final String paramService, int paramPort)
    {
	serviceAddress = paramAddress.getHostAddress();
	servicePort = paramPort;
	servicePath = paramService;

	try
	{
	    serviceUrl = new URL("http://" + serviceAddress + ":" + paramPort + "/" + paramService);
	    serviceAvailable = true;
	}
	catch (MalformedURLException ex)
	{

	    serviceUrl = null;
	    serviceAvailable = false;
	}
    }

    private int servicePort;
    private boolean serviceAvailable;

    protected void GET(final HttpRequest httpRequest, final HttpCallback actionCallback)
    {
	actionCallback.execute(__GET(httpRequest));
    }

    private HttpResponse __GET(final HttpRequest httpRequest)
    {
	final HttpURLConnection con;
	final String httpMethod = httpRequest.getMethod();
	final StringBuilder sb = new StringBuilder();

	sb.append("http://");
	sb.append(serviceAddress);
	sb.append(":");
	sb.append(servicePort);
	sb.append("/");
	sb.append(servicePath);
	sb.append("?id=3");

	System.out.println("\nSending 'GET' request to URL : " + sb.toString());

	try
	{
	    con = (HttpURLConnection) new URL(sb.toString()).openConnection();
	}
	catch (IOException ex)
	{
	    return HttpResponse.ServiceOffline;
	}

	int responseCode;

	try
	{
	    con.setRequestMethod(httpMethod);
	    con.setRequestProperty("Accept", ChatupGlobals.JsonType);
	    con.setRequestProperty("User-Agent", ChatupGlobals.UserAgent);
	    responseCode = con.getResponseCode();
	    System.out.println("Response Code : " + responseCode);
	}
	catch (IOException ex)
	{
	    return HttpResponse.ProtocolError;
	}

	final StringBuffer response = new StringBuffer();

	try (final BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())))
	{
	    for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine())
	    {
		response.append(inputLine);
	    }
	}
	catch (IOException ex)
	{
	    return HttpResponse.ProtocolError;
	}

	System.out.println(response.toString());

	return executeCallback(httpMethod, response.toString());
    }

    protected void POST(final HttpRequest httpRequest, final HttpCallback actionCallback)
    {
	actionCallback.execute(__POST(httpRequest));
    }

    private HttpResponse __POST(final HttpRequest httpRequest)
    {
	if (!serviceAvailable)
	{
	    return HttpResponse.ServiceOffline;
	}

	final HttpURLConnection con;
	final String httpMethod = httpRequest.getMethod();

	try
	{
	    con = (HttpURLConnection) serviceUrl.openConnection();
	}
	catch (IOException ex)
	{
	    return HttpResponse.ServiceOffline;
	}

	try
	{
	    con.setRequestMethod(httpMethod);
	    con.setRequestProperty("Accept", ChatupGlobals.JsonType);
	    con.setRequestProperty("Content-Type", ChatupGlobals.JsonType);
	    con.setRequestProperty("User-Agent", ChatupGlobals.UserAgent);
	    con.setDoInput(true);
	    con.setDoOutput(true);
	}
	catch (ProtocolException ex)
	{
	    return HttpResponse.ProtocolError;
	}

	System.out.println("\nSending " + httpMethod + " request to URL : " + serviceUrl);
	System.out.println("Post parameters : " + httpRequest.getMessage());

	try (final DataOutputStream wr = new DataOutputStream(con.getOutputStream()))
	{
	    wr.writeBytes(httpRequest.getMessage());
	    wr.flush();
	}
	catch (IOException ex)
	{
	    return HttpResponse.ProtocolError;
	}

	int responseCode;

	try
	{
	    responseCode = con.getResponseCode();
	    System.out.println("Response Code : " + responseCode);
	}
	catch (IOException ex)
	{
	    return HttpResponse.ProtocolError;
	}

	final StringBuffer response = new StringBuffer();

	try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())))
	{
	    for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine())
	    {
		response.append(inputLine);
	    }
	}
	catch (IOException ex)
	{
	    return HttpResponse.ProtocolError;
	}

	return executeCallback(httpMethod, response.toString());
    }

    protected abstract HttpResponse executeCallback(final String httpMethod, final String httpParameters);
}