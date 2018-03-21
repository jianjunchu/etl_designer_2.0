package org.pentaho.di.core.util.function;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaStrTimeDec {

	
	
	
	private static StrTimeDecLibaray library;

	public static StrTimeDecLibaray loadLibrary() {
		if (library == null) {
			library = (StrTimeDecLibaray) Native.loadLibrary("StrTimeDec", StrTimeDecLibaray.class);
		}
		return library;
	}
	
	
	public static String  strTime(String i_time,int pre,String i_format){
		loadLibrary();
		return library.StrTime(i_time, pre, i_format);
		
	}
	
//	public static void main(String[] args) {
//		System.out.println(strTime("00:00:00",12,"hh"));
//	}
	
	
	public interface StrTimeDecLibaray extends Library {

		
		String StrTime(String i_time,int pre,String i_format);
	}


}
