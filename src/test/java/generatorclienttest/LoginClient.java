package generatorclienttest;

import generatorclient.GeneratorClient;

import static com.codeborne.selenide.Selenide.$;

public class LoginClient {
    static void loginClint(GeneratorClient client){
        $("input[name='login']").setValue(client.getLogin());
        $("input[name='password']").setValue(client.getPassword());
        $("span.button__text").click();
    }
    static void loginClint(String login, String password){
        $("input[name='login']").setValue(login);
        $("input[name='password']").setValue(password);
        $("span.button__text").click();
    }
}
