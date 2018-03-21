package org.pentaho.di.core.util.function;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class JavaFileNorm {
	
	private static FileNormLibaray library;

	public static FileNormLibaray loadLibrary() {
		if (library == null) {
			library = (FileNormLibaray) Native.loadLibrary("FileNorm", FileNormLibaray.class);
		}
		return library;
	}
	
	
	public static int file_convert(String SourceFile,String TargetFile,String Recorddelimiter,char Errordelimiter,int Rn){
		return library.file_convert(SourceFile, TargetFile, Recorddelimiter, Errordelimiter, Rn);
	}
	
	public interface FileNormLibaray extends Library{

		int file_convert(String SourceFile,String TargetFile,String Recorddelimiter,char Errordelimiter,int Rn);
	}

}
