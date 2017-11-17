package com.deccom.core;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.collect.Maps;

import net.logstash.logback.encoder.org.apache.commons.lang.ClassUtils;

public class CoreMain {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// DB DATA
		String className = "com.deccom.core.RESTExtractor";
		Map<String, String> attribs = Maps.newHashMap();
		attribs.put("url", "https://jsonplaceholder.typicode.com/users");
		attribs.put("jsonpath", "$.[5].id");
		
		Object wrapper = Class.forName(className).newInstance();
		if(wrapper instanceof DataExtractor) {
			DataExtractor dataExtractor = (DataExtractor) wrapper;
			propertiesInjection(dataExtractor, attribs);
			System.out.println("Data extracted ["+className+"]: "+dataExtractor.getData());
		}
	
		// DB DATA
		className = "com.deccom.core.SQLExtractor";
		attribs = Maps.newHashMap();
		attribs.put("username", "developer");
		attribs.put("password", "developer");
		attribs.put("url", "jdbc:mysql://localhost:3306/deccom");
		attribs.put("query", "select age from author where idauthor='1'");
			
		wrapper = Class.forName(className).newInstance();
		
		if(wrapper instanceof DataExtractor) {
			DataExtractor dataExtractor = (DataExtractor) wrapper;
			propertiesInjection(dataExtractor, attribs);
			System.out.println("Data extracted ["+className+"]: "+dataExtractor.getData());
		}
		

		Map<Class<? extends DataExtractor>, List<Class<?>>> res = getClassInterface(DataExtractor.class);
		
		for(Entry<Class<? extends DataExtractor>, List<Class<?>>> e: res.entrySet()) {
			System.out.println("["+e.getKey().getName()+"]: "+e.getValue().toString());
		}
		
		
		
		/*
		Class<SQLExtractor> _sql = SQLExtractor.class;
		SQLExtractor sql = _sql.newInstance();
		
		Map<String, String> properties = Maps.newHashMap();
		properties.put("username", "developer");
		properties.put("password", "developer");
		properties.put("url", "jdbc:mysql://localhost:3306/deccom");
		properties.put("query", "select age from author where idauthor='1'");
		
		propertiesInjection(sql, properties);
		System.out.println("SQLExtractor: " + sql.getData());
		
		Class<RESTExtractor> _rest = RESTExtractor.class;
		RESTExtractor rest2 = _rest.newInstance();
		
		properties = Maps.newHashMap();
		properties.put("url", "https://jsonplaceholder.typicode.com/users");
		properties.put("jsonpath", "$.[5].id");

		propertiesInjection(rest2, properties);
		
		System.out.println("RESTExtractor: " + rest2.getData());
		*/
		
		
	}
	
	private static <T> void propertiesInjection(Object o, Map<String, T> properties) {
		for (Entry<String, T> property : properties.entrySet()) {
			String f = property.getKey();
			T v = property.getValue();
			Class<?> clazz = o.getClass();
			try {
				Field field = clazz.getDeclaredField(f);
				field.setAccessible(true);
				field.set(o, v);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private static <T> List<Class<?>> getAllInterfaces(Class<T> cls){
		return ClassUtils.getAllInterfaces(cls);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private static <T> Set<Class<? extends T>> getSubInterfacesOf(Class<T> cls){
		Reflections reflections = new Reflections(
				new ConfigurationBuilder()
				.setUrls(Arrays.asList(ClasspathHelper.forClass(cls))));
		Set<?> subTypes = reflections.getSubTypesOf(cls);
		return subTypes.stream()
				.map((o)->(Class<? extends T>) o)
				.filter((c)->c.isInterface())
				.collect(Collectors.toSet());
	}
	
	@SuppressWarnings("unchecked")
	private static <T> Set<Class<? extends T>> getSubClassesOf(Class<T> cls){
		Reflections reflections = new Reflections(
				new ConfigurationBuilder()
				.setUrls(Arrays.asList(ClasspathHelper.forClass(cls))));
		Set<?> subTypes = reflections.getSubTypesOf(cls);
		return subTypes.stream()
				.map((o)->(Class<? extends T>) o)
				.filter((c)->!c.isInterface())
				.collect(Collectors.toSet());
	}
	
	@SuppressWarnings("unused")
	private static <T> Map<Class<? extends T>, List<Class<?>>> getClassInterface(Class<T> parent){
		Map<Class<? extends T>, List<Class<?>>> res = Maps.newHashMap();
		Set<Class<? extends T>> classes = getSubClassesOf(parent);
		
		classes.forEach((c)->res.put(c, Arrays.asList(c.getInterfaces())));

		
		return res;
		
	}

}
