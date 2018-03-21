package org.pentaho.di.core.util.function;

public class JavaTest1 {
	public static String initCap(String st)
	{
		if (st == null || st.trim().length() == 0)
			return "";

		if ( st.substring(0,1).equals(st.substring(0,1).toUpperCase()))
		{
			// Already initially capitalized.
			return st;
		}
		else
		{
			// Capitalize first character
			return st.substring(0,1).toUpperCase() + st.substring(1); 
		}			
	}
	
	public static String test1(String st,Integer pos)
	{
		if (st == null || st.trim().length() == 0)
			return "";

		if ( st.substring(0,pos).equals(st.substring(0,pos).toUpperCase()))
		{
			// Already initially capitalized.
			return st;
		}
		else
		{
			// Capitalize first character
			return st.substring(0,pos).toUpperCase() + st.substring(pos); 
		}			
	}
}
