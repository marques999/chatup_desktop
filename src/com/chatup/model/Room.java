package com.chatup.model;

import java.util.HashMap;
import java.util.TreeSet;
import javafx.util.Pair;

public class Room
{
    private String roomName;
    private String roomPassword;
    private String roomOwner;
    private RoomType roomType;
    private TreeSet<Message> roomMessages;
    private HashMap<String, String> roomUsers;

    public Room(int paramId, final String paramName, final String paramPassword, final String paramOwner)
    {
        roomId = paramId;
        roomName = paramName;
        roomOwner = paramOwner;
        roomPassword = paramPassword;
        roomMessages = new TreeSet<>();
        roomUsers = new HashMap<>();

        if (roomPassword == null)
        {
            roomType = RoomType.Public;
        }
        else
        {
            roomType = RoomType.Private;
        }
    }

    public Room(int roomId, final String roomName, final String roomOwner)
    {
        this(roomId, roomName, null, roomOwner);
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

    public boolean isEmpty()
    {
        return roomUsers.isEmpty();
    }

    public boolean isPrivate()
    {
        return roomType == RoomType.Private;
    }

    public int getId()
    {
        return roomId;
    }

    void setId(Integer integer)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public final String getPassword()
    {
        return roomPassword;
    }

    public final HashMap<String, String> getUsers()
    {
        return roomUsers;
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Room && ((Room) o).roomId == roomId;
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