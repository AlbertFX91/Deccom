package com.deccom.domain.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class Core_ConnectionDeserializer extends JsonDeserializer<Core_Connection>{

	@Override
	public Core_Connection deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        String _connectionClass = node.get("_connectionClass").textValue();
        try {
			Core_Connection res = (Core_Connection) Class.forName(_connectionClass).newInstance();
			List<String> fields = new ArrayList<>();
			node.fieldNames().forEachRemaining(fields::add);
			Map<String, String> data = fields.stream()
					.filter(x->!x.equals("_connectionClass"))
					.collect(Collectors.toMap(x->x, x->node.get(x).textValue()));
			if(connectionVerification(res, data)) {
				propertiesInjection(res, data);
				return res;
			}else {
				throw new RuntimeException("The connection data doesn't match with its class");
			}
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static <T> void propertiesInjection(Object o,
			Map<String, T> properties) {
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

	private static Boolean connectionVerification(Core_Connection connection,
			Map<String, String> connectionData) {
		return Arrays.stream(connection.getClass().getDeclaredFields())
				.allMatch(f -> connectionData.containsKey(f.getName()));
	}
	
}
