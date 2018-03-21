package org.pentaho.di.core.util.function;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaStrDate {

	
	private static StrDateLibatay library;

	public static StrDateLibatay loadLibrary() {
		if (library == null) {
			library = (StrDateLibatay) Native.loadLibrary("StrDate", StrDateLibatay.class);
		}
		return library;
	}

	
	public static String strDate(String i_date,String str_format){
		loadLibrary();
		return library.StrDate(i_date, str_format);
		
	}
	
//	public static void main(String[] args) {
//		System.out.println(strDate("20151225","yyyymmdd"));
//	}
	
	public interface StrDateLibatay extends Library{

		
		String StrDate(String i_date,String str_format);
	}

}
