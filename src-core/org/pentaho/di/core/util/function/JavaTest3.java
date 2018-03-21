package org.pentaho.di.core.util.function;

public class JavaTest3 {
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
	
	public static String test3(String st1,String st2,Integer pos)
	{
		if (st1 == null || st1.trim().length() == 0)
			return "";

		if ( st1.substring(0,pos).equals(st1.substring(0,pos).toUpperCase()))
		{
			// Already initially capitalized.
			return st1;
		}
		else
		{
			// Capitalize first character
			return st1.substring(0,pos).toUpperCase() + st1.substring(pos); 
		}
	}
}
