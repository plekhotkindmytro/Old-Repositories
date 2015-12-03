package com.plekhotkin.sitemap.model;

import java.util.HashMap;
import java.util.Map;

public enum Priority {
    NONE("none"), RANDOM("random");

    private final String value;

    private static final Map<String, Priority> map = new HashMap<String, Priority>();

    static {
        for (Priority value : values()) {
            map.put(value.value, value);
        }
    }

    private Priority(String value) {
        this.value = value;
    }

    public static Priority lookup(String value) {
        return map.get(value);
    }
}
