package com.chatup.gui;

import java.awt.image.BufferedImage;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics;
import java.awt.Transparency;

import java.net.URL;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

class GraphicsUtilities
{
    static BufferedImage loadCompatibleImage(final URL paramUrl) throws IOException
    {
        return toCompatibleImage(ImageIO.read(paramUrl));
    }

    static BufferedImage loadCompatibleImage(final InputStream paramStream) throws IOException
    {
        return toCompatibleImage(ImageIO.read(paramStream));
    }

    private static BufferedImage toCompatibleImage(final BufferedImage paramImage)
    {
        if (isHeadless())
        {
            return paramImage;
        }

        if (paramImage.getColorModel().equals(getGraphicsConfiguration().getColorModel()))
        {
            return paramImage;
        }

        final BufferedImage compatibleImage = getGraphicsConfiguration().createCompatibleImage(paramImage.getWidth(), paramImage.getHeight(), paramImage.getTransparency());
        final Graphics g = compatibleImage.getGraphics();

        g.drawImage(paramImage, 0, 0, null);
        g.dispose();

        return compatibleImage;
    }

    private static GraphicsConfiguration getGraphicsConfiguration()
    {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    private static boolean isHeadless()
    {
        return GraphicsEnvironment.isHeadless();
    }

    static BufferedImage createTranslucentCompatibleImage(int paramWidth, int paramHeight)
    {
        return getGraphicsConfiguration().createCompatibleImage(paramWidth, paramHeight, Transparency.TRANSLUCENT);
    }

    static int[] getPixels(final BufferedImage paramImage, int x, int y, int w, int h, int[] pixels)
    {
        if (w == 0 || h == 0)
        {
            return new int[0];
        }

        if (pixels == null)
        {
            pixels = new int[w * h];
        }
        else if (pixels.length < w * h)
        {
            throw new IllegalArgumentException("Pixels array must have a length >= w * h");
        }

        int imageType = paramImage.getType();

        if (imageType == BufferedImage.TYPE_INT_ARGB || imageType == BufferedImage.TYPE_INT_RGB)
        {
            return (int[]) paramImage.getRaster().getDataElements(x, y, w, h, pixels);
        }

        return paramImage.getRGB(x, y, w, h, pixels, 0, w);
    }
}