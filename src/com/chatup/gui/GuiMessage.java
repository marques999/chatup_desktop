package com.chatup.gui;

import com.chatup.model.Message;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

public class GuiMessage extends javax.swing.JPanel
{
    private int currentY = 8;

    public GuiMessage()
    {
	initComponents();
    }
    
    public void insert(final Message paramMessage)
    {	
	jPanel1.add(new ChatBubble(paramMessage),  new AbsoluteConstraints(8, currentY, -1, -1));
	jPanel1.invalidate();
	jPanel1.repaint();
	jScrollPane1.revalidate();
	currentY += 96;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setHorizontalScrollBar(null);

        jPanel1.setMinimumSize(new java.awt.Dimension(280, 200));
        jPanel1.setPreferredSize(new java.awt.Dimension(280, 200));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}