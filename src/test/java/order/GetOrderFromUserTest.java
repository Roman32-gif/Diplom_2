package order;

import generate.random.GenerateRandomUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import user.CreateUserExample;
import user.User;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrderFromUserTest {
    private CreateUserExample createUserExample;
    private CreateOrderExample createOrderExample;

    @Before
    public void setUp() {
        createUserExample = new CreateUserExample();

    }

    @Test
    @DisplayName("получение заказа конкретного пользователя")
    @Description("получения заказа авторизованного пользователя")
    public void getOrderFromAuthUser () {
        User user = GenerateRandomUser.getRandomUser();
        // Создание пользователя и получение токена
        Response createResponse = createUserExample.createUser(user);
        Response loginResponse = createUserExample.loginUser(user);
        String token = loginResponse.jsonPath().getString("accessToken");

        // Создание заказа с указанным ингредиентом
        Order order = new Order();
        String ingredientId = "61c0c5a71d1f82001bdaaa6d"; // ID ингредиента
        order.setIngredients(Collections.singletonList(ingredientId)); // Добавляем выбранный ингредиент

        // Отправка запроса на создание заказа
        Response createOrderResponse = createOrderExample.createOrder(order, token);

        Response getOrderResponse = createOrderExample.getOrders(token);
        getOrderResponse.then()
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("получение заказа конкретного пользователя")
    @Description("получения заказа неавторизованного пользователя")
    public void getAllOrdersFromNotAuthUser () {
        // Создание заказа без авторизации
        Order order = new Order();
        String ingredientId = "61c0c5a71d1f82001bdaaa6d"; // ID ингредиента
        order.setIngredients(Collections.singletonList(ingredientId)); // Добавляем выбранный ингредиент

        // Отправка запроса на создание заказа
        Response createOrderResponse = createOrderExample.createOrder(order, null); // Передаем null вместо токена

        // Попытка получить все заказы без авторизации
        Response getOrderResponse = createOrderExample.getOrders(null); // Передаем null для неавторизованного запроса
        getOrderResponse.then()
                .statusCode(401) // Ожидаем статус 401 Unauthorized
                .body("success", equalTo(false)) // Проверяем  false
                .body("message", equalTo("You should be authorised"));
    }
}
