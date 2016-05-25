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

class JsonString extends JsonValue
{
    private final String string;

    JsonString(final String paramString)
    {
	if (paramString == null)
	{
	    throw new NullPointerException("string is null");
	}

	string = paramString;
    }

    @Override
    void write(final JsonWriter paramWriter) throws IOException
    {
	paramWriter.writeString(string);
    }

    @Override
    public boolean isString()
    {
	return true;
    }

    @Override
    public String asString()
    {
	return string;
    }

    @Override
    public int hashCode()
    {
	return string.hashCode();
    }

    @Override
    public boolean equals(final Object paramObject)
    {
	if (this == paramObject)
	{
	    return true;
	}

	if (paramObject == null)
	{
	    return false;
	}

	if (getClass() != paramObject.getClass())
	{
	    return false;
	}

	return string.equals(((JsonString) paramObject).string);
    }
}