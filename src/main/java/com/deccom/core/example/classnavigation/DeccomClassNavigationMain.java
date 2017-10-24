package com.deccom.core.example.classnavigation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.collect.Maps;

import net.logstash.logback.encoder.org.apache.commons.lang.ClassUtils;

public class DeccomClassNavigationMain {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
			
		System.out.println("All interfaces which this class extends: " + DeccomFacebookDataRecover.class.getName());
		for(Class<?> c: getAllInterfaces(DeccomFacebookDataRecover.class)) {
			System.out.println("+ " + c.getName());
		}
		
		System.out.println("------------------------------------------------");
		
		System.out.println("All interfaces which extends from: " + DeccomDataRecover.class.getName());
		Set<Class<? extends DeccomDataRecover>> interfaces = getSubInterfacesOf(DeccomDataRecover.class);
		for(Class<?> c: interfaces) {
			System.out.println("+ " + c.getName());
		}
		
		System.out.println("------------------------------------------------");
		
		System.out.println("All subclases which extends from: " + DeccomDataRecover.class.getName());
		Set<Class<? extends DeccomDataRecover>> classes = getSubClassesOf(DeccomDataRecover.class);
		for(Class<?> c: classes) {
			System.out.println("+ "+c.getName()+": " + Arrays.toString(c.getInterfaces()));
		}
		
		System.out.println("------------------------------------------------");
		
		System.out.println("All Classes + Interfaces implemented: " + DeccomDataRecover.class.getName());
		Map<Class<? extends DeccomDataRecover>, List<Class<?>>> map = getClassInterface(DeccomDataRecover.class);
		for(Class<? extends DeccomDataRecover> c: map.keySet()) {
			System.out.println("+ " + c.getName() + ": " +map.get(c));
			System.out.println("+ getData: " + c.newInstance().getData());
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private static <T> List<Class<?>> getAllInterfaces(Class<T> cls){
		return ClassUtils.getAllInterfaces(cls);
	}
	
	@SuppressWarnings("unchecked")
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
	
	private static <T> Map<Class<? extends T>, List<Class<?>>> getClassInterface(Class<T> parent){
		Map<Class<? extends T>, List<Class<?>>> res = Maps.newHashMap();
		Set<Class<? extends T>> classes = getSubClassesOf(parent);
		
		classes.forEach((c)->res.put(c, Arrays.asList(c.getInterfaces())));

		
		return res;
		
	}

}
