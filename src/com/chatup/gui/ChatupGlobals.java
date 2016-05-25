package com.chatup.gui;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatupGlobals
{
    public static final String FieldUsername = "K9zh4P3x";
    public static final String FieldPassword = "x5TnrbTc";
    public static final String FieldRemember = "hDZ6Mrxq";
    public static final String DefaultUsername = "Guest";
    public static final String DefaultRemember = "false";
    public static final String DefaultPassword = "";
    public static final String PreferencesFilename = "ChatupClient.prefs";
    public static final String HeartbeatServiceUrl = "HeartbeatServiceUrl";
    public static final String MessageServiceUrl = "MessageService";
    public static final String UserServiceUrl = "UserService";
    public static final String RoomServiceUrl = "RoomService";
    public static final String JsonType = "application/json";
    public static final String UserAgent = "Mozilla/5.0";

    public static String formatDate(long paramTimestamp)
    {
	return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(paramTimestamp));
    }
}