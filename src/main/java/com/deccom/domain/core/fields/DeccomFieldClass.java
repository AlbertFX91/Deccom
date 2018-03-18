package com.deccom.domain.core.fields;

public class DeccomFieldClass {
	private InputType type;
	private String component;
	private String name;

	public static DeccomFieldClass create() {
		return new DeccomFieldClass();
	}

	public static DeccomFieldClass create(String name, InputType type, String component) {
		return new DeccomFieldClass(name, type, component);
	}

	public static DeccomFieldClass create(String name, DeccomField field) {
		return new DeccomFieldClass(name, field.type(), field.component());
	}

	public DeccomFieldClass() {
		this.name = "";
		this.type = InputType.TEXT;
		this.component = "";
	}

	public DeccomFieldClass(String name, InputType type, String component) {
		this.name = name;
		this.type = type;
		this.component = component;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((component == null) ? 0 : component.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
