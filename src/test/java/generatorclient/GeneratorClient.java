package generatorclient;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static generatorclient.GeneratorClient.RegistrationRequest.getRegisteredClient;
import static io.restassured.RestAssured.given;

@Value
public class GeneratorClient {
private GeneratorClient() {

}
    public static String generateLogin(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePassword(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.internet().password();
    }
public static class RegistrationRequest{

 private  RegistrationRequest(){

 }
        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        static void getRegisteredClient(DataClient client) {
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(client) // передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
        }
    }
public static class RegistrationClient {
    private RegistrationClient() {

    }
    public static DataClient generatedClientActive(String locale) {
        return new DataClient(generateLogin(locale), generatePassword(locale), Status.active);
    }

    public static DataClient registeredClient(String locale, Status status) {
        // TODO: объявить переменную registeredUser и присвоить ей значение возвращённое getUser(status).
        // Послать запрос на регистрацию пользователя с помощью вызова sendRequest(registeredUser)
        //  var client= new DataClient(generateLogin(locale), generatePassword(locale), status);
        var client = new DataClient(generateLogin(locale), generatePassword(locale), status);
        getRegisteredClient(client);
        return client;
    }
}
    @Value
    public static class DataClient {
        String login;
        String password;
        Status status;
    }

}