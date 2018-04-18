package com.deccom.domain.core.fields;

public class DeccomFieldClass {
	private InputType type;
	private String component;
	private String name;
	private Boolean display;

	public static DeccomFieldClass create() {
		return new DeccomFieldClass();
	}

	public static DeccomFieldClass create(String name) {
		return new DeccomFieldClass(name, InputType.TEXT, "", true);
	}
	
	public static DeccomFieldClass create(String name, InputType type, String component, Boolean display) {
		return new DeccomFieldClass(name, type, component, display);
	}

	public static DeccomFieldClass create(String name, DeccomField field) {
		return new DeccomFieldClass(name, field.type(), field.component(), field.display());
	}

	public DeccomFieldClass() {
		this.name = "";
		this.type = InputType.TEXT;
		this.component = "";
		this.display = true;
	}

	public DeccomFieldClass(String name, InputType type, String component, Boolean display) {
		this.name = name;
		this.type = type;
		this.component = component;
		this.display = display;
	}

	public InputType getType() {
		return type;
	}

	public void setType(InputType type) {
		this.type = type;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((component == null) ? 0 : component.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((display == null) ? 0 : display.hashCode());
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
		DeccomFieldClass other = (DeccomFieldClass) obj;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Field [type=" + type + ", component=" + component + ", name=" + name + "]";
	}

}
