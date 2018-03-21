package org.pentaho.di.core.util.function;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaStrClnsUTF8 {
	private static StrClnsUTF8Libaray library;

	public static StrClnsUTF8Libaray loadLibrary() {
		if (library == null) {
			library = (StrClnsUTF8Libaray) Native.loadLibrary("StrClnsUTF8", StrClnsUTF8Libaray.class);
		}
		return library;
	}

	
	public interface StrClnsUTF8Libaray extends Library{
		
		String ConvertStrClnsUTF8(String strData);
		
		String  StrClnsUTF8(String i_date);
	}
	
	public static String  strClnsUTF8(String i_date){
		loadLibrary();
		return library.StrClnsUTF8(i_date);
	}
//	public static void main(String[] args) {
//		System.out.println(strClnsUTF8("哈哈哈"));
//	}
	
	

}
