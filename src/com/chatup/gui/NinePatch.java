package com.chatup.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class NinePatch
{
    private final BufferedImage mImage;
    private final NinePatchChunk mChunk;

    public BufferedImage getImage()
    {
	return mImage;
    }

    public NinePatchChunk getChunk()
    {
	return mChunk;
    }

    public static NinePatch load(final URL fileUrl, boolean convert) throws IOException
    {
	try
	{
	    return load(GraphicsUtilities.loadCompatibleImage(fileUrl), fileUrl.getPath().toLowerCase(Locale.US).endsWith(".9.png"), convert);
	}
	catch (MalformedURLException e)
	{
	    return null;
	}
    }

    public static NinePatch load(final InputStream stream, boolean is9Patch, boolean convert) throws IOException
    {
	try
	{
	    return load(GraphicsUtilities.loadCompatibleImage(stream), is9Patch, convert);
	}
	catch (MalformedURLException e)
	{
	    return null;
	}
    }

    public static NinePatch load(BufferedImage image, boolean is9Patch, boolean convert)
    {
	if (is9Patch == false)
	{
	    if (convert)
	    {
		image = convertTo9Patch(image);
	    }
	    else
	    {
		return null;
	    }
	}
	else
	{
	    ensure9Patch(image);
	}

	return new NinePatch(image);
    }

    public int getWidth()
    {
	return mImage.getWidth();
    }

    public int getHeight()
    {
	return mImage.getHeight();
    }

    public boolean getPadding(int[] padding)
    {
	mChunk.getPadding(padding);
	return true;
    }

    public void draw(final Graphics2D graphics2D, int x, int y, int scaledWidth, int scaledHeight)
    {
	mChunk.draw(mImage, graphics2D, x, y, scaledWidth, scaledHeight, 0, 0);
    }

    private NinePatch(final BufferedImage image)
    {
	mChunk = NinePatchChunk.create(image);
	mImage = extractBitmapContent(image);
    }

    private static void ensure9Patch(final BufferedImage image)
    {
	int width = image.getWidth();
	int height = image.getHeight();

	for (int i = 0; i < width; i++)
	{
	    int pixel = image.getRGB(i, 0);

	    if (pixel != 0 && pixel != 0xFF000000)
	    {
		image.setRGB(i, 0, 0);
	    }

	    pixel = image.getRGB(i, height - 1);

	    if (pixel != 0 && pixel != 0xFF000000)
	    {
		image.setRGB(i, height - 1, 0);
	    }
	}

	for (int i = 0; i < height; i++)
	{
	    int pixel = image.getRGB(0, i);

	    if (pixel != 0 && pixel != 0xFF000000)
	    {
		image.setRGB(0, i, 0);
	    }

	    pixel = image.getRGB(width - 1, i);

	    if (pixel != 0 && pixel != 0xFF000000)
	    {
		image.setRGB(width - 1, i, 0);
	    }
	}
    }

    private static BufferedImage convertTo9Patch(final BufferedImage image)
    {
	final BufferedImage buffer = GraphicsUtilities.createTranslucentCompatibleImage(image.getWidth() + 2, image.getHeight() + 2);
	final Graphics2D g2 = buffer.createGraphics();

	g2.drawImage(image, 1, 1, null);
	g2.dispose();

	return buffer;
    }

    private BufferedImage extractBitmapContent(final BufferedImage image)
    {
	return image.getSubimage(1, 1, image.getWidth() - 2, image.getHeight() - 2);
    }
}