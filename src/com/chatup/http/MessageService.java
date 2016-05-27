package com.chatup.http;

import com.chatup.gui.ChatupClient;
import com.chatup.gui.ChatupGlobals;

import com.chatup.model.GetMessages;
import com.chatup.model.Message;
import com.chatup.model.SendMessage;

import java.net.MalformedURLException;

public class MessageService extends HttpService
{
    public MessageService(final String serviceAddress, int servicePort) throws MalformedURLException
    {
	super("http://" + serviceAddress, ChatupGlobals.MessageServiceUrl, servicePort);
    }

    public void getMessages(int roomId, long messageTimestamp, final HttpCallback actionCallback)
    {
	GET(new GetMessages(roomId, messageTimestamp, ChatupClient.getInstance().getToken()), 65536, actionCallback);
    }

    public void sendMessage(final Message paramMessage, final HttpCallback actionCallback)
    {
	POST(new SendMessage(paramMessage), actionCallback);
    }
}