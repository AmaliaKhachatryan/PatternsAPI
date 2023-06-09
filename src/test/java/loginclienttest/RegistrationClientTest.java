package loginclienttest;

import com.codeborne.selenide.Condition;
import generatorclient.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static generatorclient.GeneratorClient.*;

public class RegistrationClientTest {
    static void loginClint(String login, String password){
        $("input[name='login']").setValue(login);
        $("input[name='password']").setValue(password);
        $("span.button__text").click();
    }
    @BeforeEach
    public void setUp() {
       // Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var client = RegistrationClient.registeredClient("en", Status.active);
        loginClint(client.getLogin(),client.getPassword());
        $("h2[class='heading heading_size_l heading_theme_alfa-on-white']").shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.exactText("  Личный кабинет"));
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var client =RegistrationClient.generatedClientActive("en");
        loginClint(client.getLogin(),client.getPassword());
        $("div[class='notification__content']").shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var client =RegistrationClient.registeredClient("en", Status.blocked);
        loginClint(client.getLogin(),client.getPassword());
        $("div[class='notification__content']").shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var client =RegistrationClient.registeredClient("en", Status.active);
        var wrongLogin = generateLogin("en");
        loginClint(wrongLogin,client.getPassword());
        $("div[class='notification__content']").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var client = RegistrationClient.registeredClient("en", Status.active);
        var wrongPassword = generatePassword("en");
        loginClint(client.getLogin(),wrongPassword);
        $("div[class='notification__content']").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
//         TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
//          паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
//          "Пароль" - переменную wrongPassword
    }
}