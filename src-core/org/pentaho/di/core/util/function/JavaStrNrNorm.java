package org.pentaho.di.core.util.function;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaStrNrNorm {

	
	private static StrNrNormLibaray library;

	public static StrNrNormLibaray loadLibrary() {
		if (library == null) {
			library = (StrNrNormLibaray) Native.loadLibrary("StrNrNorm", StrNrNormLibaray.class);
		}
		return library;
	}
	
	public static String  strNrNorm(String i_date){
		loadLibrary();
		return library.StrNrNorm(i_date);
	}
	
//	public static void main(String[] args) {
//		System.out.println(strNrNorm("201501225"));
//	}
	
	public interface StrNrNormLibaray extends Library{

		String  StrNrNorm(String i_date);
	}
}
