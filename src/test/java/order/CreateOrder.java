package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import user.CreateUserExample;

public class CreateOrder {

    private CreateUserExample createUserExample;
    private int id;
    private Order order;

    @Before
    public void setUp() {
        createUserExample = new CreateUserExample();

    }

    @Test
    @DisplayName("")
    @Description("")
    public void createWithAuth() {
        Response response = CreateOrderExample.createOrderWithAuthorization(order, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ODNlNjU5OWVkMjgwMDAxYjU0OWIwMiIsImlhdCI6MTczNjY5NzQ0OCwiZXhwIjoxNzM2Njk4NjQ4fQ.ubUSwI4EEXQagLE26Hbg7xFM4ARtL3_aeHK-9ZOTtqk");
    }

    @Test
    @DisplayName("")
    @Description("")
    public void createWithOutAuth() {
        Response response = CreateOrderExample.createOrderWithoutAuthorization(order);
    }
}
