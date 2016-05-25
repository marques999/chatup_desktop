package com.chatup.gui;

import com.chatup.http.HeartbeatService;
import com.chatup.http.HttpFields;
import com.chatup.http.HttpResponse;
import com.chatup.model.Room;
import com.chatup.model.RoomType;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.awt.Point;
import java.awt.event.WindowEvent;

import java.net.MalformedURLException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class GUIMain extends JFrame
{
    private ScheduledExecutorService ses;
    
    private final HeartbeatService heartbeatExecutor = new HeartbeatService(
	ChatupClient.getInstance().getRoomService(),
	this::checkConnectionStatus
    );
    
    public GUIMain()
    {
	initComponents();
	setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	ses = Executors.newSingleThreadScheduledExecutor();
    }
    
    private void checkConnectionStatus(final JsonValue jsonValue)
    {
	boolean serverDisconnect = false;
	final ChatupClient chatupInstance = ChatupClient.getInstance();
	
	if (chatupInstance.jsonError(this, jsonValue))
	{
	    serverDisconnect = true;
	}
	else
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
	
	if (serverDisconnect)
	{
	    returnLogin();
	}
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        panelMain = new javax.swing.JPanel();
        panelButtons = new javax.swing.JPanel();
        buttonJoin = new javax.swing.JButton();
        buttonDisconnect = new javax.swing.JButton();
        buttonSettings = new javax.swing.JButton();
        buttonJoin1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        panelRooms = new javax.swing.JScrollPane();
        tableRooms = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chatup Client : ROOMS");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/chatup/resources/application-icon.png")).getImage());
        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosed(java.awt.event.WindowEvent evt)
            {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        panelMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panelMain.setLayout(new java.awt.BorderLayout());

        panelButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 0));
        panelButtons.setLayout(new java.awt.GridBagLayout());

        buttonJoin.setText("Join Room");
        buttonJoin.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonJoinActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panelButtons.add(buttonJoin, gridBagConstraints);

        buttonDisconnect.setText("Disconnect");
        buttonDisconnect.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonDisconnectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panelButtons.add(buttonDisconnect, gridBagConstraints);

        buttonSettings.setText("Settings");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panelButtons.add(buttonSettings, gridBagConstraints);

        buttonJoin1.setText("Refresh");
        buttonJoin1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonJoin1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panelButtons.add(buttonJoin1, gridBagConstraints);

        jButton1.setText("Create Room");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panelButtons.add(jButton1, gridBagConstraints);

        panelMain.add(panelButtons, java.awt.BorderLayout.LINE_END);

        tableRooms.setAutoCreateRowSorter(true);
        tableRooms.setModel(ChatupClient.getInstance().getRooms());
        tableRooms.setAutoscrolls(false);
        tableRooms.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableRooms.setFillsViewportHeight(true);
        tableRooms.setFocusTraversalPolicyProvider(true);
        tableRooms.setGridColor(new java.awt.Color(0, 0, 0));
        tableRooms.setRequestFocusEnabled(false);
        tableRooms.setRowMargin(0);
        tableRooms.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableRooms.setShowHorizontalLines(false);
        tableRooms.setShowVerticalLines(false);
        tableRooms.getTableHeader().setResizingAllowed(false);
        tableRooms.getTableHeader().setReorderingAllowed(false);
        tableRooms.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                tableRoomsMousePressed(evt);
            }
        });
        panelRooms.setViewportView(tableRooms);

        panelMain.add(panelRooms, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelMain, "card3");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
    
    private void buttonJoinActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonJoinActionPerformed
    {//GEN-HEADEREND:event_buttonJoinActionPerformed
	int selectedRoom = tableRooms.getSelectedRow();

	if (selectedRoom < 0)
	{
	    JOptionPane.showMessageDialog(this, "Please select a room to join first!", "Chatup Client : ERROR", JOptionPane.ERROR_MESSAGE);
	}
	else
	{
	    actionJoin(selectedRoom);
	}
    }//GEN-LAST:event_buttonJoinActionPerformed

    private void buttonDisconnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonDisconnectActionPerformed
    {//GEN-HEADEREND:event_buttonDisconnectActionPerformed
	dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_buttonDisconnectActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosed
    {//GEN-HEADEREND:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void toggleButtons()
    {
	boolean buttonState = !buttonJoin.isEnabled();
	
	buttonJoin.setEnabled(buttonState);
	buttonJoin1.setEnabled(buttonState);
	buttonDisconnect.setEnabled(buttonState);
	buttonSettings.setEnabled(buttonState);
	panelRooms.setEnabled(buttonState);
    }
    
    private void returnLogin()
    {
	dispose();
	ses.shutdown();
	ChatupClient.getInstance().setLogin(null, null);
	GUILogin.getInstance().setVisible(true);
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
	final ChatupClient chatupInstance = ChatupClient.getInstance();
	
	toggleButtons();

	chatupInstance.actionUserDisconnect((jsonValue) ->
	{
	    if (chatupInstance.jsonError(this, jsonValue))
	    {
		returnLogin();
	    }
	    else
	    {
		final JsonObject jsonObject = chatupInstance.extractResponse(jsonValue);

		if (jsonObject == null)
		{
		    chatupInstance.showError(this, HttpResponse.EmptyResponse);
		}
		else
		{
		    final String userEmail = jsonObject.getString(HttpFields.UserEmail, null);
		    final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		    if (userEmail == null || userToken == null)
		    {
			chatupInstance.showError(this, HttpResponse.MissingParameters);
		    }
		    else
		    {
			final HttpResponse serverResponse = chatupInstance.validateUser(userEmail, userToken);

			chatupInstance.setLogin(null, null);

			if (serverResponse == HttpResponse.SuccessResponse)
			{
			    returnLogin();
			}
			else
			{	
			    chatupInstance.showError(this, serverResponse);
			}
		    }
		}
	    }

	    toggleButtons();
	});
    }//GEN-LAST:event_formWindowClosing

    private void tableRoomsMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tableRoomsMousePressed
    {//GEN-HEADEREND:event_tableRoomsMousePressed
	final JTable target = (JTable) evt.getSource();

	if (evt.getClickCount() == 2)
	{
	    final Point selectedPoint = evt.getPoint();
	    int selectedRow = target.rowAtPoint(selectedPoint);

	    if (selectedRow >= 0)
	    {
		actionJoin(selectedRow);
	    }
	}
    }//GEN-LAST:event_tableRoomsMousePressed

    private void buttonJoin1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonJoin1ActionPerformed
    {//GEN-HEADEREND:event_buttonJoin1ActionPerformed
	actionRefresh();
    }//GEN-LAST:event_buttonJoin1ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentShown
    {//GEN-HEADEREND:event_formComponentShown
	actionRefresh();
    }//GEN-LAST:event_formComponentShown

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        new GUICreateRoom(this).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private static GUIMain guimainInstance;

    public static GUIMain getInstance()
    {
	if (guimainInstance == null)
	{
	    guimainInstance = new GUIMain();
	}

	return guimainInstance;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonDisconnect;
    private javax.swing.JButton buttonJoin;
    private javax.swing.JButton buttonJoin1;
    private javax.swing.JButton buttonSettings;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelMain;
    private javax.swing.JScrollPane panelRooms;
    private javax.swing.JTable tableRooms;
    // End of variables declaration//GEN-END:variables

    protected void actionRefresh()
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();
	
	toggleButtons();

	chatupInstance.actionGetRooms((jsonValue) ->
	{
	    if (chatupInstance.jsonError(this, jsonValue))
	    {
	    }
	    else
	    {
		final JsonArray jsonArray = chatupInstance.extractArray(jsonValue);

		if (jsonArray == null)
		{
		    chatupInstance.showError(this, HttpResponse.InvalidResponse);
		}
		else
		{
		    final HttpResponse serverResponse = chatupInstance.responseGetRooms(jsonArray);

		    if (serverResponse != HttpResponse.SuccessResponse)
		    {
			chatupInstance.showError(this, serverResponse);
		    }
		}
	    }
	    
	    toggleButtons();
	});
    }

    private void actionJoin(int selectedId)
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();
	final RoomType roomType = (RoomType) tableRooms.getModel().getValueAt(selectedId, 2);
	final Room currentRoom = chatupInstance.getRoom(selectedId);
	int selectedRoomId = (Integer) tableRooms.getModel().getValueAt(selectedId, 0);

	if (roomType == RoomType.Private)
	{
	    new GUIPassword(this, currentRoom).setVisible(true);
	}
	else
	{
	    chatupInstance.actionJoinRoom(selectedRoomId, null, (jsonValue) ->
	    {
		if (chatupInstance.jsonError(this, jsonValue))
		{
		    return;
		}

		final JsonObject jsonObject = chatupInstance.extractResponse(jsonValue);

		if (jsonObject == null)
		{
		    chatupInstance.showError(this, HttpResponse.EmptyResponse);
		}
		else
		{
		    final String userToken = jsonObject.getString(HttpFields.UserToken, null);

		    if (ChatupClient.getInstance().validateToken(userToken))
		    {
			final String serverAddress = jsonObject.getString(HttpFields.ServerAddress, null);
			int serverPort = jsonObject.getInt(HttpFields.ServerPort, -1);

			if (serverAddress == null || serverPort < 0 || serverAddress.isEmpty())
			{
			    chatupInstance.showError(this, HttpResponse.MissingParameters);
			}
			else
			{
			    try
			    {
				GUIRoom guiRoom = new GUIRoom(currentRoom, serverAddress, serverPort);
				chatupInstance.insertRoom(currentRoom.getId(), guiRoom);
				guiRoom.setVisible(true);
			    }
			    catch (MalformedURLException ex)
			    {
				chatupInstance.showError(this, HttpResponse.ServiceOffline);
			    }
			}
		    }
		    else
		    {
			chatupInstance.showError(this, HttpResponse.InvalidToken);
		    }
		}
	    });
	}
    }
}