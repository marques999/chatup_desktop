/**
 * *****************************************************************************
 * Copyright (c) 2015, 2016 EclipseSource.
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
 *****************************************************************************
 */
package com.eclipsesource.json;

public final class Json
{
    private Json()
    {
    }

    public static final JsonValue NULL = new JsonLiteral("null");
    public static final JsonValue TRUE = new JsonLiteral("true");
    public static final JsonValue FALSE = new JsonLiteral("false");

    public static JsonValue value(int paramValue)
    {
	return new JsonNumber(Integer.toString(paramValue, 10));
    }

    public static JsonValue value(long paramValue)
    {
	return new JsonNumber(Long.toString(paramValue, 10));
    }

    public static JsonValue value(float paramValue)
    {
	if (Float.isInfinite(paramValue) || Float.isNaN(paramValue))
	{
	    throw new IllegalArgumentException("Infinite and NaN values not permitted in JSON");
	}

	return new JsonNumber(cutOffPointZero(Float.toString(paramValue)));
    }

    public static JsonValue value(double paramValue)
    {
	if (Double.isInfinite(paramValue) || Double.isNaN(paramValue))
	{
	    throw new IllegalArgumentException("Infinite and NaN values not permitted in JSON");
	}

	return new JsonNumber(cutOffPointZero(Double.toString(paramValue)));
    }

    public static JsonValue value(final String paramString)
    {
	return paramString == null ? NULL : new JsonString(paramString);
    }

    public static JsonValue value(boolean paramValue)
    {
	return paramValue ? TRUE : FALSE;
    }

    public static JsonValue array()
    {
	return new JsonArray();
    }

    public static JsonArray array(int... paramValues)
    {
	if (paramValues == null)
	{
	    throw new NullPointerException("values is null");
	}

	final JsonArray array = new JsonArray();

	for (int value : paramValues)
	{
	    array.add(value);
	}

	return array;
    }

    public static JsonArray array(long... paramValues)
    {
	if (paramValues == null)
	{
	    throw new NullPointerException("values is null");
	}

	final JsonArray array = new JsonArray();

	for (long value : paramValues)
	{
	    array.add(value);
	}

	return array;
    }

    public static JsonArray array(float... paramValues)
    {
	if (paramValues == null)
	{
	    throw new NullPointerException("values is null");
	}

	final JsonArray array = new JsonArray();

	for (float value : paramValues)
	{
	    array.add(value);
	}

	return array;
    }

    public static JsonArray array(double... paramValues)
    {
	if (paramValues == null)
	{
	    throw new NullPointerException("values is null");
	}

	final JsonArray array = new JsonArray();

	for (double value : paramValues)
	{
	    array.add(value);
	}

	return array;
    }

    public static JsonArray array(boolean... paramValues)
    {
	if (paramValues == null)
	{
	    throw new NullPointerException("values is null");
	}

	final JsonArray array = new JsonArray();

	for (boolean value : paramValues)
	{
	    array.add(value);
	}

	return array;
    }

    public static JsonArray array(final String... paramStrings)
    {
	if (paramStrings == null)
	{
	    throw new NullPointerException("values is null");
	}

	final JsonArray array = new JsonArray();

	for (String value : paramStrings)
	{
	    array.add(value);
	}

	return array;
    }

    public static JsonObject object()
    {
	return new JsonObject();
    }

    public static JsonValue parse(final String paramString)
    {
	if (paramString == null)
	{
	    throw new NullPointerException("string is null");
	}
	
	final DefaultHandler handler = new DefaultHandler();
	new JsonParser(handler).parse(paramString);
	return handler.getValue();
    }

    private static String cutOffPointZero(final String paramString)
    {
	if (paramString.endsWith(".0"))
	{
	    return paramString.substring(0, paramString.length() - 2);
	}
	
	return paramString;
    }

    static class DefaultHandler extends JsonHandler<JsonArray, JsonObject>
    {
	protected JsonValue value;

	@Override
	public JsonArray startArray()
	{
	    return new JsonArray();
	}

	@Override
	public JsonObject startObject()
	{
	    return new JsonObject();
	}

	@Override
	public void endNull()
	{
	    value = NULL;
	}

	@Override
	public void endBoolean(boolean bool)
	{
	    value = bool ? TRUE : FALSE;
	}

	@Override
	public void endString(final String string)
	{
	    value = new JsonString(string);
	}

	@Override
	public void endNumber(final String string)
	{
	    value = new JsonNumber(string);
	}

	@Override
	public void endArray(final JsonArray paramArray)
	{
	    value = paramArray;
	}

	@Override
	public void endObject(final JsonObject paramObject)
	{
	    value = paramObject;
	}

	@Override
	public void endArrayValue(final JsonArray paramArray)
	{
	    paramArray.add(value);
	}

	@Override
	public void endObjectValue(final JsonObject paramObject, final String paramName)
	{
	    paramObject.add(paramName, value);
	}

	final JsonValue getValue()
	{
	    return value;
	}
    }
}