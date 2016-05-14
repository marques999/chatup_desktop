package com.chatup.gui;

import com.chatup.model.Message;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ChatBubble extends javax.swing.JLabel
{
    private final NinePatch npatch;
    private final String messageAuthor;
    private final String messageTimestamp;

    public ChatBubble(final Message paramMessage)
    {
	initComponents();
	messageAuthor = paramMessage.getSender();
	messageTimestamp = " @ " + Date.from(Instant.ofEpochSecond(paramMessage.getTimestamp()));
	setText(paramMessage.getMessage());
	BufferedImage img2 = null;

	try
	{
	    img2 = ImageIO.read(new File(getClass().getResource("/com/chatup/resources/message_bubble.9.png").toURI()));
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	}
	catch (URISyntaxException ex)
	{
	    Logger.getLogger(ChatBubble.class.getName()).log(Level.SEVERE, null, ex);
	}

	npatch = NinePatch.load(img2, true, false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel2 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setMinimumSize(new java.awt.Dimension(224, 100));
        setPreferredSize(new java.awt.Dimension(224, 100));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private Font messageFont = new java.awt.Font("Tahoma", 0, 11);
    private Font authorFont = new java.awt.Font("Tahoma", 1, 11);

    private void drawString(Graphics g, final String text, int x, int y)
    {
	final FontMetrics fm = g.getFontMetrics();
	final Rectangle2D rect = fm.getStringBounds(text, g);
	final List<String> textList = StringUtils.wrap(text, fm, this.getSize().width - x - 8);

	for (final String line : textList)
	{
	    g.drawString(line, x, y);
	    y += fm.getHeight();
	}
    }

    @Override
    public void paint(Graphics g)
    {
	npatch.draw((Graphics2D) g, 0, 0, this.getSize().width, this.getSize().height);
	g.setFont(authorFont);
	g.drawString(messageAuthor, 24, 24);
	g.setFont(messageFont);
	g.drawString(messageTimestamp, 24 + g.getFontMetrics(authorFont).stringWidth(messageAuthor), 24);
	g.setColor(Color.black);
	drawString(g, getText(), 24, 40);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
