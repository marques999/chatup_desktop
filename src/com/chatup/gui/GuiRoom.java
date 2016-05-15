package com.chatup.gui;

import com.chatup.model.Message;
import com.chatup.model.Room;

import java.time.Instant;

public class GuiRoom extends javax.swing.JFrame
{
    private Room thisRoom;
    
    public GuiRoom(final Room paramRoom)
    {
	initComponents();
	thisRoom = paramRoom;
	setTitle(paramRoom.getName());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        panelUsers = new javax.swing.JScrollPane();
        listUsers = new javax.swing.JList<>();
        panelForm = new javax.swing.JPanel();
        inputMessage = new javax.swing.JTextField();
        buttonSend = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        guiMessage1 = new com.chatup.gui.GuiMessage();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(426, 253));

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

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Room: MigaxPraSempre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Utilizadores");
        jPanel2.add(jLabel2, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        guiMessage1.setMinimumSize(new java.awt.Dimension(280, 39));
        guiMessage1.setPreferredSize(new java.awt.Dimension(280, 200));
        jPanel1.add(guiMessage1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSendActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonSendActionPerformed
    {//GEN-HEADEREND:event_buttonSendActionPerformed
	guiMessage1.insert(new Message(
	    1,
	    ChatupClient.getInstance().getUser(),
	    Instant.now().getEpochSecond(),
	    inputMessage.getText())
	);
    }//GEN-LAST:event_buttonSendActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonSend;
    private com.chatup.gui.GuiMessage guiMessage1;
    private javax.swing.JTextField inputMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JList<String> listUsers;
    private javax.swing.JPanel panelForm;
    private javax.swing.JScrollPane panelUsers;
    // End of variables declaration//GEN-END:variables
}