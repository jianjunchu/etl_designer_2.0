package org.pentaho.di.core.util.function;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaStrTime {

	
	private static StrTimeLibaray library;

	public static StrTimeLibaray loadLibrary() {
		if (library == null) {
			library = (StrTimeLibaray) Native.loadLibrary("StrTime", StrTimeLibaray.class);
		}
		return library;
	}
	
	
	public static String  strTime(String i_time,int pre,String i_format){
		loadLibrary();
		return library.StrTime(i_time, pre, i_format);
		
	}
	
//	public static void main(String[] args) {
//		System.out.println(strTime("12",1,"hh"));
//	}
	
	
	public interface StrTimeLibaray extends Library{

		String  StrTime(String i_time,int pre,String i_format);
		
	}

}
