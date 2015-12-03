package com.plekhotkin.sitemap.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum (String.class)
public enum ChangeFrequency {
    @XmlEnumValue ("always")
    ALWAYS("always"), @XmlEnumValue ("hourly")
    HOURLY("hourly"), @XmlEnumValue ("daily")
    DAILY("daily"), @XmlEnumValue ("weekly")
    WEEKLY("weekly"), @XmlEnumValue ("monthly")
    MONTHLY("monthly"), @XmlEnumValue ("yearly")
    YEARLY("yearly"), @XmlEnumValue ("never")
    NEVER("never");

    private final String frequency;

    private static final Map<String, ChangeFrequency> map = new HashMap<String, ChangeFrequency>();

    static {
        for (ChangeFrequency changeFrequency : values()) {
            map.put(changeFrequency.frequency, changeFrequency);
        }
    }

    private ChangeFrequency(String value) {
        frequency = value;
    }

    public static ChangeFrequency lookup(String frequency) {
        return map.get(frequency);
    }
}
