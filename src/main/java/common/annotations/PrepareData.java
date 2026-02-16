package common.annotations;

import common.extensions.Prepare;

import java.lang.annotation.*;

@Repeatable(PrepareData.List.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PrepareData {
    // Тип сущности, которую нужно создать (например: Prepare.PERSON, Prepare.PATIENT, Prepare.VISIT)
    Prepare value();

    // Количество сущностей для создания
    int count() default 1;

    // Под каким ключом сохранить список в SessionStorage
    // По умолчанию: "prepared_{type}s" → prepared_persons, prepared_orders и т.д.
    String storageKey() default "";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface List {
        PrepareData[] value();
    }
}
