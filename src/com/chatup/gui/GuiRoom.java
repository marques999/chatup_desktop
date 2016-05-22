package com.chatup.gui;

import com.chatup.http.HttpResponse;
import com.chatup.model.Room;

import java.io.IOException;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.WindowConstants;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

public class GUIRoom extends javax.swing.JFrame
{
    private final Room thisRoom;

    public GUIRoom(final Room paramRoom)
    {
	initComponents();
	thisRoom = paramRoom;
	setTitle(paramRoom.getName());
	setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/chatup/resources/application-icon.png")).getImage());
	setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        panelUsers = new javax.swing.JScrollPane();
        listUsers = new javax.swing.JList<>();
        panelForm = new javax.swing.JPanel();
        inputMessage = new javax.swing.JTextField();
        buttonSend = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(520, 360));
        setPreferredSize(new java.awt.Dimension(520, 360));
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        jPanel1.setLayout(new java.awt.BorderLayout(8, 8));

        listUsers.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { "marques999", "mellus", "darklord" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listUsers.setMaximumSize(new java.awt.Dimension(200, 48));
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
        buttonSend.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
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
	try
	{
	    final String messageTimestamp = Date.from(Instant.now()).toString();
	    final String messageAuthor = ChatupClient.getInstance().getUser();
	    final String messageContents = inputMessage.getText();
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
	    sb.append(messageAuthor);
	    sb.append("</strong>&nbsp<span>");
	    sb.append(messageTimestamp);
	    sb.append("</span><br>");
	    sb.append(messageContents);   
	    doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), sb.toString());
	}
	catch (BadLocationException ex)
	{
	    Logger.getLogger(GUIRoom.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch (IOException ex)
	{
	    Logger.getLogger(GUIRoom.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_buttonSendActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        final ChatupClient chatupInstance = ChatupClient.getInstance();

        chatupInstance.actionLeaveRoom(thisRoom.getId(), (rv) -> 
        {
            if (rv == HttpResponse.SuccessResponse)
            {
                dispose();
                GUIMain.getInstance().setVisible(true);
            }
            else
            {
                chatupInstance.showError(this, rv);
            }
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
}