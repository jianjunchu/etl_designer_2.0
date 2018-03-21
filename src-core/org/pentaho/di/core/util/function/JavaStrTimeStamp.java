package org.pentaho.di.core.util.function;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaStrTimeStamp {

	
	public interface StrTimeStampLibaray extends Library {
		String  StrTimeStamp(String i_timestamp,String i_format);
	}

	
	private static StrTimeStampLibaray library;

	public static StrTimeStampLibaray loadLibrary() {
		if (library == null) {
			library = (StrTimeStampLibaray) Native.loadLibrary("StrTimeStamp", StrTimeStampLibaray.class);
		}
		return library;
	}
	
	public static String  strTimeStamp(String i_timestamp,String i_format){
		loadLibrary();
		return library.StrTimeStamp(i_timestamp, i_format);
	}
	
//	public static void main(String[] args) {
//		System.out.println(strTimeStamp("12","hh"));
//	}
}
