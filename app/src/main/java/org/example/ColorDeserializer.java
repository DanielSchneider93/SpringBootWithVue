package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ColorDeserializer extends JsonDeserializer<Color> {

    private static final Logger log = LoggerFactory.getLogger(ColorDeserializer.class);

    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        var colorName = p.getText().trim().toLowerCase();
        try {
            return Color.getColorByName(colorName);
        } catch (IllegalArgumentException e) {
            try {
                return Color.getColorById(p.getLongValue());
            } catch (IllegalArgumentException e1) {
                log.error("Fehler bei der Deserialisierung der Farbe: e:" + e + " e1:" + e1);
                return null;
            }
        }
    }
}