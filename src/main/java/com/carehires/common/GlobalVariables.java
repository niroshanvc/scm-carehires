package com.carehires.common;

import java.util.HashMap;
import java.util.Map;

public class GlobalVariables {

    // Private constructor to prevent instantiation
    private GlobalVariables() {
        // Prevent instantiation
    }

    // Store variables in a map
    private static final Map<String, Object> variables = new HashMap<>();

    // Method to add or update variables
    public static void setVariable(String key, Object value) {
        variables.put(key, value);
    }

    // Method to get a variable by key
    public static Object getVariable(String key) {
        return variables.get(key);
    }

    // Method to get a variable by key with type casting
    public static <T> T getVariable(String key, Class<T> type) {
        return type.cast(variables.get(key));
    }

    // Method to remove a variable by key
    public static void removeVariable(String key) {
        variables.remove(key);
    }

    // Method to check if a variable exists
    public static boolean containsVariable(String key) {
        return variables.containsKey(key);
    }

    // Method to store the incremented value for an entity type (e.g., agency, provider)
    public static void storeIncrementedValue(String entityType, int incrementValue) {
        setVariable(entityType + "_incrementValue", incrementValue);
    }

    // Method to retrieve the incremented value for an entity type
    public static int getIncrementedValue(String entityType) {
        Object value = getVariable(entityType + "_incrementValue");
        if (value == null) {
            return 1; // Default value if the increment value is not set
        }
        return (int) value;
    }
}
