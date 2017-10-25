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
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
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
		RESTExtractor rest = _rest.newInstance();
		
		properties = Maps.newHashMap();
		properties.put("url", "https://jsonplaceholder.typicode.com/users");
		properties.put("jsonpath", "$.[5].id");

		propertiesInjection(rest, properties);
		
		System.out.println("RESTExtractor: " + rest.getData());
		
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
