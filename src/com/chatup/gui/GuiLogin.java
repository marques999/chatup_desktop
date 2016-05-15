package com.chatup.gui;

import com.chatup.http.HttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;
import javax.swing.JFrame;

public class GuiLogin extends JFrame
{
    private File propertiesFile;
    private Properties propertiesObject;
    private String currentUsername = ChatupGlobals.DefaultUsername;
    private String currentPassword = ChatupGlobals.DefaultPassword;

    public static GuiLogin getInstance()
    {
	if (guimainInstance == null)
	{
	    guimainInstance = new GuiLogin();
	}

	return guimainInstance;
    }
    
    private static GuiLogin guimainInstance;
   
    public GuiLogin()
    {
	initComponents();
	readPreferences();
    }
    
    private boolean currentRemember = false;

    private final String getEncodedString(final String paramInput)
    {
	return Base64.getEncoder().encodeToString(paramInput.getBytes(StandardCharsets.UTF_8));
    }

    private final String getDecodedString(final String paramInput)
    {
	return new String(Base64.getDecoder().decode(paramInput));
    }

    private void initializePreferences()
    {
	if (propertiesObject == null)
	{
	    propertiesObject = new Properties();
	}

	if (propertiesFile == null)
	{
	    propertiesFile = new File(ChatupGlobals.PreferencesFilename);
	}
    }

    private boolean savePreferences(boolean rememberPassword)
    {
	initializePreferences();

	try (final FileOutputStream fout = new FileOutputStream(propertiesFile))
	{
	    propertiesObject.setProperty(ChatupGlobals.FieldUsername, getEncodedString(tfAccount.getText()));
	    propertiesObject.setProperty(ChatupGlobals.FieldRemember, String.valueOf(checkRemember.isSelected()));

	    if (rememberPassword)
	    {
		propertiesObject.setProperty(ChatupGlobals.FieldPassword, getEncodedString(new String(inputPassword.getPassword())));
	    }
	    else
	    {
		propertiesObject.setProperty(ChatupGlobals.FieldPassword, ChatupGlobals.DefaultPassword);
	    }

	    propertiesObject.store(fout, "chatup");
	}
	catch (IOException ex)
	{
	    return false;
	}

	return true;
    }

    private boolean readPreferences()
    {
	initializePreferences();

	try (final FileInputStream fin = new FileInputStream(propertiesFile))
	{
	    propertiesObject.load(fin);
	    currentUsername = getDecodedString(propertiesObject.getProperty(ChatupGlobals.FieldUsername, ChatupGlobals.DefaultUsername));
	    currentPassword = getDecodedString(propertiesObject.getProperty(ChatupGlobals.FieldPassword, ChatupGlobals.DefaultPassword));
	    currentRemember = Boolean.valueOf(propertiesObject.getProperty(ChatupGlobals.FieldRemember, ChatupGlobals.DefaultRemember));
	}
	catch (IOException ex)
	{
	    return false;
	}

	tfAccount.setText(currentUsername);
	inputPassword.setText(currentPassword);
	checkRemember.setSelected(currentRemember);

	return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        panelAvatar = new javax.swing.JPanel();
        labelAvatar = new javax.swing.JLabel();
        panelForm = new javax.swing.JPanel();
        labelAccount = new javax.swing.JLabel();
        tfAccount = new javax.swing.JTextField();
        labelPassword = new javax.swing.JLabel();
        inputPassword = new javax.swing.JPasswordField();
        checkRemember = new javax.swing.JCheckBox();
        panelButtons = new javax.swing.JPanel();
        buttonLogin = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chatup Client : LOGIN");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/chatup/resources/application-icon.png")).getImage());
        setMinimumSize(new java.awt.Dimension(300, 500));
        setResizable(false);

        panelAvatar.setMinimumSize(new java.awt.Dimension(0, 256));
        panelAvatar.setPreferredSize(new java.awt.Dimension(300, 280));
        panelAvatar.setLayout(new java.awt.BorderLayout());

        labelAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/chatup/resources/application-large.png"))); // NOI18N
        panelAvatar.add(labelAvatar, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelAvatar, java.awt.BorderLayout.PAGE_START);

        panelForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 16, 0, 16));
        panelForm.setLayout(new java.awt.GridBagLayout());

        labelAccount.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAccount.setText("Google Account");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        panelForm.add(labelAccount, gridBagConstraints);

        tfAccount.setText("guest@gmail.com");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelForm.add(tfAccount, gridBagConstraints);

        labelPassword.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelPassword.setText("Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 6, 0);
        panelForm.add(labelPassword, gridBagConstraints);

        inputPassword.setText("12345678");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelForm.add(inputPassword, gridBagConstraints);

        checkRemember.setText("Remember my credentials");
        checkRemember.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                checkRememberActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 0);
        panelForm.add(checkRemember, gridBagConstraints);

        getContentPane().add(panelForm, java.awt.BorderLayout.CENTER);

        panelButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 1, 16, 1));

        buttonLogin.setText("Login");
        buttonLogin.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonLoginActionPerformed(evt);
            }
        });
        panelButtons.add(buttonLogin);

        buttonExit.setText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonExitActionPerformed(evt);
            }
        });
        panelButtons.add(buttonExit);

        getContentPane().add(panelButtons, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonExitActionPerformed
    {//GEN-HEADEREND:event_buttonExitActionPerformed
	System.exit(0);
    }//GEN-LAST:event_buttonExitActionPerformed

    private void checkRememberActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkRememberActionPerformed
    {//GEN-HEADEREND:event_checkRememberActionPerformed
    }//GEN-LAST:event_checkRememberActionPerformed

    private void formEnabled(boolean paramEnabled)
    {
	inputPassword.setEnabled(paramEnabled);
	tfAccount.setEnabled(paramEnabled);
	checkRemember.setEnabled(paramEnabled);
	buttonLogin.setEnabled(paramEnabled);
	buttonExit.setEnabled(paramEnabled);
    }
    
    private void buttonLoginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonLoginActionPerformed
    {//GEN-HEADEREND:event_buttonLoginActionPerformed
	final String userEmail = tfAccount.getText();
	final String userPassword = new String(inputPassword.getPassword());
	final ChatupClient chatupInstance = ChatupClient.getInstance();
	
	formEnabled(false);

	chatupInstance.actionLogin(userEmail, userPassword, (rv) -> 
	{
	    if (rv == HttpResponse.SuccessResponse)
	    {
		setVisible(false);
		savePreferences(checkRemember.isSelected());
		GuiMain.getInstance().setVisible(true);
	    }
	    else
	    {
		chatupInstance.setLogin(null, null);
		chatupInstance.showError(this, rv);
	    }
	    
	    formEnabled(true);
	});
    }//GEN-LAST:event_buttonLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonLogin;
    private javax.swing.JCheckBox checkRemember;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JLabel labelAccount;
    private javax.swing.JLabel labelAvatar;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JPanel panelAvatar;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelForm;
    private javax.swing.JTextField tfAccount;
    // End of variables declaration//GEN-END:variables
}