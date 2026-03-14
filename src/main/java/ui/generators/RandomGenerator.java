package ui.generators;

import ui.enums.RadioOption;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {
    public static <E extends Enum<E> & RadioOption> E randomEnum(Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }
}
