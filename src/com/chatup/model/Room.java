package com.chatup.model;

import java.util.HashMap;
import java.util.TreeSet;

import javafx.util.Pair;

public class Room
{
	private String roomName;
	private String roomOwner;
	private RoomType roomType;
	private final TreeSet<Message> roomMessages;
	private final HashMap<String, String> roomUsers;

	public Room(int paramId, final String paramName, boolean paramPrivate, final String paramOwner)
	{
		roomId = paramId;
		roomName = paramName;
		roomOwner = paramOwner;
		roomMessages = new TreeSet<>();
		roomUsers = new HashMap<>();

		if (paramPrivate)
		{
			roomType = RoomType.Private;
		}
		else
		{
			roomType = RoomType.Public;
		}
	}

	private int roomId;

	public void registerMessage(final String userToken, long messageTimestamp, final String messageBody)
	{
		roomMessages.add(new Message(roomId, userToken, messageTimestamp, messageBody));
	}

	public Message[] getMessages()
	{
		return (Message[]) roomMessages.toArray();
	}

	public final RoomType getType()
	{
		return roomType;
	}

	void setType(final RoomType paramType)
	{
		roomType = paramType;
	}

	public boolean registerUser(final Pair<String, String> userAccount)
	{
		final String userToken = userAccount.getKey();

		if (roomUsers.containsKey(userToken))
		{
			return false;
		}

		roomUsers.put(userToken, userAccount.getValue());

		return true;
	}

	public boolean removeUser(final Pair<String, String> userAccount)
	{
		final String userToken = userAccount.getKey();

		if (!roomUsers.containsKey(userToken))
		{
			return false;
		}

		roomUsers.remove(userToken);

		return true;
	}

	public int getId()
	{
		return roomId;
	}

	void setId(int paramId)
	{
		roomId = paramId;
	}

	public final String getName()
	{
		return roomName;
	}

	void setName(final String paramName)
	{
		roomName = paramName;
	}

	public final String getOwner()
	{
		return roomOwner;
	}

	void setOwner(final String paramOwner)
	{
		roomOwner = paramOwner;
	}

	public final HashMap<String, String> getUsers()
	{
		return roomUsers;
	}

	@Override
	public boolean equals(final Object other)
	{
		return other instanceof Room && ((Room) other).roomId == roomId;
	}

	@Override
	public int hashCode()
	{
		return roomId;
	}

	public boolean hasUser(final String userToken)
	{
		return roomUsers.containsKey(userToken);
	}
}