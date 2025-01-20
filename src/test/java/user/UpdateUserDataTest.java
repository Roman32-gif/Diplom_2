package user;

import generate.random.GenerateRandomUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class UpdateUserDataTest {
    private CreateUserExample createUserExample;
    private int id;
    public String accessToken;

    @Before
    public void setUp(){
        createUserExample = new CreateUserExample();
        //id = createUserExample.createUser();
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение имени пользователя")
    public void changeUserNameWithAuth() {
        User updatedUser = GenerateRandomUser.getRandomUser();
        updatedUser.setName("New Name");

        Response response = createUserExample.updateUserWithAuthorization(updatedUser, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ODNlNjU5OWVkMjgwMDAxYjU0OWIwMiIsImlhdCI6MTczNjY5NzQ0OCwiZXhwIjoxNzM2Njk4NjQ4fQ.ubUSwI4EEXQagLE26Hbg7xFM4ARtL3_aeHK-9ZOTtqk");

        response.then()
                .statusCode(SC_OK)
                .body("name", equalTo("New Name"));
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение почты пользователя")
    public void changeUserEmailWithAuth() {
        User updatedUser = GenerateRandomUser.getRandomUser();
        updatedUser.setEmail("newemail@example.com");

        Response response = createUserExample.updateUserWithAuthorization(updatedUser, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ODNlNjU5OWVkMjgwMDAxYjU0OWIwMiIsImlhdCI6MTczNjY5NzQ0OCwiZXhwIjoxNzM2Njk4NjQ4fQ.ubUSwI4EEXQagLE26Hbg7xFM4ARtL3_aeHK-9ZOTtqk");

        response.then()
                .statusCode(SC_OK)
                .body("email", equalTo("newemail@example.com"));
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение пароля пользователя")
    public void changeUserPasswordWithAuth() {
        User updatedUser = GenerateRandomUser.getRandomUser();
        Response loginResponse = createUserExample.loginUser(updatedUser);
        //accessToken = loginResponse.body().as(CreateUserExample.class).getAccessToken();
        updatedUser.setPassword("1234567890");
        Response response = createUserExample.updateUserWithAuthorization(updatedUser,"");

        response.then()
                .statusCode(SC_OK)
                .body("password", equalTo("1234567890"));
    }



    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение данных пользователя без авторизации")
    public void changeUserDataWithoutAuth() {
        User updateUser = GenerateRandomUser.getRandomUser();
        updateUser.setEmail("newemail@example.com");
        updateUser.setPassword("newpassword123");
        updateUser.setName("New Name");

        Response response = createUserExample.updateUserWithoutAuthorization(updateUser);

        response.then()
                .statusCode(SC_UNAUTHORIZED);

        User currentUser = (User) createUserExample.getUser("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ODNlNjU5OWVkMjgwMDAxYjU0OWIwMiIsImlhdCI6MTczNjY5NzQ0OCwiZXhwIjoxNzM2Njk4NjQ4fQ.ubUSwI4EEXQagLE26Hbg7xFM4ARtL3_aeHK-9ZOTtqk");

        assertEquals("oldemail@example.com", currentUser.getEmail());
        assertEquals("Old Name", currentUser.getName());
        assertEquals("1234567890", currentUser.getPassword());
}
}
