package com.chatup.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class GUIDetails extends javax.swing.JDialog
{
    public GUIDetails(final Frame paramFrame, final String userEmail, final String userName)
    {
        super(paramFrame);
        final JButton buttonAbort = new JButton();
        final JButton buttonContinue = new JButton();
        final JPanel buttonsPanel = new JPanel();
        final JPanel formPanel = new JPanel();
        final JTextField inputEmail = new JTextField();
        final JTextField inputName = new JTextField();
        final JLabel labelEmail = new JLabel();
        final JLabel labelName = new JLabel();
        final JLabel labelTitle = new JLabel();
        final JPanel titlePanel = new JPanel();
        final Insets defaultInsets = new Insets(4, 0, 4, 0);
        final Dimension labelDimension = new Dimension(35, 14);
        GridBagConstraints gridBagConstraints;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Facebook Login");
        setModal(true);
        setResizable(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        formPanel.setLayout(new GridBagLayout());
        labelEmail.setText("E-mail");
        labelEmail.setMaximumSize(labelDimension);
        labelEmail.setMinimumSize(labelDimension);
        labelEmail.setPreferredSize(labelDimension);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(4, 6, 4, 6);
        formPanel.add(labelEmail, gridBagConstraints);
        inputEmail.setEditable(false);
        inputEmail.setBackground(Color.WHITE);
        inputEmail.setColumns(20);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = defaultInsets;
        formPanel.add(inputEmail, gridBagConstraints);
        labelName.setText("Name");
        labelName.setMaximumSize(labelDimension);
        labelName.setMinimumSize(labelDimension);
        labelName.setPreferredSize(labelDimension);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(4, 6, 4, 6);
        formPanel.add(labelName, gridBagConstraints);
        inputName.setEditable(false);
        inputName.setBackground(Color.WHITE);
        inputName.setColumns(20);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.insets = defaultInsets;
        formPanel.add(inputName, gridBagConstraints);
        getContentPane().add(formPanel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(8, 4, 4, 4));
        titlePanel.setLayout(new BorderLayout());
        labelTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setText("Please confirm your account details");
        titlePanel.add(labelTitle, BorderLayout.CENTER);
        getContentPane().add(titlePanel, BorderLayout.PAGE_START);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        buttonAbort.setText("Abort");
        buttonContinue.setText("Continue");
        buttonAbort.addActionListener(this::buttonAbortActionPerformed);
        buttonContinue.addActionListener(this::buttonContinueActionPerformed);
        buttonsPanel.add(buttonContinue);
        buttonsPanel.add(buttonAbort);
        getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(paramFrame);
        inputEmail.setText(userEmail);
        inputName.setText(userName);
    }

    private boolean dialogResult = false;

    public boolean getResult()
    {
        return dialogResult;
    }

    private void buttonAbortActionPerformed(final ActionEvent evt)
    {
        dispose();
    }

    private void buttonContinueActionPerformed(final ActionEvent evt)
    {
        dialogResult = true;
        dispose();
    }
}