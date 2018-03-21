package org.pentaho.di.core.util.function;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;

public class JavaDecDate {

	private static DecDateLibrary library ;
	
	public interface DecDateLibrary extends Library{

		PointerByReference DecDate(int strDate);
	}
	
	public static DecDateLibrary loadLibrary(){
		if(library == null){
			library = (DecDateLibrary) Native.loadLibrary("DecDate", DecDateLibrary.class);
		}
		return library;
	}
	
	
	public static String decDate(int strDate) {
		loadLibrary();
		return library.DecDate(strDate).toString();
	}
	
//	public static void main(String[] args) {
//		System.out.println(decDate(20120202));
//	}
	
	
}
