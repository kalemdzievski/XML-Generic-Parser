package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ConvertUtil {

    private static Map<Class<?>, Class<?>> primitiveMap = new HashMap<Class<?>, Class<?>>();
    static {
		primitiveMap.put(boolean.class, Boolean.class);
		primitiveMap.put(byte.class, Byte.class);
		primitiveMap.put(char.class, Character.class);
		primitiveMap.put(short.class, Short.class);
		primitiveMap.put(int.class, Integer.class);
		primitiveMap.put(long.class, Long.class);
		primitiveMap.put(float.class, Float.class);
		primitiveMap.put(double.class, Double.class);
    }

    public static Object convert(String value, Class<?> destClass) {
    	
    	if (value == null || destClass == String.class || "".equals(value))
    		return value;

		if (destClass.isPrimitive())
		    destClass = primitiveMap.get(destClass);
		Method method = null;
		try {
			method = destClass.getMethod("valueOf", String.class);
		    return method.invoke(destClass, value);
		} 
		catch (NoSuchMethodException e) {
		    if (destClass == Character.class)
		    	return Character.valueOf(value.charAt(0));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	
		return value;
    }
}