package com.chatup.gui;

import com.chatup.http.FacebookService;
import com.chatup.http.HttpFields;
import com.chatup.http.HttpResponse;
import com.chatup.http.LoginService;

import com.eclipsesource.json.JsonObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GUILogin extends JFrame
{
    private ScheduledExecutorService ses;

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
	try
	{
	    initComponents();
	    profileService = new FacebookService();
	    statusService = new LoginService(ChatupGlobals.EndpointStatus);
	}
	catch (final Exception ex)
	{
	    ChatupGlobals.abort(ex);
	}
    }
    
    @Override
    public void setVisible(boolean paramVisible)
    {
	super.setVisible(paramVisible);
	
	if (ses == null || ses.isShutdown())
	{
	    ses = Executors.newSingleThreadScheduledExecutor();
	}
	
	actionAuthentication();
    }

    private Timer swingTimer;
    private FacebookService profileService;

    private void startTimer(int secondsDuration)
    {
	swingTimer = new Timer(1000, new ActionListener()
	{
	    private int currentTick = secondsDuration;

	    @Override
	    public void actionPerformed(ActionEvent evt)
	    {
		updateProgress(--currentTick);

		if (currentTick < 1)
		{
		    swingTimer.stop();
		    actionAuthentication();
		}
	    }
	});

	swingTimer.start();
    }

    private void updateProgress(int timeoutSeconds)
    {
	if (timeoutSeconds > 0)
	{
	    progressBar.setValue(timeoutSeconds);
	    progressBar.setString(timeoutSeconds + " s");
	}
    }

    private void actionResetCounter()
    {
	progressBar.setValue(0);
	progressBar.setString("");
	inputCode.setText("...");
    }

    private void actionRefreshCode(final String codeString, int timeoutSeconds)
    {
	if (codeString != null)
	{
	    inputCode.setText(codeString);
	    progressBar.setMaximum(timeoutSeconds);
	    updateProgress(timeoutSeconds);
	}
    }

    private void actionAuthentication()
    {
	try
	{
	    facebookAuthentication();
	}
	catch (final Exception ex)
	{
	    ChatupGlobals.abort(ex);
	}
    }

    private void actionProfileInformation(final String authenticationToken)
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	formEnabled(false);

	profileService.requestInformation(authenticationToken, (jsonValue) ->
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
		    final String userEmail = jsonObject.getString("email", null);
		    final String userName = jsonObject.getString("name", null);
		    final String userId = jsonObject.getString("id", null);

		    if (userEmail == null || userId == null)
		    {
			chatupInstance.showError(this, HttpResponse.MissingParameters);
		    }
		    else
		    {
			chatupInstance.setLogin(authenticationToken, userEmail);
			actionInterrupt();
			actionResetCounter();
			
			final GUIDetails guiDetails = new GUIDetails(this, userEmail, userName);
			
			guiDetails.setVisible(true);

			if (guiDetails.getResult())
			{
			    actionLogin(authenticationToken);
			}
		    }
		}
	    }

	    formEnabled(true);
	});
    }

    private void actionInterrupt()
    {
	if (swingTimer != null)
	{
	    swingTimer.stop();
	}

	if (ses != null && !ses.isShutdown())
	{
	    ses.shutdown();
	}
    }

    private LoginService statusService;
  
    private void showAuthentication()
    {
	buttonsEnabled(true);
	inputCode.setVisible(true);
	labelFacebook.setVisible(true);
	progressBar.setVisible(true);
    }
    
    private void hideAuthentication()
    {
	buttonsEnabled(false);
	inputCode.setVisible(false);
	labelFacebook.setVisible(false);
	progressBar.setVisible(false);
    }

    private void actionLogin(final String authenticationToken)
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	hideAuthentication();
	labelCode.setText("Authorizing with the server...");

	chatupInstance.actionUserLogin(authenticationToken, (jsonValue) ->
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
			    chatupInstance.savePreferences();
			    GUIMain.getInstance().setVisible(true);
			}
			else
			{ 
			    chatupInstance.showError(this, serverResponse);
			    actionAuthentication();
			}
		    }
		}
	    }

	    labelCode.setText("Please enter the following code:");
	    showAuthentication();
	    formEnabled(true);   
	});
    }

    private String authenticationToken;

    private void checkStatus()
    {
	final ChatupClient chatupInstance = ChatupClient.getInstance();

	statusService.checkStatus(authenticationToken, (jsonValue)->
	{
	    if (jsonValue != null && jsonValue.isObject())
	    {
		final JsonObject jsonObject = jsonValue.asObject();
		final String errorMessage = jsonObject.getString("error", null);

		if (errorMessage == null)
		{
		    final String userToken = jsonObject.getString("access_token", null);

		    if (userToken == null)
		    {
			chatupInstance.showError(this, HttpResponse.EmptyResponse);
		    }
		    else
		    {
			ses.shutdown();
			actionProfileInformation(userToken);
		    }
		}
	    }
	});
    }

    private void restartScheduler()    
    {
	if (ses == null)
	{
	    ses = Executors.newSingleThreadScheduledExecutor();
	}
	else
	{
	    if (!ses.isShutdown())
	    {
		ses.shutdown();
	    }
	    
	    ses = Executors.newSingleThreadScheduledExecutor();
	}
    }

    private void facebookAuthentication() throws Exception
    {
	if (swingTimer != null)
	{
	    swingTimer.stop();
	}

	restartScheduler();
	buttonsEnabled(false);
	actionResetCounter();

	new LoginService(ChatupGlobals.EndpointLogin).requestLogin((jsonValue) ->
	{
	    if (jsonValue.isObject())
	    {
		final JsonObject jsonObject = jsonValue.asObject();
		int refreshInterval = jsonObject.getInt("interval", 0);
		int timeoutSeconds = jsonObject.getInt("expires_in", 0);

		labelFacebook.setUrl(jsonObject.getString("verification_uri", null));
		authenticationToken = jsonObject.getString("code", null);
		actionRefreshCode(jsonObject.getString("user_code", null), timeoutSeconds);
		startTimer(timeoutSeconds);
		ses.scheduleWithFixedDelay(
		    this::checkStatus,
		    refreshInterval,
		    refreshInterval,
		    TimeUnit.SECONDS
		);
	    }
	    else
	    {
		inputCode.setText("ERROR");
	    }

	    buttonsEnabled(true);
	});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        panelAvatar = new javax.swing.JPanel();
        labelAvatar = new javax.swing.JLabel();
        panelForm = new javax.swing.JPanel();
        labelCode = new javax.swing.JLabel();
        inputCode = new javax.swing.JTextField();
        labelFacebook = new com.chatup.gui.JLinkLabel();
        progressBar = new javax.swing.JProgressBar();
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

        labelCode.setText("Please enter the following code:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        panelForm.add(labelCode, gridBagConstraints);

        inputCode.setEditable(false);
        inputCode.setBackground(new java.awt.Color(255, 255, 255));
        inputCode.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        inputCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputCode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inputCode.setFocusable(false);
        inputCode.setMargin(new java.awt.Insets(4, 4, 4, 4));
        inputCode.setMinimumSize(new java.awt.Dimension(115, 36));
        inputCode.setName(""); // NOI18N
        inputCode.setPreferredSize(new java.awt.Dimension(115, 36));
        inputCode.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        panelForm.add(inputCode, gridBagConstraints);

        labelFacebook.setText("Facebook Login");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        panelForm.add(labelFacebook, gridBagConstraints);

        progressBar.setMaximum(420);
        progressBar.setToolTipText("");
        progressBar.setMinimumSize(new java.awt.Dimension(115, 14));
        progressBar.setPreferredSize(new java.awt.Dimension(120, 14));
        progressBar.setRequestFocusEnabled(false);
        progressBar.setString("0 s");
        progressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        panelForm.add(progressBar, gridBagConstraints);

        getContentPane().add(panelForm, java.awt.BorderLayout.CENTER);

        panelButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 1, 16, 1));

        buttonLogin.setText("Request Code");
        buttonLogin.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonLoginActionPerformed(evt);
            }
        });
        panelButtons.add(buttonLogin);

        buttonExit.setText("Cancel");
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

    private void buttonsEnabled(boolean paramEnabled)
    {
	buttonLogin.setEnabled(paramEnabled);
	buttonExit.setEnabled(paramEnabled);
    }
    
    private void formEnabled(boolean paramEnabled)
    {
	buttonsEnabled(paramEnabled);
	labelFacebook.setVisible(paramEnabled);
	progressBar.setVisible(paramEnabled);
    }

    private void buttonLoginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonLoginActionPerformed
    {//GEN-HEADEREND:event_buttonLoginActionPerformed
	actionAuthentication();
    }//GEN-LAST:event_buttonLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonLogin;
    private javax.swing.JTextField inputCode;
    private javax.swing.JLabel labelAvatar;
    private javax.swing.JLabel labelCode;
    private com.chatup.gui.JLinkLabel labelFacebook;
    private javax.swing.JPanel panelAvatar;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelForm;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}