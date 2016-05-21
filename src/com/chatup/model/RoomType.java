package com.chatup.model;

public enum RoomType
{
	Private("Private"),
	Public("Public");

	private RoomType(final String paramType)
	{
		roomType = paramType;
	}

	private final String roomType;

	@Override
	public String toString()
	{
		return roomType;
	}
}