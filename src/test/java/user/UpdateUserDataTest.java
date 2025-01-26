package user;
import generate.random.GenerateRandomUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserDataTest {
    private CreateUserExample createUserExample;
    private String token;

    @Before
    public void setUp(){
        createUserExample = new CreateUserExample();

    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение имени пользователя с авторизацией")
    public void changeUserNameWithAuth() {
        User user = GenerateRandomUser.getRandomUser();
        Response createResponse = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        token = loginResponse.jsonPath().getString("accessToken");
        user.setName("newname");
        String name = user.getName();
        Response updateResponse = createUserExample.updateUserWithAuthorization(user, token);
        String email = user.getEmail();
        System.out.println("Update response: " + updateResponse.asString());
        updateResponse.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }


    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение почты пользователя с авторизацией")
    public void changeUserEmailWithAuth() {
        User user = GenerateRandomUser.getRandomUser();
        Response createResponse = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        token = loginResponse.jsonPath().getString("accessToken");

        String name = user.getName();
        user.setEmail("newlife");
        Response updateResponse = createUserExample.updateUserWithAuthorization(user, token);
        String email = user.getEmail();

        System.out.println("Update response: " + updateResponse.asString());

        updateResponse.then()
                .statusCode(SC_OK)
                .body("success" , equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));

    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение пароля пользователя с авторизацией")
    public void changeUserPasswordWithAuth() {
        User user = GenerateRandomUser.getRandomUser();
        Response createResponse = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        String token = loginResponse.jsonPath().getString("accessToken");
        user.setPassword("1243656857");
        Response updateResponse = createUserExample.resetPassword(user, token);
        System.out.println("Update response: " + updateResponse.asString());
        updateResponse.then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("message", equalTo("Reset email sent"));


    }



    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Изменение имени пользователя")
    public void changeUserNameWithoutAuth() {
        User user = GenerateRandomUser.getRandomUser();
        Response createResponse = createUserExample.createUser(user);
        user.setName("NewName");
        Response updateResponse = createUserExample.updateUserWithoutAuthorization(user);
        System.out.println("Update response: " + updateResponse.asString());
        updateResponse.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Изменение пароля пользователя ")
    public void changeUserPasswordWithoutAuth() {
        User user = GenerateRandomUser.getRandomUser();
        Response createResponse = createUserExample.createUser(user);
        user.setPassword("1243656857");
        Response updateResponse = createUserExample.updateUserWithoutAuthorization(user);
        System.out.println("Update response: " + updateResponse.asString());
        updateResponse.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Изменение почты пользователя ")
    public void changeUserEmailWithoutAuth() {
        User user = GenerateRandomUser.getRandomUser();
        Response createResponse = createUserExample.createUser(user);
        user.setEmail("NewEmail");
        Response updateResponse = createUserExample.updateUserWithoutAuthorization(user);
        System.out.println("Update response: " + updateResponse.asString());
        updateResponse.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false));
    }

    @After
    public void tearDown() {
        if (token != null) {
            Response deleteResponse = createUserExample.deleteNewUser(token);
            System.out.println("Delete response: " + deleteResponse.asString()); // Логируем ответ на удаление
        } else {
            System.out.println("Token is null, skipping deletion.");
        }


    }
}
