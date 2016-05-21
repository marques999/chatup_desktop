package com.chatup.model;

public class SimplePair
{
	public final String first;
	public final String second;

	public SimplePair(String paramKey, String paramValue)
	{
		first = paramKey;
		second = paramValue;
	}

	public final String getFirst()
	{
		return first;
	}

	public final String getSecond()
	{
		return second;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof SimplePair && first.equals(((SimplePair) o).first) && second.equals(((SimplePair) o).second);
	}

	@Override
	public int hashCode()
	{
		return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
	}
}