/**
 * *****************************************************************************
 * Copyright (c) 2016 EclipseSource.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * ****************************************************************************
 */
package com.eclipsesource.json;

import java.io.IOException;
import java.io.Writer;

class WritingBuffer extends Writer
{
    private final Writer writer;
    private final char[] buffer;
    private int fill = 0;

    WritingBuffer(final Writer paramWriter)
    {
	this(paramWriter, 16);
    }

    WritingBuffer(final Writer paramWriter, int bufferSize)
    {
	this.writer = paramWriter;
	buffer = new char[bufferSize];
    }

    @Override
    public void write(int c) throws IOException
    {
	if (fill > buffer.length - 1)
	{
	    flush();
	}

	buffer[fill++] = (char) c;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException
    {
	if (fill > buffer.length - len)
	{
	    flush();

	    if (len > buffer.length)
	    {
		writer.write(cbuf, off, len);
		return;
	    }
	}

	System.arraycopy(cbuf, off, buffer, fill, len);
	fill += len;
    }

    @Override
    public void write(String str, int off, int len) throws IOException
    {
	if (fill > buffer.length - len)
	{
	    flush();

	    if (len > buffer.length)
	    {
		writer.write(str, off, len);
		return;
	    }
	}

	str.getChars(off, off + len, buffer, fill);
	fill += len;
    }

    @Override
    public void flush() throws IOException
    {
	writer.write(buffer, 0, fill);
	fill = 0;
    }

    @Override
    public void close() throws IOException
    {
    }
}