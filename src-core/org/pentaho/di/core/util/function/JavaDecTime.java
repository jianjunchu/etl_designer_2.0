package org.pentaho.di.core.util.function;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaDecTime {
	
	public interface DecTimeLibrary extends Library{
		String TrimDecTime(String strTime);
		
		String DecTime(String strTime,int pre);
	}
	
	private static DecTimeLibrary library;

	public static DecTimeLibrary loadLibrary() {
		if (library == null) {
			library = (DecTimeLibrary) Native.loadLibrary("DecTime", DecTimeLibrary.class);
		}
		return library;
	}
	
	public static String trimDecTime(String strTime){
		loadLibrary();
		return library.TrimDecTime(strTime);
	}
	
	public static String decTime(String strTime,int pre){
		loadLibrary();
		return library.DecTime(strTime,pre);
	}
	
//	public static void main(String[] args) {
//		//System.out.println(trimDecTime("1111111 11111  111"));
//		System.out.println(decTime("02:00:00",6));
//	}
}
