package com.chatup.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.swing.JLabel;

public class JLinkLabel extends JLabel
{
    public JLinkLabel()
    {
	this("Facebook Login", "https://facebook.com");
    }

    public JLinkLabel(final String paramLabel, final String paramAction)
    {
	super(paramLabel);
	setForeground(Color.BLUE.darker());
	setCursor(new Cursor(Cursor.HAND_CURSOR));
	apdaterInstance = new HyperlinkMouseAdapter(paramAction);
	addMouseListener(apdaterInstance);
    }

    private final String[] webBrowsers =
    {
	"firefox",
	"iceweasel",
	"chrome",
	"opera",
	"konqueror",
	"epiphany",
	"mozilla",
	"netscape"
    };

    private HyperlinkMouseAdapter apdaterInstance;

    @Override
    protected void paintComponent(Graphics g)
    {
	super.paintComponent(g);

	g.setColor(getForeground());

	final Insets insets = getInsets();
	int left = insets.left;

	if (getIcon() != null)
	{
	    left += getIcon().getIconWidth() + getIconTextGap();
	}

	g.drawLine(left, getHeight() - 1 - insets.bottom, (int) getPreferredSize().getWidth() - insets.right, getHeight() - 1 - insets.bottom);
    }

    void setUrl(final String paramUrl)
    {
	apdaterInstance.setUrl(paramUrl);
    }

    private class HyperlinkMouseAdapter extends MouseAdapter
    {
	private HyperlinkMouseAdapter(final String paramUrl)
	{
	    urlString = paramUrl;
	}

	private String urlString;

	private void setUrl(final String paramUrl)
	{
	    if (paramUrl != null)
	    {
		urlString = paramUrl;
	    }
	}

	private void browseUrl()
	{
	    if (Desktop.isDesktopSupported())
	    {
		final Desktop desktop = java.awt.Desktop.getDesktop();

		if (desktop.isSupported(java.awt.Desktop.Action.BROWSE))
		{
		    try
		    {
			desktop.browse(new URI(urlString));
		    }
		    catch (final IOException | URISyntaxException ex)
		    {
			ChatupGlobals.abort(ex);
		    }
		}
	    }
	    else
	    {
		final String osName = System.getProperty("os.name");

		try
		{
		    if (osName.startsWith("Windows"))
		    {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + urlString);
		    }
		    else if (osName.startsWith("Mac OS"))
		    {
			final Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");

			final Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]
			{
			    String.class
			});

			openURL.invoke(null, new Object[]
			{
			    urlString
			});
		    }
		    else
		    {
			Map<String, String> env = System.getenv();

			if (env.get("BROWSER") != null)
			{
			    Runtime.getRuntime().exec(env.get("BROWSER") + " " + urlString);
			    return;
			}

			String browser = null;

			for (int count = 0; count < webBrowsers.length && browser == null; count++)
			{
			    if (Runtime.getRuntime().exec(new String[]{"which", webBrowsers[count]}).waitFor() == 0)
			    {
				browser = webBrowsers[count];
				break;
			    }
			}

			if (browser == null)
			{
			    throw new RuntimeException("couldn't find any browser...");
			}
			else
			{
			    Runtime.getRuntime().exec(new String[]{browser, urlString});
			}
		    }
		}
		catch (final IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InterruptedException | RuntimeException ex)
		{
		    ChatupGlobals.abort(ex);
		}
	    }
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	    browseUrl();
	}
    }
}