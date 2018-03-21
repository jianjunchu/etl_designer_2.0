
package org.pentaho.di.core.util.function;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaDecTimeStamp {

	
	public interface DecTimeStampLibrary extends Library{
		
		String TrimDecTimeStamp(String strTimeStamp);
		
		int CheckDecTimeStamp(String TxTrSrc);
		
		String DecTimeStamp(String strTimeStamp,int pre);

	}
	
	private static DecTimeStampLibrary library;

//		public static DecTimeStampLibrary loadLibrary() {
//			if (DecTimeStamplibrary == null) {
//				DecTimeStamplibrary = (DecTimeStampLibrary) Native.loadLibrary("DecTimeStamp", DecTimeStampLibrary.class);
//			}
//			return DecTimeStamplibrary;
//		}
	
	public static void loadLibrary() {
		if (library == null) {
			library = (DecTimeStampLibrary) Native.loadLibrary("DecTimeStamp", DecTimeStampLibrary.class);
		}
	}
	
	
	public static String decTimeStamp(String strTimeStamp,Integer pre){
		loadLibrary();
		return library.DecTimeStamp(strTimeStamp,pre);
	}
	
	public static void main(String[] args) {
		//System.out.println(trimDecTimeStamp("2342-234"));
		//System.out.println(checkDecTimeStamp("2342-234"));
		System.out.println(decTimeStamp("2015033--103--120000",1));
	}
	
}
