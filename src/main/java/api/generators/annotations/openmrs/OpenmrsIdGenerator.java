package api.generators.annotations.openmrs;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class OpenmrsIdGenerator {
    private static final Random random = new Random();
    private static final LuhnMod30IdentifierValidator validator =
            new LuhnMod30IdentifierValidator();

    public static String generateOpenmrsId() {
        // base identifier (без check digit)
        String base = String.valueOf(ThreadLocalRandom.current().nextInt(10000, 99999));

        // validator добавляет check digit
        return validator.getValidIdentifier(base);
    }
}