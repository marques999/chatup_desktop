package com.chatup.gui;

import com.chatup.http.HeartbeatService;
import com.chatup.http.HttpFields;
import com.chatup.http.HttpResponse;
import com.chatup.http.MessageService;

import com.chatup.model.Message;
import com.chatup.model.Room;
import com.chatup.model.UserModel;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

public class GUIRoom extends javax.swing.JFrame
{
    private final Room myRoom;
    private final UserModel myRoomUsers;
    private final MessageService messageService;
    private final HeartbeatService heartbeatExecutor;
     
    public GUIRoom(final Room paramRoom, final String serverAddress, int serverPort) throws MalformedURLException
    {
        myRoomUsers = new UserModel();
	initComponents();
	messageService = new MessageService(serverAddress, serverPort);
	myRoom = paramRoom;
	setTitle(paramRoom.getName());
	setIconImage(new ImageIcon(getClass().getResource("/com/chatup/resources/application-icon.png")).getImage());
	setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	ses = Executors.newSingleThreadScheduledExecutor();
	actionGetMessages();
	heartbeatExecutor = new HeartbeatService(messageService,this::checkConnectionStatus);
    }
    
    @Override
    public void setVisible(boolean paramVisible)
    {
	super.setVisible(paramVisible);
	
	if (ses.isShutdown())
	{
	    ses = Executors.newSingleThreadScheduledExecutor();
	}

	ses.scheduleWithFixedDelay(heartbeatExecutor, 5, 5, TimeUnit.SECONDS);
    }
    
    private ScheduledExecutorService ses;
    
    private void checkConnectionStatus(final JsonValue jsonValue)
    {
	boolean serverDisconnect = false;
	final ChatupClient chatupInstance = ChatupClient.getInstance();
	
	if (chatupInstance.validateResponse(this, jsonValue))
	{
	    if (jsonValue.isString())
	    {
		final String userToken = jsonValue.asString();
		
		if (!chatupInstance.validateToken(userToken))
		{
		    chatupInstance.showError(this, HttpResponse.InvalidToken);
		    serverDisconnect = true;
		}
	    }
	    else
	    {
		chatupInstance.showError(this, HttpResponse.InvalidResponse);
		serverDisconnect = true;
	    }
	}
        else
        {
            serverDisconnect = true;
        }
	
	if (serverDisconnect)
	{
	    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
    }
    
    private void parseUsers(final JsonArray jsonArray)
    {
        final ChatupClient chatupInstance = ChatupClient.getInstance();
        
        if (jsonArray == null)
        {
            chatupInstance.showError(this, HttpResponse.InvalidResponse);
        }
        else
        {
            myRoomUsers.clear();
             
            for (final JsonValue jsonMessage : jsonArray)
            {
                if (!jsonMessage.isString())
                {
                    continue;
                }

                myRoomUsers.insert(jsonMessage.asString());
            }

            panelUsers.revalidate();
        }
    }

    private void parseMessage(final JsonArray jsonArray)
    {
        final ChatupClient chatupInstance = ChatupClient.getInstance();

        if (jsonArray == null)
        {
            chatupInstance.showError(this, HttpResponse.InvalidResponse);
        }
        else
        {
            for (final JsonValue jsonMessage : jsonArray)
            {
                if (!jsonMessage.isObject())
                {
                    continue;
                }

                final JsonObject jsonMessageObject = jsonMessage.asObject();

                final Message newMessage = new Message(
                    jsonMessageObject.getInt(HttpFields.RoomId, -1),
                    jsonMessageObject.getString(HttpFields.UserToken, null),
                    jsonMessageObject.getLong(HttpFields.Timestamp, 0L),
                    jsonMessageObject.getString(HttpFields.UserMessage, null)
                );

                insertMessage(newMessage);
            }
        }
    }

    private void actionGetMessages()
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	toggleButtons();

	messageService.getMessages(myRoom.getId(), (jsonValue) ->
	{
	    if (chatupInstance.validateResponse(this, jsonValue))
	    {
                final JsonObject jsonObject = chatupInstance.extractResponse(jsonValue);
                final JsonValue jsonUsers = jsonObject.get("users");
                
                if (jsonUsers == null || !jsonUsers.isArray())
                {
                    chatupInstance.showError(this, HttpResponse.InvalidResponse);
                }
                else
                {
                    parseUsers(jsonUsers.asArray());
                }
                
                final JsonValue jsonMessages = jsonObject.get("messages");
                
                if (jsonMessages == null || !jsonMessages.isArray())
                {
                    chatupInstance.showError(this, HttpResponse.InvalidResponse);
                }
                else
                {
                    parseMessage(jsonMessages.asArray());
                }
	    }

	    toggleButtons();
	});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelUsers = new javax.swing.JScrollPane();
        listUsers = new javax.swing.JList<String>();
        panelForm = new javax.swing.JPanel();
        inputMessage = new javax.swing.JTextField();
        buttonSend = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(520, 360));
        setPreferredSize(new java.awt.Dimension(520, 360));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        jPanel1.setLayout(new java.awt.BorderLayout(8, 8));

        panelUsers.setMaximumSize(new java.awt.Dimension(300, 32767));

        listUsers.setModel(myRoomUsers);
        listUsers.setMaximumSize(new java.awt.Dimension(32768, 32768));
        listUsers.setMinimumSize(new java.awt.Dimension(128, 48));
        listUsers.setPreferredSize(new java.awt.Dimension(128, 48));
        panelUsers.setViewportView(listUsers);

        jPanel1.add(panelUsers, java.awt.BorderLayout.LINE_END);

        panelForm.setLayout(new java.awt.BorderLayout(4, 0));

        inputMessage.setText("jTextField1");
        inputMessage.setMargin(new java.awt.Insets(2, 4, 2, 4));
        panelForm.add(inputMessage, java.awt.BorderLayout.CENTER);

        buttonSend.setText("Enviar");
        buttonSend.setMargin(new java.awt.Insets(2, 16, 2, 16));
        buttonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendActionPerformed(evt);
            }
        });
        panelForm.add(buttonSend, java.awt.BorderLayout.LINE_END);

        jPanel1.add(panelForm, java.awt.BorderLayout.PAGE_END);

        jEditorPane1.setEditable(false);
        jEditorPane1.setContentType("text/html"); // NOI18N
        jScrollPane2.setViewportView(jEditorPane1);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean firstMessage = true;

    private void buttonSendActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonSendActionPerformed
    {//GEN-HEADEREND:event_buttonSendActionPerformed
	sendMessage(new Message(
	    myRoom.getId(),
	    ChatupClient.getInstance().getToken(),
	    Instant.now().toEpochMilli(),
	    inputMessage.getText()
	));
    }//GEN-LAST:event_buttonSendActionPerformed

    void toggleButtons()
    {
	buttonSend.setEnabled(!buttonSend.isEnabled());
	inputMessage.setEnabled(!inputMessage.isEnabled());
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	toggleButtons();
        ses.shutdown();

	chatupInstance.actionLeaveRoom(myRoom.getId(), (jsonValue) ->
	{
	    if (chatupInstance.validateResponse(this, jsonValue))
	    {
                final JsonObject jsonObject = chatupInstance.extractResponse(jsonValue);
		final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		if (ChatupClient.getInstance().validateToken(userToken))
		{
		    dispose();
		}
		else
		{
		    chatupInstance.showError(this, HttpResponse.InvalidToken);
		}
	    }

	    toggleButtons();
	});
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonSend;
    private javax.swing.JTextField inputMessage;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listUsers;
    private javax.swing.JPanel panelForm;
    private javax.swing.JScrollPane panelUsers;
    // End of variables declaration//GEN-END:variables

    private void sendMessage(final Message paramMessage)
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	toggleButtons();

	messageService.sendMessage(paramMessage, (jsonValue) ->
	{
	    if (chatupInstance.validateResponse(this, jsonValue))
	    {
                final JsonObject jsonObject = chatupInstance.extractResponse(jsonValue);

                insertMessage(new Message(
		    jsonObject.getInt(HttpFields.RoomId, -1),
		    jsonObject.getString(HttpFields.UserToken, null),
		    jsonObject.getLong(HttpFields.Timestamp, 0L),
		    jsonObject.getString(HttpFields.UserMessage, null)
		));
	    }

	    toggleButtons();
	});
    }

    private void insertMessage(Message paramMessage)
    {
	final HTMLDocument doc = (HTMLDocument) jEditorPane1.getDocument();
	final StringBuilder sb = new StringBuilder();

	if (firstMessage)
	{
	    firstMessage = false;
	}
	else
	{
	    sb.append("<br><hr>");
	}

	sb.append("<strong>");
	sb.append(paramMessage.getSender());
	sb.append("</strong> @ <span>");
	sb.append(ChatupGlobals.formatDate(paramMessage.getTimestamp()));
	sb.append("</span><br>");
	sb.append(paramMessage.getContents());

	try
	{
	    doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), sb.toString());
	}
	catch (BadLocationException | IOException ex)
	{
	}
    }
}