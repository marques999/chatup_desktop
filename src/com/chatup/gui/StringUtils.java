package com.chatup.gui;

import java.awt.FontMetrics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringUtils
{
    static final List wrap(final String paramString, final FontMetrics fm, int maxWidth)
    {
        final List lines = splitIntoLines(paramString);

        if (lines.isEmpty())
        {
            return lines;
        }

        final ArrayList strings = new ArrayList();

        for (Iterator iter = lines.iterator(); iter.hasNext();)
        {
            wrapLineInto((String) iter.next(), strings, fm, maxWidth);
        }

        return strings;
    }

    static void wrapLineInto(String paramLine, final List paramList, final FontMetrics fm, int maxWidth)
    {
        int len = paramLine.length();
        int width;

        while (len > 0 && (width = fm.stringWidth(paramLine)) > maxWidth)
        {
	    int pos;
            int guess = len * maxWidth / width;
            
	    String before = paramLine.substring(0, guess).trim();
            width = fm.stringWidth(before);

            if (width > maxWidth) // Too long
            {
                pos = findBreakBefore(paramLine, guess);
            }
            else
            {
                pos = findBreakAfter(paramLine, guess);

                if (pos != -1)
                {
                    before = paramLine.substring(0, pos).trim();

                    if (fm.stringWidth(before) > maxWidth)
                    {
			pos = findBreakBefore(paramLine, guess);
                    }
                }
            }

            if (pos == -1)
            {
                pos = guess;
            }

            paramList.add(paramLine.substring(0, pos).trim());
            paramLine = paramLine.substring(pos).trim();
            len = paramLine.length();
        }

        if (len > 0)
        {
            paramList.add(paramLine);
        }
    }

    static int findBreakBefore(final String paramLine, int paramStart)
    {
        for (int i = paramStart; i >= 0; --i)
        {
            char c = paramLine.charAt(i);

            if (Character.isWhitespace(c) || c == '-')
            {
		return i;
            }
        }

        return -1;
    }

    static int findBreakAfter(final String paramLine, int paramStart)
    {
        int len = paramLine.length();

        for (int i = paramStart; i < len; ++i)
        {
            char c = paramLine.charAt(i);

            if (Character.isWhitespace(c) || c == '-')
            {
		return i;
            }
        }

        return -1;
    }

    static List splitIntoLines(final String paramString)
    {
        final ArrayList strings = new ArrayList();
        int len = paramString.length();

        if (len == 0)
        {
            strings.add("");
            return strings;
        }

        int lineStart = 0;

        for (int i = 0; i < len; ++i)
        {
            char c = paramString.charAt(i);

            if (c == '\r')
            {
		int newlineLength = 1;

		if ((i + 1) < len && paramString.charAt(i + 1) == '\n')
		{
		    newlineLength = 2;
		}

		strings.add(paramString.substring(lineStart, i));
		lineStart = i + newlineLength;

		if (newlineLength == 2)
		{
		    ++i;
		}
            }
            else if (c == '\n')
            {
		strings.add(paramString.substring(lineStart, i));
		lineStart = i + 1;
            }
        }

        if (lineStart < len)
        {
            strings.add(paramString.substring(lineStart));
        }

        return strings;
    }
}