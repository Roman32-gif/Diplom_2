package user;

import generateRandom.GenerateRandomUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserDataTest {
    private CreateUserExample createUserExample;
    private int id;

    @Before
    public void setUp(){
        createUserExample = new CreateUserExample();
        //id = createUserExample.createUser();
    }

    @Test
    @DisplayName("")
    @Description("")
    public void changeUserDataWithAuth () {
        User updatedUser = GenerateRandomUser.getRandomUser();
        updatedUser.setName("New Name");
        updatedUser.setEmail("newemail@example.com");
        updatedUser.setPassword("1234567890");

        Response response = createUserExample.updateUserWithAuthorization(updatedUser, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ODNlNjU5OWVkMjgwMDAxYjU0OWIwMiIsImlhdCI6MTczNjY5NzQ0OCwiZXhwIjoxNzM2Njk4NjQ4fQ.ubUSwI4EEXQagLE26Hbg7xFM4ARtL3_aeHK-9ZOTtqk");


        response.then()
                .statusCode(SC_OK)
                .body("name", equalTo("New Name"))
                .body("email", equalTo("newemail@example.com"))
                .body("phone", equalTo("1234567890"));


    }

    @Test
    public void changeUserDataWithoutAuth() {
        User updateUser = GenerateRandomUser.getRandomUser();
        updateUser.setEmail("newemail@example.com");
        updateUser.setPassword("newpassword123");
        updateUser.setName("New Name");

        Response response = createUserExample.updateUserWithoutAuthorization(updateUser);


        response.then()
                .statusCode(SC_UNAUTHORIZED)
                .body("name", equalTo("New Name"))
                .body("email", equalTo("newemail@example.com"))
                .body("phone", equalTo("1234567890"));
    }
}
