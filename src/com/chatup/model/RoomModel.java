package com.chatup.model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

public class RoomModel extends AbstractTableModel
{
    private final List<Room> rooms;
    private final HashMap<Integer, Room> roomset;

    private String[] tableColumns = new String[]
    {
	"ID", "Room", "Type", "Owner"
    };

    public RoomModel()
    {
	rooms = new ArrayList<>();
	roomset = new HashMap<>();
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
		selectedRoom.setId((Integer)paramObject);
	    case ROOM_NAME:
		selectedRoom.setName((String)paramObject);
	    case ROOM_TYPE:
		selectedRoom.setType((RoomType)paramObject);
	    case ROOM_OWNER:
		selectedRoom.setOwner((String)paramObject);
	    }

	    fireTableCellUpdated(rowIndex, columnIndex);
	}
    }

    public void insertRoom(int roomId, final String paramName, final String paramPassword, final String paramOwner)
    {
	final Room newRoom = new Room(roomId, paramName, paramPassword, paramOwner);

	if (!roomset.containsKey(roomId))
	{
	    rooms.add(newRoom);
	    roomset.put(roomId, newRoom);
	    System.out.println("inserting room " + roomId);
	    int ultimoIndice = getRowCount() - 1;
	    fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
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