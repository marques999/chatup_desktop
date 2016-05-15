package com.chatup.http;

import com.chatup.gui.ChatupClient;
import com.chatup.gui.ChatupGlobals;

import com.chatup.model.GetMessages;
import com.chatup.model.Message;
import com.chatup.model.SendMessage;

import java.net.InetAddress;
import java.net.MalformedURLException;

public class MessageService extends HttpService
{
    public MessageService(final InetAddress serviceAddress, int servicePort) throws MalformedURLException
    {
	super(serviceAddress, ChatupGlobals.MessageServiceUrl, servicePort);
    }

    @Override
    protected HttpResponse responseHandler(String httpMethod, String httpParameters)
    {
	return new MessageServiceHandler(httpMethod, httpParameters).processRequest();
    }

    public void getMessages(int roomId, final HttpCallback actionCallback)
    {
	GET(new GetMessages(roomId, ChatupClient.getInstance().getToken()), actionCallback);
    }

    public void sendMessage(final Message paramMessage, final HttpCallback actionCallback)
    {
	POST(new SendMessage(paramMessage), actionCallback);
    }
}