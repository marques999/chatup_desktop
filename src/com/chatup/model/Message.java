package com.chatup.model;

import java.util.Objects;

public class Message implements Comparable<Message>
{
    private long messageTimestamp;
    private int messageRoom;

    public Message(int paramRoom, final String paramSender, long paramTimestamp, final String paramBody)
    {
	messageBody = paramBody;
	messageRoom = paramRoom;
	messageSender = paramSender;
	messageTimestamp = paramTimestamp;
    }

    private final String messageSender;
    private final String messageBody;

    public int getRoomId()
    {
	return messageRoom;
    }

    public long getTimestamp()
    {
	return messageTimestamp;
    }

    public final String getMessage()
    {
	return messageBody;
    }

    public final String getSender()
    {
	return messageSender;
    }

    @Override
    public boolean equals(final Object other)
    {
    return other instanceof Message
        && ((Message) other).getRoomId() == messageRoom
        && ((Message) other).getTimestamp() == messageTimestamp
        && ((Message) other).getSender().equals(messageSender);
    }

    @Override
    public int hashCode()
    {
	int hash = 5;
	hash = 89 * hash + (int) (this.messageTimestamp ^ (this.messageTimestamp >>> 32));
	hash = 89 * hash + this.messageRoom;
	hash = 89 * hash + Objects.hashCode(this.messageSender);
	return hash;
    }

    @Override
    public int compareTo(final Message other)
    {
	return Long.compare(messageTimestamp, other.getTimestamp());
    }
}