package com.chatup.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class UserModel extends AbstractListModel
{
    private ArrayList<String> mUsers = new ArrayList<>();
    
    @Override
    public int getSize()
    {
	return mUsers.size();
    }

    public void refresh(final ArrayList<String> paramUsers)
    {
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