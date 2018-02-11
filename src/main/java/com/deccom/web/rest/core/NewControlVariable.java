package com.deccom.web.rest.core;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import com.deccom.domain.core.ControlVariable;
import com.deccom.domain.core.extractor.ControlVariableExtractor;

public class NewControlVariable {
	private String extractorClass;
	private Map<String, String> extractorData;
	private ControlVariable controlVariable;
	
	public String getExtractorClass() {
		return extractorClass;
	}
	public void setExtractorClass(String extractorClass) {
		this.extractorClass = extractorClass;
	}
	public Map<String, String> getExtractorData() {
		return extractorData;
	}
	public void setExtractorData(Map<String, String> extractorData) {
		this.extractorData = extractorData;
	}
	public ControlVariable getControlVariable() {
		return controlVariable;
	}
	public void setControlVariable(ControlVariable controlVariable) {
		this.controlVariable = controlVariable;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((controlVariable == null) ? 0 : controlVariable.hashCode());
		result = prime * result + ((extractorClass == null) ? 0 : extractorClass.hashCode());
		result = prime * result + ((extractorData == null) ? 0 : extractorData.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewControlVariable other = (NewControlVariable) obj;
		if (controlVariable == null) {
			if (other.controlVariable != null)
				return false;
		} else if (!controlVariable.equals(other.controlVariable))
			return false;
		if (extractorClass == null) {
			if (other.extractorClass != null)
				return false;
		} else if (!extractorClass.equals(other.extractorClass))
			return false;
		if (extractorData == null) {
			if (other.extractorData != null)
				return false;
		} else if (!extractorData.equals(other.extractorData))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NewControlVariable [extractorClass=" + extractorClass + ", extractorData=" + extractorData
				+ ", controlVariable=" + controlVariable + "]";
	}
	
	public static ControlVariable recover(NewControlVariable ncv) {
		ControlVariable res;
		ControlVariableExtractor extractor;

		// 1. Recover Control variable
		res = ncv.getControlVariable();
		// 2. InstanciateExtractor
		extractor = getExtractor(ncv);
		// 3. Inject data in extractor
		injectData(ncv.getExtractorData(), extractor);
		// 4. Check all extractors fields are added
		// TODO
		
		res.setExtractor(extractor);
		return res;

	}
	
	private static ControlVariableExtractor getExtractor(NewControlVariable ncv) {
		ControlVariableExtractor res = null;
		String extractor = ncv.getExtractorClass();
		
		try {
			Object cls = Class.forName(extractor).newInstance();
			if (cls instanceof ControlVariableExtractor) {
				res = (ControlVariableExtractor) cls;
			}else {
				//TODO ERROR
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

	private static <T> void injectData(Map<String, T> extractorData, ControlVariableExtractor extractor) {
		Class<?> clazz = extractor.getClass();
		for (Entry<String, T> entry: extractorData.entrySet()) {
			String f = entry.getKey();
			T v = entry.getValue();
			try {
				Field field = clazz.getDeclaredField(f);
				field.setAccessible(true);
				field.set(extractor, v);
			} catch (NoSuchFieldException e) {
				// TODO
				e.printStackTrace();
			} catch (Exception e) {
				// TODO
				e.printStackTrace();
			}
		}
		
	}

	
	
	
	
}
