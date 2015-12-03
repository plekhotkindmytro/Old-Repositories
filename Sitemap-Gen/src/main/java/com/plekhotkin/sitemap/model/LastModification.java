package com.plekhotkin.sitemap.model;

import java.util.HashMap;
import java.util.Map;

public enum LastModification {
    NONE("none"), SERVER("server");

    private final String value;

    private static final Map<String, LastModification> map = new HashMap<String, LastModification>();

    static {
        for (LastModification value : values()) {
            map.put(value.value, value);
        }
    }

    private LastModification(String value) {
        this.value = value;
    }

    public static LastModification lookup(String value) {
        return map.get(value);
    }
}
