package com.company.config;

import java.lang.reflect.Field;

public final class ConfigValidator {

    private ConfigValidator() {}

    public static void validate(Object config) {

        Class<?> clazz = config.getClass();

        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(RangeCheck.class)) {
                continue;
            }

            RangeCheck range = field.getAnnotation(RangeCheck.class);

            field.setAccessible(true);

            try {
                int value = field.getInt(config);

                if (value < range.min() || value > range.max()) {
                    throw new ConfigValidationException(
                        String.format(
                            "Invalid value for %s: %d (allowed range: %d - %d)",
                            field.getName(),
                            value,
                            range.min(),
                            range.max()
                        )
                    );
                }

                SystemConfig.logSuccess(
                    field.getName() + " validated successfully"
                );

            } catch (IllegalAccessException e) {
                throw new ConfigValidationException(
                    "Unable to access field: " + field.getName()
                );
            }
        }
    }
}
