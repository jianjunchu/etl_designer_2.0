package org.pentaho.di.core.util.function;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaStrClnsGBK {

	
	
	
	private static StrClnsGBKLibaray library;

	public static StrClnsGBKLibaray loadLibrary() {
		if (library == null) {
			library = (StrClnsGBKLibaray) Native.loadLibrary("StrClnsGBK", StrClnsGBKLibaray.class);
		}
		return library;
	}
	
	public interface StrClnsGBKLibaray extends Library{

		String ConvertStrClnsGBK(String strData);
		
		String StrClnsGBK(String i_date);
		
	}
	
//	
	
	public static String strClnsGBK(String i_date){
		loadLibrary();
		return library.StrClnsGBK(i_date);
	}
	
//	public static void main(String[] args) {
//		System.out.println(strClnsGBK("哈哈哈哈"));
//	}
	
	

	
}
