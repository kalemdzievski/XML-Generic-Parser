package util;

import java.lang.reflect.Field;

import parser.XMLParserField;

public class FieldUtil {
	
	public static Field getField(Class<?> classType, String fieldName) throws NoSuchFieldException {

		for(Field field : classType.getDeclaredFields())
			if(field.getAnnotation(XMLParserField.class) != null && field.getAnnotation(XMLParserField.class).name().equals(fieldName))
				return field;
		
		return classType.getDeclaredField(fieldName);
	}

}
