package org.example;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.*;

@JsonDeserialize(using = ColorDeserializer.class)
public enum Color {
    blau(1L, Set.of("blau", "blue")),
    gruen(2L, Set.of("gruen", "grün", "green")),
    violett(3L, Set.of("violett", "purple")),
    rot(4L, Set.of("rot", "red")),
    gelb(5L, Set.of("gelb", "yellow")),
    tuerkis(6L, Set.of("tuerkis", "türkis", "turquoise")),
    weiss(7L, Set.of("weiß", "weiss", "white"));

    private final Long colorId;
    private final Set<String> colorNames;
    private static final Map<Long, Color> colorIdMap = new HashMap<>();
    private static final Map<String, Color> colorNameMap = new HashMap<>();

    static {
        for (Color color : values()) {
            colorIdMap.put(color.colorId, color);
            for (String name : color.colorNames) {
                colorNameMap.put(name.toLowerCase(), color);
            }
        }
    }

    Color(Long id, Set<String> names) {
        this.colorId = id;
        this.colorNames = names;
    }

    public Long getColorId() {
        return colorId;
    }

    public Set<String> getColorNames() {
        return colorNames;
    }

    public static Color getColorById(Long id) {
        Color color = colorIdMap.get(id);
        if (color == null) {
            throw new IllegalArgumentException("Wrong Color ID: " + id);
        }
        return color;
    }

    public static Color getColorByName(String name) {
        Color color = colorNameMap.get(name.toLowerCase());
        if (color == null) {
            throw new IllegalArgumentException("Wrong Color Name: " + name);
        }
        return color;
    }
}
