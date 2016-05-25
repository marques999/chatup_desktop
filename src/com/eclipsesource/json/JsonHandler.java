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

public abstract class JsonHandler<A, O>
{
    JsonParser parser;

    protected Location getLocation()
    {
	return parser.getLocation();
    }

    public void startNull()
    {
    }

    public void endNull()
    {
    }

    public void startBoolean()
    {
    }

    public void endBoolean(boolean value)
    {
    }

    public void startString()
    {
    }

    public void endString(String string)
    {
    }

    public void startNumber()
    {
    }

    public void endNumber(String string)
    {
    }

    public A startArray()
    {
	return null;
    }

    public void endArray(final A paramArray)
    {
    }

    public void startArrayValue(final A paramArray)
    {
    }

    public void endArrayValue(final A paramArray)
    {
    }

    public O startObject()
    {
	return null;
    }

    public void endObject(final O paramObject)
    {
    }

    public void startObjectName(final O paramObject)
    {
    }

    public void endObjectName(final O paramObject, final String paramName)
    {
    }

    public void startObjectValue(final O paramObject, final String paramName)
    {
    }

    public void endObjectValue(final O paramObject, final String paramName)
    {
    }
}