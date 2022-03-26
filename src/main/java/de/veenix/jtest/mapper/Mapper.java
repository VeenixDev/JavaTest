package de.veenix.jtest.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class Mapper {

    public static <ENTITY, DTO> DTO mapToDTO(Class<DTO> dto, ENTITY entity) {
        List<Field> entityFields = List.of(entity.getClass().getDeclaredFields());
        List<Field> dtoFields = List.of(dto.getDeclaredFields());

        // DTO Field - Entity Field
        HashMap<Field, Field> fieldMap = new HashMap<>();

        for (Field field : entityFields) {
            MapperOptions options = field.getAnnotation(MapperOptions.class);

            if (options != null) {
                if (options.ignore()) {
                    continue;
                }

                String rename = options.renameTo();
                if (!rename.isEmpty()) {
                    Field dtoField = getFieldIfExists(dtoFields, rename);

                    if(dtoField != null && field.getType() == dtoField.getType()) {
                        fieldMap.put(dtoField, field);
                    }
                    continue;
                }
            }

            Field dtoField = getFieldIfExists(dtoFields, field.getName());
            if(dtoField != null && field.getType() == dtoField.getType()) {
                fieldMap.put(dtoField, field);
            }
        }
        DTO dtoInstance = null;

        try {
            dtoInstance = dto.getDeclaredConstructor().newInstance();

            for(Field dtoField : fieldMap.keySet()) {
                Class<?> dtoClass = dtoInstance.getClass();

                Field declaredField = dtoClass.getDeclaredField(dtoField.getName());
                Field entityField = fieldMap.get(dtoField);

                boolean canAccess = declaredField.canAccess(dtoInstance);
                boolean entityAccess = entityField.canAccess(entity);

                entityField.setAccessible(true);

                declaredField.setAccessible(true);
                declaredField.set(dtoInstance, fieldMap.get(dtoField).get(entity));
                declaredField.setAccessible(canAccess);

                entityField.setAccessible(entityAccess);
            }

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return dtoInstance;
    }

    private static Field getFieldIfExists(List<Field> fields, String name) {
        for(Field field : fields) {
            if(field.getName().equals(name)) {
                return field;
            }
        }

        return null;
    }

}
