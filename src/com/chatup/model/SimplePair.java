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
    public boolean equals(final Object paramObject)
    {
	return paramObject instanceof SimplePair && first.equals(((SimplePair) paramObject).first) && second.equals(((SimplePair) paramObject).second);
    }

    @Override
    public int hashCode()
    {
	return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }
}