package com.chatup.http;

import com.chatup.gui.ChatupClient;
import com.chatup.gui.ChatupGlobals;

import com.chatup.model.GetMessages;
import com.chatup.model.Message;
import com.chatup.model.SendMessage;

public class MessageService extends HttpService
{
    public MessageService(final String serviceAddress, int servicePort) throws Exception
    {
	super(serviceAddress, ChatupGlobals.MessageService, servicePort);
    }

    public void getMessages(int roomId, long messageTimestamp, final HttpCallback actionCallback)
    {
	GET(new GetMessages(roomId, messageTimestamp, ChatupClient.getInstance().getToken()), -1, actionCallback);
    }

    public void sendMessage(final Message paramMessage, final HttpCallback actionCallback)
    {
	POST(new SendMessage(paramMessage), actionCallback);
    }
}