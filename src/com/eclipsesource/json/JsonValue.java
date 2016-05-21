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
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

public abstract class JsonValue implements Serializable
{
	JsonValue()
	{
	}

	public boolean isObject()
	{
		return false;
	}

	public boolean isArray()
	{
		return false;
	}

	public boolean isNumber()
	{
		return false;
	}

	public boolean isString()
	{
		return false;
	}

	public boolean isBoolean()
	{
		return false;
	}

	public boolean isTrue()
	{
		return false;
	}

	public boolean isFalse()
	{
		return false;
	}

	public boolean isNull()
	{
		return false;
	}

	public JsonObject asObject()
	{
		throw new UnsupportedOperationException("Not an object: " + toString());
	}

	public JsonArray asArray()
	{
		throw new UnsupportedOperationException("Not an array: " + toString());
	}

	public int asInt()
	{
		throw new UnsupportedOperationException("Not a number: " + toString());
	}

	public long asLong()
	{
		throw new UnsupportedOperationException("Not a number: " + toString());
	}

	public float asFloat()
	{
		throw new UnsupportedOperationException("Not a number: " + toString());
	}

	public double asDouble()
	{
		throw new UnsupportedOperationException("Not a number: " + toString());
	}

	public String asString()
	{
		throw new UnsupportedOperationException("Not a string: " + toString());
	}

	public boolean asBoolean()
	{
		throw new UnsupportedOperationException("Not a boolean: " + toString());
	}

	public void writeTo(final Writer paramWriter) throws IOException
	{
		writeTo(paramWriter, WriterConfig.MINIMAL);
	}

	public void writeTo(final Writer paramWriter, final WriterConfig paramConfig) throws IOException
	{
		if (paramWriter == null)
		{
			throw new NullPointerException("writer is null");
		}

		if (paramConfig == null)
		{
			throw new NullPointerException("config is null");
		}

		final WritingBuffer buffer = new WritingBuffer(paramWriter, 128);

		write(paramConfig.createWriter(buffer));
		buffer.flush();
	}

	@Override
	public String toString()
	{
		return toString(WriterConfig.MINIMAL);
	}

	public String toString(final WriterConfig paramConfig)
	{
		final StringWriter writer = new StringWriter();

		try
		{
			writeTo(writer, paramConfig);
		}
		catch (final IOException ex)
		{
			throw new RuntimeException(ex);
		}

		return writer.toString();
	}

	@Override
	public boolean equals(final Object paramObject)
	{
		return super.equals(paramObject);
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	abstract void write(final JsonWriter paramWriter) throws IOException;
}