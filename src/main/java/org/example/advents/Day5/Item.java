package org.example.advents.Day5;

public enum Item {
    SEED, SOIL, FERTILIZER, WATER, LIGHT, TEMPERATURE, HUMIDITY, LOCATION;
    public static Item parseItem(String itemString) {
        return switch (itemString){
            case "seed" -> SEED;
            case "soil" -> SOIL;
            case "fertilizer" -> FERTILIZER;
            case "water" -> WATER;
            case "light" -> LIGHT;
            case "temperature" -> TEMPERATURE;
            case "humidity" -> HUMIDITY;
            case "location" -> LOCATION;
            
            default -> throw new IllegalStateException("Unexpected value: " + itemString);
        };
    }
}
