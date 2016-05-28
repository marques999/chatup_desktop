package com.chatup.gui;

import com.chatup.http.HttpFields;
import com.chatup.http.HttpResponse;

import com.eclipsesource.json.JsonObject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

class GUILogin extends JFrame
{
    private File propertiesFile;
    private Properties propertiesObject;
    private String currentUsername = ChatupGlobals.DefaultUsername;
    private String currentPassword = ChatupGlobals.DefaultPassword;

    public static GUILogin getInstance()
    {
	if (guimainInstance == null)
	{
	    guimainInstance = new GUILogin();
	}

	return guimainInstance;
    }

    private static GUILogin guimainInstance;

    public GUILogin()
    {
	GridBagConstraints gridBagConstraints;
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	setTitle("Chatup Client : LOGIN");
	setIconImage(new ImageIcon(getClass().getResource("/com/chatup/resources/application-icon.png")).getImage());
	setMinimumSize(new Dimension(300, 500));
	setResizable(false);
	panelAvatar.setMinimumSize(new Dimension(0, 256));
	panelAvatar.setPreferredSize(new Dimension(300, 280));
	panelAvatar.setLayout(new BorderLayout());
	labelAvatar.setHorizontalAlignment(SwingConstants.CENTER);
	labelAvatar.setIcon(new ImageIcon(getClass().getResource("/com/chatup/resources/application-large.png")));
	panelAvatar.add(labelAvatar, BorderLayout.CENTER);
	getContentPane().add(panelAvatar, BorderLayout.PAGE_START);
	panelForm.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
	panelForm.setLayout(new GridBagLayout());
	labelUsername.setFont(new Font("Tahoma", 1, 11));
	labelUsername.setText("Google Account");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.weightx = 0.1;
	gridBagConstraints.insets = new Insets(6, 0, 6, 0);
	panelForm.add(labelUsername, gridBagConstraints);
	inputAccount.setText("guest@gmail.com");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 1;
	gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
	panelForm.add(inputAccount, gridBagConstraints);
	labelPassword.setFont(new Font("Tahoma", 1, 11));
	labelPassword.setText("Password");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 2;
	gridBagConstraints.insets = new Insets(11, 0, 6, 0);
	panelForm.add(labelPassword, gridBagConstraints);
	inputPassword.setText("12345678");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 3;
	gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
	panelForm.add(inputPassword, gridBagConstraints);
	checkRemember.setText("Remember my credentials");
	gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.gridy = 4;
	gridBagConstraints.insets = new Insets(12, 0, 12, 0);
	panelForm.add(checkRemember, gridBagConstraints);
	getContentPane().add(panelForm, BorderLayout.CENTER);
	panelButtons.setBorder(BorderFactory.createEmptyBorder(16, 1, 16, 1));
	buttonLogin.setText("Login");
	buttonLogin.addActionListener(this::buttonLoginActionPerformed);
	panelButtons.add(buttonLogin);
	buttonExit.setText("Exit");
	buttonExit.addActionListener(this::buttonExitActionPerformed);
	panelButtons.add(buttonExit);
	getContentPane().add(panelButtons, BorderLayout.PAGE_END);
	pack();
	setLocationRelativeTo(null);
	readPreferences();
    }

    private boolean currentRemember = false;

    private String getEncodedString(final String paramInput)
    {
	return Base64.getEncoder().encodeToString(paramInput.getBytes(StandardCharsets.UTF_8));
    }

    private String getDecodedString(final String paramInput)
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
	    propertiesObject.setProperty(ChatupGlobals.FieldUsername, getEncodedString(inputAccount.getText()));
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

	inputAccount.setText(currentUsername);
	inputPassword.setText(currentPassword);
	checkRemember.setSelected(currentRemember);

	return true;
    }

    private void buttonExitActionPerformed(final ActionEvent paramEvent)
    {
	System.exit(0);
    }

    private void formEnabled(boolean paramEnabled)
    {
	inputPassword.setEnabled(paramEnabled);
	inputAccount.setEnabled(paramEnabled);
	checkRemember.setEnabled(paramEnabled);
	buttonLogin.setEnabled(paramEnabled);
	buttonExit.setEnabled(paramEnabled);
    }

    private void buttonLoginActionPerformed(final ActionEvent paramEvent)
    {
	final String inputEmail = inputAccount.getText();
	final String inputToken = new String(inputPassword.getPassword());
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	formEnabled(false);

	chatupInstance.actionUserLogin(inputEmail, inputToken, (jsonValue) ->
	{
	    if (chatupInstance.validateResponse(this, jsonValue))
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

			if (serverResponse == HttpResponse.SuccessResponse)
			{
			    setVisible(false);
			    savePreferences(checkRemember.isSelected());
			    GUIMain.getInstance().setVisible(true);
			}
			else
			{
			    chatupInstance.setLogin(null, null);
			    chatupInstance.showError(this, serverResponse);
			}
		    }
		}
	    }

	    formEnabled(true);
	});
    }

    private final JPanel panelAvatar = new JPanel();
    private final JPanel panelButtons = new JPanel();
    private final JPanel panelForm = new JPanel();
    private final JLabel labelUsername = new JLabel();
    private final JLabel labelAvatar = new JLabel();
    private final JLabel labelPassword = new JLabel();
    private final JButton buttonExit = new JButton();
    private final JButton buttonLogin = new JButton();
    private final JCheckBox checkRemember = new JCheckBox();
    private final JPasswordField inputPassword = new JPasswordField();
    private final JTextField inputAccount = new JTextField();
}