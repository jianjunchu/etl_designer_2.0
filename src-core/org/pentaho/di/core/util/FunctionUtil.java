package org.pentaho.di.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;

public class FunctionUtil {

	
	public static void main (String[] args)
	{
		FunctionUtil f = new FunctionUtil();
		String s1 = "test3(\"a\",test3(\"a\",\"b\",1),1)";
		try {
			FunctionUtil.execFunction(s1, null, args);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String s2 = "test2(test1(\"2015033--103--120000\",1),1)";
		try {
			FunctionUtil.execFunction(s2, null, args);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * execute a IF Statement or a nested function
	 * @param statementExpress
	 * @param rowMeta
	 * @param rowData
	 * @return
	 * @throws KettleException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object execStatement(String statementExpress, RowMetaInterface rowMeta,Object[] rowData) throws KettleException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String token = statementExpress.trim().substring(0,2);
		if (token.equalsIgnoreCase("IF"))
		{
			StatementComponents components = parseStatement(statementExpress);
			if(evl(components.getCondition(),rowMeta,rowData))
			{
				return execFunction( components.getTrueFunction(),  rowMeta, rowData);
			}
			else
				return execFunction( components.getFalseFunction(),  rowMeta, rowData);
		}
		else
		{
			return execFunction( statementExpress,  rowMeta, rowData);
		}		
	}
	
	/**
	 * evaluate a condition express 
	 * @param condition
	 * @return true or false
	 * @throws KettleException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private static boolean evl(String condition, RowMetaInterface rowMeta,Object[] rowData) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, KettleException {
		Pattern p = Pattern.compile("[ ]*[>|<|=][ ]*");		
		Matcher m = p.matcher(condition);		
		int startIdx = m.start();		
		int endIdx=  m.end();
		if(startIdx>-1 && endIdx>-1)
		{
			String sign = condition.substring(startIdx,endIdx).trim();
			String left = condition.substring(0,startIdx);
			String right = condition.substring(startIdx,endIdx);
			
			if (sign.equals("="))
			{
				Object leftResult = execFunction(left,rowMeta,rowData);
				Object rightResult = execFunction(right,rowMeta,rowData);
				if(leftResult.toString().equals(rightResult.toString()))
					return true;
			    else 
					return false;
			}
			else if (sign.equals(">"))
			{
				Object leftResult = execFunction(left,rowMeta,rowData);
				Object rightResult = execFunction(right,rowMeta,rowData);
				if(new Double(leftResult.toString()).doubleValue() > new Double(rightResult.toString()).doubleValue())
					return true;
			    else 
					return false;
			}
			else if (sign.equals("<"))
			{
				Object leftResult = execFunction(left,rowMeta,rowData);
				Object rightResult = execFunction(right,rowMeta,rowData);
				if(new Double(leftResult.toString()).doubleValue() < new Double(rightResult.toString()).doubleValue())
					return true;
			    else 
					return false;
			}else
			{
				throw new KettleException("error condition"+condition);
			}
		}else
		{
			throw new KettleException("error condition"+condition);
		}
	}

	/**
	 * parse a statement to 3 parts(condition, true function,false function)
	 * @param statementExpress
	 * @return
	 */
	private static StatementComponents parseStatement(String statementExpress) {
		int ifIdx  = statementExpress.toUpperCase().indexOf("IF");
		int thenIdx  = statementExpress.toUpperCase().indexOf("THEN");
		int elseIdx  = statementExpress.toUpperCase().indexOf("ELSE");
		String condition = statementExpress.substring(ifIdx+2,thenIdx);
		String trueFunction = statementExpress.substring(thenIdx+4,elseIdx);
		String falseFunction = statementExpress.substring(elseIdx+4,statementExpress.length());
		StatementComponents sc = new StatementComponents();
		sc.setCondition(condition);
		sc.setTrueFunction(trueFunction);
		sc.setFalseFunction(falseFunction);
		return sc;
//		int length = statementExpress.length();
//		StringBuilder sb = new StringBuilder();
//		for(int i=0;i<statementExpress.length();i++)
//		{
//			char c = statementExpress.charAt(i);
//			while (c!=' ')
//			{
//				sb.append(c);
//			}
//			if (sb.toString().equalsIgnoreCase("IF"))
//			{
//				sb.delete(0, arg1)
//			}
//			else if
//		}
//		return null;
	}


	public static Object execFunction(String functionExpress, RowMetaInterface rowMeta,Object[] rowData) throws KettleException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(functionExpress.trim().equals("''") || functionExpress.trim().equals("\"\""))
			return new String().trim();
		if(functionExpress.indexOf("(")>-1)
		{
		String methodName  = functionExpress.substring(0,functionExpress.indexOf("("));
		String  parametersString =  functionExpress.substring(functionExpress.indexOf("(")+1, functionExpress.lastIndexOf(")"));
		Object [] parameters = parseParametersString(parametersString);
		Object [] newParameters = new Object[parameters.length];
		for(int i=0; i<parameters.length;i++)
		{
			String parameter = parameters[i].toString();
			newParameters[i] = getParameterValue(parameter);

		}
		return execNestedFunction(methodName,newParameters, rowMeta,rowData);
		}else
		{
			int index = rowMeta.indexOfValue(functionExpress);
			if(index>-1)
				return rowData[index];
			else
				throw new KettleException("No Column found! Column name="+functionExpress);
		}
	}
	
	
	public static Object getParameterValue(String parameter)
	{
		Object parameterVa = null;
		if(isInteger(parameter))
		{
			int intvalue = java.lang.Integer.parseInt(parameter);
			parameterVa = intvalue;
		}
		else
			parameterVa = parameter;
		return parameterVa;
	}
	
	/**
	 * parse parameter string to get parameters
	 * @param parametersString
	 * @return
	 */
	private static Object[] parseParametersString(String parametersString) {
		int length = parametersString.length();
		int i=0;
		ArrayList<Object> list = new ArrayList<Object>();
		int level = 0;
		int start = 0;
		while( i < length)
		{
			if(parametersString.charAt(i)==',' && level==0 )
			{
				list.add(getParameterValue(parametersString.substring(start,i)));
			    start=i+1;
			    
			}else if (parametersString.charAt(i)=='(' )
			{
				level++;
			}else if (parametersString.charAt(i)==')' )
			{
				level--;
			}
				i++;
		}
		list.add(getParameterValue(parametersString.substring(start,parametersString.length())));
		return list.toArray();
	}

	public static boolean isInteger(String parameter)
	{
		try{
		java.lang.Integer.parseInt(parameter);
		return true;
		}catch(Exception e)
		{
			return false;
		}
	}
	
	public static Object execNestedFunction(String methodName, Object[] parameters, RowMetaInterface rowMeta, Object[] rowData) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, KettleException
	{
		if(!hasNested(parameters))
		{
			return execOneFunction(methodName,parameters, rowMeta, rowData);
		}
		else
		{
			Object[] newParameters = new Object[parameters.length];
			for (int i=0;i<parameters.length;i++)
			 {
				 String parameter= parameters[i].toString();
				 Object parameterValue;
				 if (parameter.indexOf("(")>-1)
				 {
					 String nestedMethodName = parameter.substring(0,parameter.indexOf("("));
					 String nestedMethodParametersString =  parameter.substring(parameter.indexOf("(")+1, parameter.lastIndexOf(")"));
					 Object[] nestedMethodParameter = parseParametersString(nestedMethodParametersString);
					 parameterValue = execNestedFunction(nestedMethodName,nestedMethodParameter,rowMeta,rowData);
					 if(parameterValue instanceof String)
						 parameterValue = "\""+parameterValue+"\"";
					 newParameters[i]=parameterValue;
				 }
				 else
				 {
					 newParameters[i]=getParameterValue(parameters[i].toString());
				 }
			 }
			return execNestedFunction(methodName,newParameters,rowMeta,rowData);
		}		
	}

	/**
	 * execute one method
	 * @param method: a method string without nested method
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws ClassNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws KettleException 
	 */
	public static Object execOneFunction(String methodName, Object[] parameters, RowMetaInterface rowMeta,Object[] rowData) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, KettleException
	{
//        ClassLoader classLoaderUtil.loadJarPath("kettle/4.3/");
//        //init environment
//        Class<?> kttleEnvironmentClass = Class.forName("org.pentaho.di.core.KettleEnvironment", true, classLoaderUtil);
        String className= "org.pentaho.di.core.util.function."+"Java"+StringUtil.initCap(methodName);
		Class<?> clazz = Class.forName(className);
		Class<?>[] parameterClass = null;
		Object[] realParameters = new Object[parameters.length];
		//execute trans
		if(parameters!=null && parameters.length>0)
		{
			 parameterClass = new Class[parameters.length];
			 
			 for (int i=0;i<parameters.length;i++)
			 {
				 parameterClass[i]= parameters[i].getClass();
				 if(parameters[i].toString().startsWith("\"") && parameters[i].toString().endsWith("\""))
				 {
					 realParameters[i]= parameters[i].toString().substring(1,parameters[i].toString().length()-1);
				 }
				 else if(isInteger(parameters[i].toString()))
				 {
					 realParameters[i]= parameters[i];
				 }
				 else
				 {
					 String columnName = parameters[i].toString();
					 int idx = rowMeta.indexOfValue(columnName);
					 if(idx>-1)
						 realParameters[i]= rowData[idx];
					 else
						 throw new KettleException("Column name "+ columnName +" not found in function "+ methodName);
				 }
			 }
		}
        Method method = clazz.getDeclaredMethod(methodName, parameterClass);
        Object result = method.invoke(clazz, realParameters);
        return result;
	}
	
	private static boolean hasNested(Object[] parameters) {
		for (int i=0;i<parameters.length;i++)
		 {
			 String parameter= parameters[i].toString();
			 if (parameter.indexOf("(")>-1)
			 {
				 return true;
			 }
		 }
		return false;
	}

//
//	public static Object exec(String functionExpress) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
//	{
//		String methodName  = functionExpress.substring(0,functionExpress.indexOf("("));
//		String  parametersString =  functionExpress.substring(functionExpress.indexOf("(")+1, functionExpress.lastIndexOf(")"));
//		Object [] parameters = parseParametersString(parametersString);
//		Object [] newParameters = new Object[parameters.length];
//		for(int i=0; i<parameters.length;i++)
//		{
//			String parameter = parameters[i].toString();
//			newParameters[i] = getParameterValue(parameter);
//
//		}
//		return execNestedFunction(methodName,newParameters);
//	}
//	
//	public static Object execNestedFunction(String methodName, Object[] parameters) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
//	{
//		if(!hasNested(parameters))
//		{
//			return execOneFunction(methodName,parameters);
//		}
//		else
//		{
//			Object[] newParameters = new Object[parameters.length];
//			for (int i=0;i<parameters.length;i++)
//			 {
//				 String parameter= parameters[i].toString();
//				 Object parameterValue;
//				 if (parameter.indexOf("(")>-1)
//				 {
//					 String nestedMethodName = parameter.substring(0,parameter.indexOf("("));
//					 String nestedMethodParametersString =  parameter.substring(parameter.indexOf("(")+1, parameter.lastIndexOf(")"));
//					 Object[] nestedMethodParameter = parseParametersString(nestedMethodParametersString);
//					 parameterValue = execNestedFunction(nestedMethodName,nestedMethodParameter);
//					 newParameters[i]=parameterValue;
//				 }
//				 else
//				 {
//					 newParameters[i]=getParameterValue(parameters[i].toString());
//				 }
//			 }
//			return execNestedFunction(methodName,newParameters);
//		}		
//	}
	

//	/**
//	 * execute one method
//	 * @param method: a method string without nested method
//	 * @return
//	 * @throws SecurityException 
//	 * @throws NoSuchMethodException 
//	 * @throws ClassNotFoundException 
//	 * @throws InvocationTargetException 
//	 * @throws IllegalArgumentException 
//	 * @throws IllegalAccessException 
//	 */
//	public static Object execOneFunction(String methodName, Object[] parameters) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
//	{	
////        ClassLoader classLoaderUtil.loadJarPath("kettle/4.3/");
////        //init environment
////        Class<?> kttleEnvironmentClass = Class.forName("org.pentaho.di.core.KettleEnvironment", true, classLoaderUtil);
//        String className= "org.pentaho.di.core.util.function."+"Java"+StringUtil.initCap(methodName);
//		Class<?> clazz = Class.forName(className);
//		Class<?>[] parameterClass = null;
//		//execute trans
//		if(parameters!=null && parameters.length>0)
//		{
//			 parameterClass = new Class[parameters.length];
//			 for (int i=0;i<parameters.length;i++)
//			 {
//				 parameterClass[i]= parameters[i].getClass();
//			 }
//		}
//        Method method = clazz.getDeclaredMethod(methodName, parameterClass);
//        Object result = method.invoke(clazz, parameters);
//        return result;
//	}
	public static class StatementComponents
	{
		
		private String condition;
		private String trueFunction;
		private String falseFunction;
		
		public String getCondition() {
			return condition;
		}
		public void setCondition(String condition) {
			this.condition = condition;
		}
		public String getTrueFunction() {
			return trueFunction;
		}
		public void setTrueFunction(String trueFunction) {
			this.trueFunction = trueFunction;
		}
		public String getFalseFunction() {
			return falseFunction;
		}
		public void setFalseFunction(String falseFunction) {
			this.falseFunction = falseFunction;
		}
		
		
	}

}


