package com.chatup.gui;

import com.chatup.http.HttpResponse;
import com.chatup.model.RoomType;

import java.awt.Point;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class GuiMain extends JFrame
{
    public GuiMain()
    {
	initComponents();
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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panelButtons.add(buttonDisconnect, gridBagConstraints);

        buttonSettings.setText("Settings");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        panelButtons.add(buttonJoin1, gridBagConstraints);

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
	actionDisconnect();
    }//GEN-LAST:event_buttonDisconnectActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosed
    {//GEN-HEADEREND:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
	dispose();
	GuiLogin.getInstance().setVisible(true);
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

    private static GuiMain guimainInstance;

    public static GuiMain getInstance()
    {
	if (guimainInstance == null)
	{
	    guimainInstance = new GuiMain();
	}

	return guimainInstance;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonDisconnect;
    private javax.swing.JButton buttonJoin;
    private javax.swing.JButton buttonJoin1;
    private javax.swing.JButton buttonSettings;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelMain;
    private javax.swing.JScrollPane panelRooms;
    private javax.swing.JTable tableRooms;
    // End of variables declaration//GEN-END:variables

    private void actionDisconnect()
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	chatupInstance.actionDisconnect((rv) -> 
	{
	    if (rv == HttpResponse.SuccessResponse)
	    {
		chatupInstance.setLogin(null, null);
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	    }
	    else
	    {
		chatupInstance.showError(this, rv);
	    }
	});
    }

    protected void actionRefresh()
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	tableRooms.setEnabled(false);

	ChatupClient.getInstance().actionGetRooms((rv) -> 
	{
	    if (rv != HttpResponse.SuccessResponse)
	    {
		chatupInstance.showError(this, rv);
	    }

	    tableRooms.setEnabled(true);
	});
    }

    private void actionJoin(int selectedId)
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();
	final RoomType roomType = (RoomType) tableRooms.getModel().getValueAt(selectedId, 2);

	if (roomType == RoomType.Private)
	{
	    new GuiPassword(this, selectedId).setVisible(true);
	}
	else
	{
	    int selectedRoom = (Integer) tableRooms.getModel().getValueAt(selectedId, 0);

	    chatupInstance.actionJoinRoom(selectedRoom, null, (rv) -> 
	    {
		if (rv == HttpResponse.SuccessResponse)
		{
		    new GuiRoom(chatupInstance.getRoom(selectedRoom)).setVisible(true);
		}
		else
		{
		    chatupInstance.showError(this, rv);
		}
	    });
	}
    }
}