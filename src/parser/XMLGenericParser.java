package parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import tree.Node;
import tree.Tree;
import util.ConvertUtil;
import util.FieldUtil;
import util.StringUtil;

public class XMLGenericParser extends DefaultHandler{

	private String currentValue;
	private Class<?> resultObjectType;
	private Tree<Object> tree;
	private ArrayList<String> tags;
	
	public XMLGenericParser(Class<?> resultClass){
		
		currentValue = "";
		resultObjectType = resultClass;
		tree = new Tree<Object>();
		tags = new ArrayList<String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(tree.getCurrentParentNode() == null || tags.size() > tree.getCurrentParentNode().getLevel()){
			
			try {
				
				Node<Object> currentNode = null;
				
				if(tree.getRoot() == null) {
					currentNode = new Node<Object>(resultObjectType.newInstance(), 1);
				}
				else {
					
					String currentTag = tags.get(tags.size() - 1);
					Object lastObject = tree.getCurrentParentNode().getData();
					Field objectField = FieldUtil.getField(lastObject.getClass(), currentTag);
					Class<?> classType;
					
					if (objectField.getType() == ArrayList.class || objectField.getType() == LinkedList.class) {
						ParameterizedType genericType = (ParameterizedType) objectField.getGenericType();
						classType = (Class<?>) genericType.getActualTypeArguments()[0];
					}
					else 
						classType = objectField.getType();

					currentNode = new Node<Object>(classType.newInstance(), tree.getCurrentParentNode().getLevel() + 1, tree.getCurrentParentNode());
				}
				
				if(currentNode != null) {
					tree.addChild(tree.getCurrentParentNode(), currentNode);
				}
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		
		tags.add(qName);
		currentValue = "";
		
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String value = new String(ch, start, length);
		if (value.length() > 0) {
			StringBuilder sb = new StringBuilder(currentValue);
			sb.append(value);
			currentValue = sb.toString();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		// Simple element, set it to the current last object
		// Primitive types are converted from string to the original type
		// Arrays of simple elements: get the generic type and convert to primitive type
		if (tags.size() >= tree.getCurrentParentNode().getLevel() + 1) {
			
			String lastTag = tags.remove(tags.size() - 1);
			Object lastObject = tree.getCurrentParentNode().getData();

			if (currentValue != null) {
				try {
					Field objectField = FieldUtil.getField(lastObject.getClass(), lastTag); // also returns field with annotation
					
					if (objectField.getType() == ArrayList.class || objectField.getType() == LinkedList.class) {
		        		
		        		if(objectField.get(lastObject) == null)
		        			objectField.set(lastObject, objectField.getType().newInstance());
		        		
		        		Method method = objectField.get(lastObject).getClass().getDeclaredMethod("add", Object.class);
		        		
		        		ParameterizedType genericType = (ParameterizedType) objectField.getGenericType();
		        		Class<?> classType = (Class<?>) genericType.getActualTypeArguments()[0];
		        		
		        		method.invoke(objectField.get(lastObject), ConvertUtil.convert(currentValue, classType));
		        	}
					else if (objectField.getType().isPrimitive()) {
						
						String type = objectField.getType().getSimpleName();
						Class<?> classType = objectField.getType();
		        		Method fieldSetMethod = objectField.getClass().getDeclaredMethod("set" + StringUtil.capitalizeString(type), Object.class, classType);
		        		fieldSetMethod.invoke(objectField, lastObject, ConvertUtil.convert(currentValue, classType));
					}
		        	else {
						objectField.set(lastObject, currentValue);
		        	}

				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		} else {

			// Don't remove the first/root object
			if (tags.size() == tree.getCurrentParentNode().getLevel() && tree.getCurrentParentNode().getLevel() > 1) {
				
				String lastTag = tags.remove(tags.size() - 1);
				Object lastObject = tree.getCurrentParentNode().getData();
				Object parentObject = tree.getCurrentParentNode().getParent().getData();

				try {
					Field objectField = FieldUtil.getField(parentObject.getClass(), lastTag); // also returns field with annotation
					
					if (objectField.getType() == ArrayList.class || objectField.getType() == LinkedList.class) {
		        		
		        		if(objectField.get(parentObject) == null)
		        			objectField.set(parentObject, objectField.getType().newInstance());
		        		
		        		Method method = objectField.get(parentObject).getClass().getDeclaredMethod("add", Object.class);
		        		method.invoke(objectField.get(parentObject), lastObject);
		        	}
		        	else {
		        		objectField.set(parentObject, lastObject);
		        	}

				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
				tree.goUp();
			}
		}

		currentValue = "";
	}
	
	public Object getResultObject() {
		
		if(tree.getCurrentParentNode().getLevel() > 0)
			return tree.getRoot().getData();
		return null;
	}

}
