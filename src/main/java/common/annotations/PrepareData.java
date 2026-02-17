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

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface List {
        PrepareData[] value();
    }
}
