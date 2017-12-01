package com.deccom.domain.core;

public class Core_Connection {
	protected String _class;
	protected String _extractorClass;

	Core_Connection(){
		super();
		this._class = this.getClass().getName();
	}
	
	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}
	
	public String get_extractorClass() {
		return _extractorClass;
	}

	public void set_extractorClass(String _extractorClass) {
		this._extractorClass = _extractorClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_class == null) ? 0 : _class.hashCode());
		result = prime * result + ((_extractorClass == null) ? 0 : _extractorClass.hashCode());
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
		Core_Connection other = (Core_Connection) obj;
		if (_class == null) {
			if (other._class != null)
				return false;
		} else if (!_class.equals(other._class))
			return false;
		if (_extractorClass == null) {
			if (other._extractorClass != null)
				return false;
		} else if (!_extractorClass.equals(other._extractorClass))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Core_Connection [_class=" + _class + ", _extractorClass=" + _extractorClass + "]";
	}
	
	
}
