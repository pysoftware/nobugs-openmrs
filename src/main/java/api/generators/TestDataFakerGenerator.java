package api.generators;
import com.github.javafaker.Faker;
import java.util.Locale;

public class TestDataFakerGenerator {
    private final Faker faker;

    public TestDataFakerGenerator() {
         this.faker = new Faker(new Locale("en"));
    }

    public String generateGivenName() {
        return faker.name().firstName();
    }

    public String generateFamilyName() {
        return faker.name().lastName();
    }

    public String generateAddress() {
        return faker.address().streetAddress();
    }

    public String generateCity() {
        return faker.address().cityName();
    }

    public String generateCountry() {
        return faker.address().country();
    }

    public String generatePostalCode() {
        return String.format("%05d", faker.number().numberBetween(10000, 100000));

    }

}

