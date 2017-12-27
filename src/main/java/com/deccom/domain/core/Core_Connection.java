package com.deccom.domain.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = Core_ConnectionDeserializer.class)
public interface Core_Connection {
}
