package com.chatup.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class UserModel extends AbstractListModel
{
    private ArrayList<String> mUsers = new ArrayList<>();
    private ArrayList<String> mConnected = new ArrayList<>();
    private ArrayList<String> mDisconnected = new ArrayList<>();
    
    @Override
    public int getSize()
    {
	return mUsers.size();
    }
    
    public ArrayList<String> getConnected()
    {
	return mConnected;
    }
    
    public ArrayList<String> getDisconnected()
    {
	return mDisconnected;
    }

    public void refresh(final ArrayList<String> paramUsers)
    {
	int arrayLength;
	
	mConnected.clear();
	mDisconnected.clear();

	if (paramUsers.size() < mUsers.size())
	{
	    arrayLength = paramUsers.size();
	}
	else
	{
	    arrayLength = mUsers.size();
	}

	for (int i = 0; i < arrayLength; i++)
	{
	    if (!paramUsers.get(i).equals(mUsers.get(i)))
	    {
		mConnected.add(paramUsers.get(i));
		mDisconnected.add(mUsers.get(i));
	    }
	}

	mUsers = paramUsers;
	fireIntervalAdded(this, 0, getSize() - 1);
    }

    @Override
    public Object getElementAt(int index)
    {
	if (mUsers == null)
	{
	    return null;
	}

	if (index < 0 || index > mUsers.size() - 1)
	{
	    return null;
	}

	return mUsers.get(index);
    }
}