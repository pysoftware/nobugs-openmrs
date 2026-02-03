package api.specs;

import api.configs.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Getter
public class RequestSpec {

    private static final Map<String, String> sessionCookies = new HashMap<>(); // username → JSESSIONID

    private static final String BASE_URI = Config.getProperty("apiBaseUrl") + Config.getProperty("apiVersion");
    private static final List<Filter> LOGGING_FILTERS = List.of(
            new RequestLoggingFilter(),
            new ResponseLoggingFilter()
    );

    private RequestSpec() {
    }

    private static RequestSpecification defaultBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addFilters(LOGGING_FILTERS)
                .build();
    }

    /**
     * Спецификация без авторизации
     */
    public static RequestSpecification unauthSpec() {
        return defaultBuilder();
    }

    /**
     * Спецификация для суперпользователя admin (кэширует сессию)
     */
    public static RequestSpecification adminSpec() {
        return authenticatedSpec(Config.getProperty("admin.username"), Config.getProperty("admin.password"));
    }

    /**
     * Универсальная спецификация с авторизацией по логину/паролю
     * Один раз логинится → сохраняет JSESSIONID → использует куку
     */
    public static RequestSpecification authenticatedSpec(String username, String password) {
        String jsessionId = sessionCookies.get(username);

        if (jsessionId == null) {
            // Первый вход — используем Basic Auth
            Response loginResponse = given()
                    .spec(defaultBuilder())
                    .auth().preemptive().basic(username, password)
                    .when()
                    .get("/session")
                    .then()
                    .statusCode(200)
                    .extract().response();

            // Проверяем, что залогинились
            loginResponse.then()
                    .body("authenticated", org.hamcrest.Matchers.equalTo(true));

            // Извлекаем JSESSIONID
            jsessionId = loginResponse.getCookie("JSESSIONID");

            if (jsessionId == null) {
                throw new IllegalStateException("JSESSIONID не получен после логина");
            }

            sessionCookies.put(username, jsessionId);
        }

        // Возвращаем спецификацию с кукой
        return defaultBuilder()
                .cookie(new Cookie.Builder("JSESSIONID", jsessionId).build());
    }

    /**
     * Принудительный logout и очистка кэша сессии для пользователя
     */
    public static void logout(String username) {
        String jsessionId = sessionCookies.remove(username);
        if (jsessionId != null) {
            given()
                    .spec(defaultBuilder())
                    .cookie("JSESSIONID", jsessionId)
                    .when()
                    .delete("/ws/rest/v1/session")
                    .then()
                    .statusCode(org.hamcrest.Matchers.anyOf(
                            org.hamcrest.Matchers.is(204),
                            org.hamcrest.Matchers.is(200)
                    ));
        }
    }

    /**
     * Очистить все сохранённые сессии (например, в @AfterAll)
     */
    public static void clearAllSessions() {
        sessionCookies.clear();
    }
}

