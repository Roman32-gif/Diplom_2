package user;

import generate.random.GenerateRandomUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {

    private CreateUserExample createUserExample;
    private int id;

    @Before
    public void setUp() {
        createUserExample = new CreateUserExample();

    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Успешное создание пользователя при вводе валидных данных")
    public void createUser() {
        User user = GenerateRandomUser.getRandomUser();
        Response response = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        id =loginResponse.as(UserId.class).getId();

        response
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("success", equalTo(true));


    }

    @Test
    @DisplayName("Создание пользователя без ввода данных в поле почта")
    @Description("Вернётся ошибка при попытке создания пользователя")
    public void createUserWithoutEmail() {
        User user = GenerateRandomUser.getRandomUser();
        user.setEmail(null);
        Response response = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        id =loginResponse.as(UserId.class).getId();

        response
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false));

    }

    @Test
    @DisplayName("Создание пользователя без ввода данных в поле имя")
    @Description("Вернётся ошибка при попытке создания пользователя")
    public void createUserWithoutName() {
        User user = GenerateRandomUser.getRandomUser();
        user.setName(null);
        Response response = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        id =loginResponse.as(UserId.class).getId();

        response
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false));

    }

    @Test
    @DisplayName("Создание пользователя без ввода данных в поле пароль")
    @Description("Вернётся ошибка при попытке создания пользователя")
    public void createUserWithoutPassword() {
        User user = GenerateRandomUser.getRandomUser();
        user.setPassword(null);
        Response response = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        id =loginResponse.as(UserId.class).getId();

        response
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false));

    }

    @Test
    @DisplayName("Регистрация пользователя, который уже зарегистрирован в системе")
    @Description("Вернётся ошибка при регистрации")
    public void registerUserThatAlreadyExist(){
        User existingUser = GenerateRandomUser.getRandomUser();
        createUserExample.createUser(existingUser);
        Response response = createUserExample.createUser(existingUser);

        response
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
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
