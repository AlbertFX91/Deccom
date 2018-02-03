package com.deccom.domain.core;

import java.util.Map;

import javax.validation.Valid;

public class Core_ControlVarCreation {
	@Valid
	private Core_ControlVar controlvar;
	@Valid
	private Map<String, String> connectionData;
	private String _connectionClass;
	
	public Core_ControlVar getControlvar() {
		return controlvar;
	}
	public void setControlvar(Core_ControlVar controlvar) {
		this.controlvar = controlvar;
	}
	public Map<String, String> getConnectionData() {
		return connectionData;
	}
	public void setConnectionData(Map<String, String> connectionData) {
		this.connectionData = connectionData;
	}
	public String get_connectionClass() {
		return _connectionClass;
	}
	public void set_connectionClass(String _connectionClass) {
		this._connectionClass = _connectionClass;
	}
	
	
	
}
