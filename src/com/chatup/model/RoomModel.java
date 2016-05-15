package com.chatup.model;

import com.chatup.http.HttpFields;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

public class RoomModel extends AbstractTableModel
{
    private final List<Room> rooms;
    private final HashSet<Integer> roomset;
    
    private final String[] tableColumns = new String[]
    {
	"ID", "Room", "Type", "Owner"
    };

    public RoomModel()
    {
	rooms = new ArrayList<>();
	roomset = new HashSet<>();
    }

    private static final int ROOM_ID = 0;
    private static final int ROOM_NAME = 1;
    private static final int ROOM_TYPE = 2;
    private static final int ROOM_OWNER = 3;

    @Override
    public void setValueAt(final Object paramObject, int rowIndex, int columnIndex)
    {
	final Room selectedRoom = rooms.get(rowIndex);

	if (selectedRoom != null)
	{
	    switch (columnIndex)
	    {
	    case ROOM_ID:
		selectedRoom.setId((Integer) paramObject);
	    case ROOM_NAME:
		selectedRoom.setName((String) paramObject);
	    case ROOM_TYPE:
		selectedRoom.setType((RoomType) paramObject);
	    case ROOM_OWNER:
		selectedRoom.setOwner((String) paramObject);
	    }

	    fireTableCellUpdated(rowIndex, columnIndex);
	}
    }
    
    public int insertRoom(final JsonObject jsonObject)
    {
	int roomId = jsonObject.getInt(HttpFields.RoomId, -1);
	
	if (roomId < 0)
	{
	    return -1;
	}
	
	if (roomset.contains(roomId))
	{
	    return roomId;
	}
	
	final Room newRoom = new Room(
	    roomId,
	    jsonObject.getString(HttpFields.RoomName, null),
	    jsonObject.getBoolean(HttpFields.RoomPrivate, false),
	    jsonObject.getString(HttpFields.UserToken, null)
	);
		
	rooms.add(newRoom);
	roomset.add(roomId);
	fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	
	return roomId;
    }

    public boolean insertRooms(final JsonArray jsonArray)
    {
	boolean operationResult = true;
	final HashSet<Integer> newRooms = new HashSet<>();

	for (final JsonValue jsonValue : jsonArray)
	{
	    if (jsonValue.isObject())
	    {
		int roomId = insertRoom(jsonValue.asObject());
		
		if (roomId < 0)
		{
		    operationResult = false;   
		}
		else
		{
		    newRooms.add(roomId);
		}
	    }
	    else
	    {
		operationResult = false;
	    }
	}
	
	if (newRooms.size() == roomset.size())
	{
	    return operationResult;
	}
	
	for (final Iterator<Integer> i = roomset.iterator(); i.hasNext();)
	{
	    final Integer roomId = i.next();
	    
	    if (!newRooms.contains(roomId))
	    {
		roomset.remove(roomId);
	    }
	}

	return operationResult;
    }
    
    public void clearRooms()
    {
	rooms.clear();
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
	return false;
    }

    public Room getById(int roomId)
    {
	return rooms.get(roomId);
    }

    @Override
    public int getColumnCount()
    {
	return tableColumns.length;
    }

    @Override
    public int getRowCount()
    {
	return rooms == null ? 0 : rooms.size();
    }

    @Override
    public String getColumnName(int columnIndex)
    {
	if (columnIndex < tableColumns.length)
	{
	    return tableColumns[columnIndex];
	}

	return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
	if (columnIndex == ROOM_ID)
	{
	    return Integer.class;
	}

	if (columnIndex == ROOM_TYPE)
	{
	    return RoomType.class;
	}

	return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
	final Room selectedRoom = rooms.get(rowIndex);

	if (selectedRoom == null)
	{
	    return null;
	}

	switch (columnIndex)
	{
	case ROOM_ID:
	    return selectedRoom.getId();
	case ROOM_NAME:
	    return selectedRoom.getName();
	case ROOM_TYPE:
	    return selectedRoom.getType();
	case ROOM_OWNER:
	    return selectedRoom.getOwner();
	}

	return null;
    }
}