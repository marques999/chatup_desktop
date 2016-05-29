package com.chatup.gui;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatupGlobals
{
    public static final String FieldUserEmail = "K9zh4P3x";
    public static final String FieldUserToken = "x5TnrbTc";
    public static final String EndpointLogin = "login";
    public static final String EndpointStatus = "login_status";
    public static final String FacebookService = "graph.facebook.com";
    public static final String FacebookPermissions = "public_profile,email";
    public static final String FacebookToken = "1635174376807010|d0a06fa7bafaa218bd7b7169dd7ae934";
    public static final String PreferencesFilename = "ChatupClient.prefs";
    public static final String HeartbeatService = "HeartbeatService";
    public static final String MessageService = "MessageService";
    public static final String UserService = "UserService";
    public static final String RoomService = "RoomService";
    public static final String ServerAddress = "localhost";
    public static final String JsonType = "application/json";
    public static final String UserAgent = "Mozilla/5.0";

    public static int ServerPort = 10000;

    public static String formatDate(long paramTimestamp)
    {
	return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(paramTimestamp));
    }

    public static void abort(final Exception paramException)
    {
	final StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[2];

	final String abortMessage = String.format(
	    "Caught exception %s on method %s@%s!",
	    paramException.getClass().getSimpleName(),
	    stackTrace.getMethodName(),
	    stackTrace.getClassName()
	);

	System.out.println(abortMessage);
	System.exit(1);
    }
}