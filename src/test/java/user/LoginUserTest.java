package user;

import generate.random.GenerateRandomUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest {
    private CreateUserExample createUserExample;
    private int id;

    @Before
    public void seyUp(){
        createUserExample = new CreateUserExample();
    }

    @Test
    @DisplayName("Успешная авторизация пользователя")
    @Description("Успешная авторизация при вводе валидных данных")
    public void loginWithTrueInfo() {
        User user = GenerateRandomUser.getRandomUser();
        Response registrationResponse = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        id =loginResponse.as(UserId.class).getId();

        loginResponse
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Авторизация пользователя не проходит")
    @Description("Вернётся ошибка при вводе невалидных данных логина")
    public void loginInAccountWithFakeLogin() {
        User user = GenerateRandomUser.getRandomUser();
        Response loginResponse = createUserExample.loginUser(user);
        user.setEmail("gergregger");
        id =loginResponse.as(UserId.class).getId();

        loginResponse
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Авторизация пользователя не проходит")
    @Description("Вернётся ошибка при вводе невалидных данных пароля")
    public void loginInAccountWithFakePassword() {
        User user = GenerateRandomUser.getRandomUser();
        Response loginResponse = createUserExample.loginUser(user);
        user.setPassword("4645645");
        id =loginResponse.as(UserId.class).getId();

        loginResponse
                .then()
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false));
    }

    @After
    public void tearDown() {
        if (id != 0) {
            Response deleteResponse = createUserExample.deleteUser(id);
            deleteResponse
                    .then()
                    .assertThat()
                    .statusCode(SC_OK)
                    .body("success", equalTo(true));
        }
    }
}
