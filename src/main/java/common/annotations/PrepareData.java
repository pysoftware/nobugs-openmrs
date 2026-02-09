package common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PrepareData {
    /**
     * Тип сущности, которую нужно создать (например: "person", "patient", "visit")
     */
    String value();

    /**
     * Количество сущностей для создания
     */
    int count() default 1;

    /**
     * Под каким ключом сохранить список в SessionStorage
     * По умолчанию: "prepared_{type}s" → prepared_persons, prepared_orders и т.д.
     */
    String storageKey() default "";
}
