package generatorClient;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static generatorClient.GeneratorClient.RegistrationClient.getRegisteredClient;
import static io.restassured.RestAssured.given;

@Value
public class GeneratorClient {
    String login;
    String password;
    Status status;

    public static GeneratorClient generatedClientActive(String locale, Status status) {
        return new GeneratorClient(generateLogin(locale), generatePassword(locale), status);
    }

    public static String generateLogin(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePassword(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.internet().password();
    }

    public static class RegistrationClient {
        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

      static   void getRegisteredClient(GeneratorClient client) {
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(client) // передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
        }
    }
    public static GeneratorClient registeredClient(String locale,Status status) {
        // TODO: объявить переменную registeredUser и присвоить ей значение возвращённое getUser(status).
        // Послать запрос на регистрацию пользователя с помощью вызова sendRequest(registeredUser)
        var client= new GeneratorClient(generateLogin(locale), generatePassword(locale), status);
        getRegisteredClient(client);
        return client;
    }
}