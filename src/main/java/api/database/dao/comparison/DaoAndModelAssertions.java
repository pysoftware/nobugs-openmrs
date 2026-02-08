package api.database.dao.comparison;

import org.opentest4j.AssertionFailedError;

public final class DaoAndModelAssertions {

    private static DaoModelComparator comparator;

    static {
        try {
            DaoComparisonConfig config = new DaoComparisonConfig();
            comparator = new DaoModelComparator(config);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Не удалось инициализировать DaoModelComparator: " + e.getMessage());
        }
    }
    private DaoAndModelAssertions() {
        // утилитный класс — не инстанцируется
    }

    public static DaoModelAssertion assertThat(Object model, Object dao) {
        return new DaoModelAssertion(model, dao);
    }

    public static class DaoModelAssertion {

        private final Object model;
        private final Object dao;

        private DaoModelAssertion(Object model, Object dao) {
            this.model = model;
            this.dao = dao;
        }

        /**
         * Проверяет соответствие модели и DAO по правилам из dao-comparison.properties.
         * Если не совпадает — бросает AssertionFailedError (JUnit-friendly).
         */
        public void match() {
            try {
                comparator.compare(model, dao);
            } catch (ComparisonException e) {
                throw new AssertionFailedError(
                        "Модель и DAO не совпадают: " + e.getMessage(),
                        e
                );
            }
        }

        /**
         * Альтернатива: возвращает boolean (если не хочется исключений)
         */
        public boolean matches() {
            try {
                comparator.compare(model, dao);
                return true;
            } catch (ComparisonException ignored) {
                return false;
            }
        }

        // Можно добавить fluent-методы в будущем, например:
        // .ignoringFields("createdAt", "updatedAt")
        // но пока оставляем просто .match()
    }
}
