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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JsonArray extends JsonValue implements Iterable<JsonValue>
{
	private final List<JsonValue> values;

	public JsonArray()
	{
		values = new ArrayList<>();
	}

	public JsonArray(final JsonArray paramArray)
	{
		this(paramArray, false);
	}

	private JsonArray(final JsonArray paramArray, boolean paramUnmodifiable)
	{
		if (paramArray == null)
		{
			throw new NullPointerException("array is null");
		}

		if (paramUnmodifiable)
		{
			values = Collections.unmodifiableList(paramArray.values);
		}
		else
		{
			values = new ArrayList<>(paramArray.values);
		}
	}

	public static JsonArray unmodifiableArray(final JsonArray paramArray)
	{
		return new JsonArray(paramArray, true);
	}

	public JsonArray add(int paramValue)
	{
		values.add(Json.value(paramValue));
		return this;
	}

	public JsonArray add(long paramValue)
	{
		values.add(Json.value(paramValue));
		return this;
	}

	public JsonArray add(float paramValue)
	{
		values.add(Json.value(paramValue));
		return this;
	}

	public JsonArray add(double paramValue)
	{
		values.add(Json.value(paramValue));
		return this;
	}

	public JsonArray add(boolean paramValue)
	{
		values.add(Json.value(paramValue));
		return this;
	}

	public JsonArray add(final String paramValue)
	{
		values.add(Json.value(paramValue));
		return this;
	}

	public JsonArray add(final JsonValue paramValue)
	{
		values.add(paramValue);
		return this;
	}

	public JsonArray set(int index, int value)
	{
		values.set(index, Json.value(value));
		return this;
	}

	public JsonArray set(int index, long value)
	{
		values.set(index, Json.value(value));
		return this;
	}

	public JsonArray set(int index, float value)
	{
		values.set(index, Json.value(value));
		return this;
	}

	public JsonArray set(int index, double value)
	{
		values.set(index, Json.value(value));
		return this;
	}

	public JsonArray set(int index, boolean value)
	{
		values.set(index, Json.value(value));
		return this;
	}

	public JsonArray set(int index, String value)
	{
		values.set(index, Json.value(value));
		return this;
	}

	public JsonArray set(int index, JsonValue value)
	{
		values.set(index, value);
		return this;
	}

	public JsonArray remove(int index)
	{
		values.remove(index);
		return this;
	}

	public int size()
	{
		return values.size();
	}

	public boolean isEmpty()
	{
		return values.isEmpty();
	}

	public JsonValue get(int index)
	{
		return values.get(index);
	}

	public List<JsonValue> values()
	{
		return Collections.unmodifiableList(values);
	}

	@Override
	public Iterator<JsonValue> iterator()
	{
		final Iterator<JsonValue> iterator = values.iterator();

		return new Iterator<JsonValue>()
		{
			@Override
			public boolean hasNext()
			{
				return iterator.hasNext();
			}

			@Override
			public JsonValue next()
			{
				return iterator.next();
			}

			@Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	void write(final JsonWriter paramWriter) throws IOException
	{
		paramWriter.writeArrayOpen();

		final Iterator<JsonValue> iterator = iterator();
		boolean first = true;

		while (iterator.hasNext())
		{
			if (!first)
			{
				paramWriter.writeArraySeparator();
			}

			iterator.next().write(paramWriter);
			first = false;
		}

		paramWriter.writeArrayClose();
	}

	@Override
	public boolean isArray()
	{
		return true;
	}

	@Override
	public JsonArray asArray()
	{
		return this;
	}

	@Override
	public int hashCode()
	{
		return values.hashCode();
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

		return values.equals(((JsonArray) paramObject).values);
	}
}