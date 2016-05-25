package com.chatup.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class UserModel extends AbstractListModel
{
    final ArrayList<String> userList = new ArrayList<>();

    @Override
    public int getSize()
    {
        return userList.size();
    }
    
    public void clear()
    {
        userList.clear();
    }
    
    public void insert(final String userEmail)
    {
        userList.add(userEmail);
    }

    @Override
    public Object getElementAt(int index)
    { 
        if (index < 0 || index > userList.size() - 1)
        {
            return null;
        }
        
        return userList.get(index);
    }
}