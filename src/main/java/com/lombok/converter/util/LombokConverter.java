package com.lombok.converter.util;

import com.google.gson.Gson;
import com.lombok.converter.JSONCharacters;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class LombokConverter {


    private static final Gson GSON = new Gson();

    public static <T> T convertLombokToStringToObject(String lombokString, Class<T> klass) {
        // gson is very flexible
        return GSON.fromJson(convertLombokStringToJson(lombokString, klass), klass);

    }

    private static <T> String convertLombokStringToJson(String lombokString, Class<T> klass) {
        String jsonFormatted = lombokString;

        List<Class<?>> classList = new ArrayList<>();

        getClassNamesRecursively(klass, classList);

        for (Class<?> classElement : classList) {
            String className = classElement.getSimpleName();
            jsonFormatted = jsonFormatted.contains(className)
                    ? jsonFormatted.replaceAll(className, "")
                    : jsonFormatted;
        }

        for (JSONCharacters character : JSONCharacters.values()) {
            char currentChar = character.getOldChar();
            char newChar = character.getNewChar();
            jsonFormatted = jsonFormatted.contains(String.valueOf(currentChar))
                    ? jsonFormatted.replace(currentChar, newChar)
                    : jsonFormatted;
        }


        return jsonFormatted;
    }

    private static void getClassNamesRecursively(Class<?> rootClass, List<Class<?>> classList) {
        if (rootClass != null) {
            classList.add(rootClass);
            for (Field declaredField : rootClass.getDeclaredFields()) {
                if (!declaredField.getType().isPrimitive()
                        && !declaredField.getType().getPackageName().startsWith("java.")) {
                    getClassNamesRecursively(declaredField.getType(), classList);
                }
            }
        }
    }

}
